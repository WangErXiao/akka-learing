package com.akka.transaction;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Function;
import akka.transactor.Coordinated;
import akka.transactor.CoordinatedTransactionException;
import akka.util.Timeout;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Created by root on 15-2-8.
 */
public class TranserActor extends UntypedActor {

    LoggingAdapter log= Logging.getLogger(getContext().system(),this);

    String fromAccount="XYZ";
    String toAccount="ABC";


    ActorRef from=context().actorOf(Props.create(AccountActor.class,fromAccount,Float.parseFloat("50000")));

    ActorRef to=context().actorOf(Props.create(AccountActor.class,toAccount,Float.parseFloat("10000")));

    Timeout timeout=new Timeout(5, TimeUnit.SECONDS);

    @Override
    public void onReceive(Object o) throws Exception {

        if(o instanceof  TransferMsg){
            final  TransferMsg transfer=(TransferMsg)o;

            final Coordinated coordinated=new Coordinated(timeout);

            coordinated.atomic(new Runnable() {
                @Override
                public void run() {
                    to.tell(coordinated.coordinate(
                            new AccountCredit(transfer.getAmtToTransferred(),"accountCredit")),getSelf());
                    from.tell(coordinated.coordinate(
                            new AccountDebit(transfer.getAmtToTransferred(), "accountDebit")), getSelf());
                }
            });
        }else if(o instanceof AccountBalance){
            AccountBalance accountBalance=(AccountBalance)o;
            if(accountBalance.getAccountNumber().equals(fromAccount)){
                from.tell(accountBalance,getSender());
            }
            if(accountBalance.getAccountNumber().equals(toAccount)){
                to.tell(accountBalance, getSender());
            }
        }else if(o instanceof  AccountMsg){
                from.tell(o,sender());
        }
    }

    private static SupervisorStrategy strategy=
            new AllForOneStrategy(10, Duration.create(10,TimeUnit.SECONDS),
                    new Function<Throwable, SupervisorStrategy.Directive>() {
                @Override
                public SupervisorStrategy.Directive apply(Throwable t) throws Exception {
                    if(t instanceof CoordinatedTransactionException){
                        return  SupervisorStrategy.resume();
                    }else if( t instanceof  IllegalStateException){
                        return  SupervisorStrategy.resume();
                    }else if(t instanceof IllegalArgumentException){
                        return SupervisorStrategy.stop();
                    }else{
                        return SupervisorStrategy.escalate();
                    }
                }
            });


    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }
}
