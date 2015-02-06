package com.akka.supervisor;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Created by root on 15-2-6.
 */
public class WorkerActor extends UntypedActor {
    LoggingAdapter log= Logging.getLogger(getContext().system(),this);

    private int state=0;

    public static class Result{}


    @Override
    public void preStart() throws Exception {
        log.info("Starting WorkerActor instance hashCode # {}",this.hashCode());
    }

    @Override
    public void onReceive(Object o) throws Exception {
        if(o == null){
            throw  new NullPointerException("Null Value Passed");
        } else if (o instanceof  Integer){
            Integer value=(Integer)o;
            if(value<=0){
                throw  new ArithmeticException("Number equal or less than zero");

            }else{
                state=value;
            }
        } else if (o instanceof  Result){
            getSender().tell(state,getSelf());
        }else{
            throw  new IllegalArgumentException("Illegal argument");
        }
    }

    @Override
    public void postStop() throws Exception {

        log.info("Stop WorkerActor instance hashCode #{}",this.hashCode());

    }
}
