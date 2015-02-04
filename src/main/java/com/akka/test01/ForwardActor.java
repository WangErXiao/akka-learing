package com.akka.test01;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

import java.util.concurrent.TimeUnit;

/**
 * Created by root on 15-2-4.
 */
public class ForwardActor extends UntypedActor {

    ActorRef target=getContext().actorOf(Props.create(TargetActor.class));

    @Override
    public void onReceive(Object o) throws Exception {
        //forward msg to target
        TimeUnit.SECONDS.sleep(1);
        target.forward(o,getContext());

    }
}
