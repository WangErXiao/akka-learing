package com.akka.actor;

import akka.actor.UntypedActor;

/**
 * Created by root on 15-2-4.
 */
public class MyActor extends UntypedActor {


    @Override
    public void onReceive(Object o) throws Exception {

        System.out.println("hello world");
    }
}
