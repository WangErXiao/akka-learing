package com.akka.test01;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.util.concurrent.TimeUnit;

/**
 * Created by root on 15-2-4.
 */
public class AppContext {
    public static void main(String[]args) throws InterruptedException {
        ActorSystem actorSystem=ActorSystem.create("forwardSystem");
        ActorRef sendActor=actorSystem.actorOf(Props.create(SendActor.class));
        ActorRef forwardActor=actorSystem.actorOf(Props.create(ForwardActor.class));
        forwardActor.tell("hello world",sendActor);
        TimeUnit.SECONDS.sleep(3);
        actorSystem.shutdown();
    }
}
