package com.akka.monitor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Created by root on 15-2-7.
 */
public class AppContext {

    public static void main(String[]args){

        ActorSystem system=ActorSystem.create("system");

        LoggingAdapter log= Logging.getLogger(system, system);

        ActorRef workerActor=system.actorOf(Props.create(WorkerActor.class));

        ActorRef supervisorActor=system.actorOf(Props.create(SupervisorActor.class,workerActor));


        ActorRef monitorActor=system.actorOf(Props.create(MonitorActor.class));

        RegisterWorker registerWorker=new RegisterWorker(supervisorActor,workerActor);

        //monitorActor.tell(Terminated.apply(workerActor,true,true),ActorRef.noSender());

        monitorActor.tell(registerWorker, supervisorActor);


        /*supervisorActor.tell(Integer.valueOf(10),supervisorActor);*/

        supervisorActor.tell(new Object(),supervisorActor);

        Timeout timeout=new Timeout(Duration.create(1000, TimeUnit.SECONDS));

        Future<Object> future=Patterns.ask(supervisorActor, new Result(), timeout);

        try {
            Integer result=(Integer)Await.result(future,timeout.duration());
            log.info("Value Received -> {}",result);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
