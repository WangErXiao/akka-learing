package com.akka.transaction;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by root on 15-2-8.
 */
public class AppContext {

    public  static void main(String[]args){
        AtomicInteger atomicInteger=new AtomicInteger(0);
        ActorSystem system=ActorSystem.create("system");
        ActorRef bankActor=system.actorOf(Props.create(BankActor.class));
        //TransferMsg
        TransferMsg transferMsg=new TransferMsg(Float.parseFloat("1"));
        //bankActor.tell(transferMsg,bankActor);
        new QueryBalanceThread(bankActor).start();
        new TranserferThread(bankActor,transferMsg,atomicInteger).start();
        new TranserferThread(bankActor,transferMsg,atomicInteger).start();
        new TranserferThread(bankActor,transferMsg,atomicInteger).start();
        new TranserferThread(bankActor,transferMsg,atomicInteger).start();
        new TranserferThread(bankActor,transferMsg,atomicInteger).start();

    }

    private static class QueryBalanceThread extends Thread{
        private ActorRef bankActor;
        private QueryBalanceThread(ActorRef bankActor) {
            this.bankActor=bankActor;
        }
        @Override
        public void run() {
            while (true){
                AccountBalance accountBalance =new AccountBalance("ABC");
                bankActor.tell(accountBalance, ActorRef.noSender());
                AccountBalance accountBalance1 =new AccountBalance("XYZ");
                bankActor.tell(accountBalance1 ,ActorRef.noSender());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class TranserferThread extends  Thread{
        private ActorRef bankActor;
        //TransferMsg
        private TransferMsg transferMsg;
        //private AtomicInteger atomicInteger;

        private TranserferThread(ActorRef bankActor, TransferMsg transferMsg,AtomicInteger atomicInteger) {
            this.bankActor = bankActor;
            this.transferMsg = transferMsg;
            //this.atomicInteger=atomicInteger;
        }
        @Override
        public void run() {
            while (true){
                bankActor.tell(transferMsg,bankActor);
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
