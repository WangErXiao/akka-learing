package com.akka.monitor;

import akka.actor.ActorRef;

/**
 * Created by root on 15-2-7.
 */
public class RegisterWorker{

    ActorRef worker;

    ActorRef supervisor;

    public RegisterWorker(ActorRef supervisor, ActorRef worker) {
        this.supervisor = supervisor;
        this.worker = worker;
    }

    public ActorRef getWorker() {
        return worker;
    }


    public ActorRef getSupervisor() {
        return supervisor;
    }

}
