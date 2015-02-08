package com.akka.transaction;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * Created by root on 15-2-8.
 */
public class AppContext {

    public  static void main(String[]args){


        ActorSystem system=ActorSystem.create("system");

        ActorRef bankActor=system.actorOf(Props.create(BankActor.class));
        //TransferMsg

        TransferMsg transferMsg=new TransferMsg(Float.parseFloat("10"));

        bankActor.tell(transferMsg,bankActor);


        AccountBalance accountBalance =new AccountBalance("ABC");

        bankActor.tell(accountBalance ,ActorRef.noSender());

        AccountBalance accountBalance1 =new AccountBalance("XYZ");

        bankActor.tell(accountBalance1 ,ActorRef.noSender());



    }

}
