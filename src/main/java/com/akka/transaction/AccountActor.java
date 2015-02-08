package com.akka.transaction;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.transactor.Coordinated;
import scala.concurrent.stm.Ref;
import scala.concurrent.stm.japi.STM;

/**
 * Created by root on 15-2-8.
 */
public class AccountActor extends UntypedActor {

    LoggingAdapter log= Logging.getLogger(getContext().system(),this);

    String accountNumber;

    Ref.View<Float> balance= STM.newRef(Float.parseFloat("0"));


    public AccountActor(String accountNumber, Float balance) {
        this.accountNumber = accountNumber;
        this.balance.set(balance);
    }

    @Override
    public void onReceive(Object o) throws Exception {
        if(o instanceof Coordinated){
              Coordinated coordinated=(Coordinated)o;
              final  Object message=coordinated.getMessage();

              if(message instanceof  AccountDebit){
                  coordinated.atomic(new Runnable() {
                      @Override
                      public void run() {
                          AccountDebit accountDebit=
                                  (AccountDebit)message;
                          if(balance.get()>=accountDebit.getAmount()){
                            Float bal=balance.get()-accountDebit.getAmount();
                            balance.set(bal);
                          }else{
                              throw  new IllegalStateException("Insufficient Balance");
                          }
                      }
                  });
              }else if(message instanceof AccountCredit){
                  coordinated.atomic(new Runnable() {
                      @Override
                      public void run() {
                          AccountCredit accountCredit=(AccountCredit)message;
                          Float bal=balance.get()+accountCredit.getAmount();
                          balance.set(bal);
                      }
                  });
              }
        }else if (o instanceof AccountBalance){
                AccountBalance accountBalance=(AccountBalance)o;
                accountBalance.setAmount(balance.get());
                getSender().tell(accountBalance,getSelf());
        }

    }
}
