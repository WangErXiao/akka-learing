package com.akka.supervisor.one4one;

import akka.actor.*;
import akka.japi.Function;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;


/**
 * Created by root on 15-2-6.
 */
public class SupervisorActor extends UntypedActor {

    private ActorRef childActor;

    public SupervisorActor() {
        this.childActor = getContext().actorOf(Props.create(WorkerActor.class),"workerActor");

    }

    private static SupervisorStrategy strategy=
            new OneForOneStrategy(10, Duration.apply(10, TimeUnit.SECONDS),
                    new Function<Throwable, SupervisorStrategy.Directive>() {
                        @Override
                        public SupervisorStrategy.Directive apply(Throwable throwable) throws Exception {
                            if(throwable instanceof ArithmeticException){
                                return  SupervisorStrategy.resume();
                            }else if (throwable instanceof NullPointerException){
                                return SupervisorStrategy.restart();
                            }else if(throwable instanceof IllegalArgumentException){
                                return SupervisorStrategy.stop();
                            }else{
                                return SupervisorStrategy.escalate();
                            }
                        }
                    });


    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    @Override
    public void onReceive(Object o) throws Exception {
        if(o instanceof WorkerActor.Result){
            childActor.tell(o,getSender());
        }else{
            childActor.tell(o,getSelf());
        }
    }
}
