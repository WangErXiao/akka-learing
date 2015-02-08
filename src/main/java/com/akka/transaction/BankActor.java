package com.akka.transaction;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Created by root on 15-2-8.
 */
public class BankActor extends UntypedActor {

    ActorRef transfer =getContext().actorOf(Props.create(TranserActor.class));

    @Override
    public void onReceive(Object o) throws Exception {
        if(o instanceof  TransferMsg){
            transfer.tell(o,getSelf());
        }else if(o instanceof  AccountBalance){
            Timeout timeout=new Timeout(Duration.create(500, TimeUnit.SECONDS));
            Future<Object> future= Patterns.ask(transfer,o, timeout);
            AccountBalance result=(AccountBalance)Await.result(future,timeout.duration());
            System.out.println("Account #"+result.getAccountNumber()
            +" , Balance  "+result.getAmount());
            getSender().tell(result,getSelf());
        }else{
            unhandled(o);
        }
    }
}
