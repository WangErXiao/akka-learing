package com.akka.monitor;

import akka.actor.ActorRef;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 15-2-7.
 */
public class MonitorActor extends UntypedActor {
    LoggingAdapter log= Logging.getLogger(getContext().system(),this);
    Map<ActorRef,ActorRef> monitoredActors=new HashMap<>();

    @Override
    public void onReceive(Object o) throws Exception {
           if(o instanceof Terminated){
               final  Terminated t=(Terminated)o;
               if(monitoredActors.containsKey(t.actor())){
                    log.info("Received worker actor termination{}",t.getActor().path());
                    log.info("Sending message to Supervisor");
                    monitoredActors.get(t.actor()).tell(new DeadWorker(),getSelf());
               }
           }else if(o instanceof RegisterWorker){
               RegisterWorker msg=(RegisterWorker)o;
               getContext().watch(msg.getWorker());
               log.info("register supervisor #{} worker #{}",msg.getSupervisor().hashCode(),msg.getWorker().hashCode());
               monitoredActors.put(msg.getWorker(),msg.getSupervisor());
           }else{
               unhandled(o);
           }
    }
}
