package com.akka.supervisor.one4one;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Created by root on 15-2-6.
 */
public class AppContext {

    public static void main(String[]args){

        Timeout timeout=new Timeout(Duration.create(5, TimeUnit.SECONDS));

        ActorSystem system=ActorSystem.create("faultTolerance");

        LoggingAdapter log= Logging.getLogger(system,system);

        Integer originalValue=Integer.valueOf(0);

        ActorRef supervisor=system.actorOf(Props.create(SupervisorActor.class),"supervisor");

        //log.info("Sending value 8 ,no exceptions should be thrown!");

        supervisor.tell(new Object(),supervisor);

        Future<Object> future=Patterns.ask(supervisor,new WorkerActor.Result(),timeout);

        Integer result= null;
        try {
            result = (Integer) Await.result(future, timeout.duration());
            log.info("Value Received -> {}",result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        system.shutdown();
    }
}
