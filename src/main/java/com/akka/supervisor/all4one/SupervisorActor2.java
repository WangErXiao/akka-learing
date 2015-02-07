package com.akka.supervisor.all4one;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.akka.supervisor.one4one.WorkerActor;

/**
 * Created by root on 15-2-6.
 */
public class SupervisorActor2 extends UntypedActor {

    public ActorRef workerActor1;

    public ActorRef workerActor2;


    public SupervisorActor2() {
        this.workerActor1 = getContext().actorOf(Props.create(WorkerActor.class));
        this.workerActor2 = getContext().actorOf(Props.create(WorkerActor.class));
    }


    @Override
    public void onReceive(Object o) throws Exception {

    }
}
