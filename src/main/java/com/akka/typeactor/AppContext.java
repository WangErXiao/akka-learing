package com.akka.typeactor;

import akka.actor.ActorSystem;
import akka.actor.TypedActor;
import akka.actor.TypedProps;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;

import java.util.concurrent.TimeUnit;

/**
 * Created by root on 15-2-4.
 */
public class AppContext {
    public static void main(String[]args){
        ActorSystem system=ActorSystem.create("system");
        CalculatorInt calculator= TypedActor.get(system)
                .typedActorOf(TypedProps.apply(CalculatorInt.class,Calculator.class));
        Future<Integer> result=calculator.add(12, 2);
        Timeout timeout=new Timeout(FiniteDuration.create(5, TimeUnit.SECONDS));
        //calculator.subtract(3,1);
        //calculator.incrementCount();
        System.out.println("start-----------");
        /*while (true){
            if(result.isCompleted()){
                System.out.println(result.value().toString());
                break;
            }else{
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }*/
        try {
            Await.result(result,timeout.duration());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("finish---------");
        system.shutdown();
    }



}
