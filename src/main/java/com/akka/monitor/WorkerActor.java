package com.akka.monitor;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Created by root on 15-2-7.
 */
public class WorkerActor extends UntypedActor {
    LoggingAdapter log= Logging.getLogger(getContext().system(),this);

    private int state=0;

    @Override
    public void preStart() throws Exception {
        log.info("Starting WorkerActor instance hashCode #{}",
                this.hashCode());

    }

    @Override
    public void postStop() throws Exception {
        log.info("terminate WorkerActor instance hashCode #{}",
                this.hashCode());
    }

    @Override
    public void onReceive(Object o) throws Exception {
        if(o instanceof Integer){
            state=(Integer)o;
        }else if (o instanceof Result){
            getSender().tell(state,getSelf());
        }else{
            throw new  IllegalArgumentException("Wrong Argument");
        }
    }
}
