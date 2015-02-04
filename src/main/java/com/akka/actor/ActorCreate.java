package com.akka.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * Created by root on 15-2-4.
 */
public class ActorCreate {

    public static void main(String[]args){
        ActorSystem actorSystem=ActorSystem.create("actorSystem");

        ActorRef myActor=actorSystem.actorOf(Props.create(MyActor.class),"myActor");


        myActor.tell("test---",myActor);

        actorSystem.shutdown();


    }


}
