package com.akka.mapred;


import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import com.akka.mapred.actor.MasterActor;
import com.akka.mapred.model.Result;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Created by root on 15-2-3.
 */
public class MapReduceApplication {
    public static void main(String[]args) throws Exception {
        Timeout timeout=new Timeout(Duration.create(50, TimeUnit.SECONDS));
        ActorSystem system=ActorSystem.create("MapReduceApp");
        ActorRef master=system.actorOf(Props.create(MasterActor.class),"masterActor");
        master.tell("i am a young man", null);
        master.tell("i am a young man",null);
        master.tell("i am a young man",null);
        master.tell("i am a young man",null);
        master.tell("i am a young man where is you sorry null and ", null);
        Thread.sleep(50);
        Future<Object> future=Patterns.ask(master,new Result(),timeout);
        String result=(String) Await.result(future, timeout.duration());
        System.out.println(result);
        system.shutdown();




    }
}
