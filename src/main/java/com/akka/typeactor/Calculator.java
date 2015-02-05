package com.akka.typeactor;

import akka.actor.TypedActor;
import akka.dispatch.Futures;
import akka.japi.Option;
import scala.concurrent.Future;

import java.util.concurrent.TimeUnit;


/**
 * Created by root on 15-2-4.
 */
public class Calculator implements CalculatorInt {
    Integer counter=0;
    @Override
    public Future<Integer> add(Integer first, Integer second) {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("noblocking +++++");
        return Futures.successful(first+second);
    }

    @Override
    public Future<Integer> subtract(Integer first, Integer second) {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("noblocking -----");
        return Futures.successful(first-second);
    }

    @Override
    public void incrementCount() {
        counter++;
        System.out.println("fire and forget");
    }

    @Override
    public Option<Integer> incrementAndReturn() {
        System.out.println("blocking request response");
        return Option.some(++counter);
    }
}
