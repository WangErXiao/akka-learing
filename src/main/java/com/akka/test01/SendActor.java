package com.akka.test01;

import akka.actor.UntypedActor;

/**
 * Created by root on 15-2-4.
 */
public class SendActor extends UntypedActor {


    @Override
    public void onReceive(Object o) throws Exception {
        System.out.println(o.toString());
    }
}
