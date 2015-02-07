package com.akka.monitor;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Function;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Created by root on 15-2-7.
 */
public class SupervisorActor extends UntypedActor {

    LoggingAdapter log= Logging.getLogger(getContext().system(), this);

    private ActorRef childActor;


    public SupervisorActor(ActorRef workerActor) {
        this.childActor =workerActor;
    }

    @Override
    public void preStart() throws Exception {
        log.info("starting supervisorActor  hashCode{}",this.hashCode());
    }

    private static SupervisorStrategy strategy=
            new OneForOneStrategy(10, Duration.apply(10, TimeUnit.SECONDS),
                    new Function<Throwable, SupervisorStrategy.Directive>() {
                        @Override
                        public SupervisorStrategy.Directive apply(Throwable throwable) throws Exception {
                            if(throwable instanceof IllegalArgumentException){
                                return SupervisorStrategy.stop();
                            }else{
                                return SupervisorStrategy.escalate();
                            }
                        }
                    });

   /* @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }*/

    @Override
    public void onReceive(Object o) throws Exception {
        if(o instanceof  DeadWorker){
            childActor.tell(Kill.getInstance(),getSelf());
        }else if(o instanceof Result){
            childActor.tell(o,getSender());
        }else{
            childActor.tell(o,getSelf());
        }
    }
}
