package com.akka.test01;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

/**
 * Created by root on 15-2-4.
 */
public class TargetActor extends UntypedActor {


    @Override
    public void onReceive(Object o) throws Exception {
        System.out.println("received:"+o.toString());
        getSender().tell("i received your msg",getSelf());
    }
}
