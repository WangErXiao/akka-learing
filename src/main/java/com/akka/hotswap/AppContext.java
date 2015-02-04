package com.akka.hotswap;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * Created by root on 15-2-4.
 */
public class AppContext {
    public static void main(String[]args){

        ActorSystem actorSystem=ActorSystem.create("system");

        ActorRef pingPong=actorSystem.actorOf(Props.create(PingPongActor.class));
        pingPong.tell(PingPongActor.PING,pingPong);




    }
}
