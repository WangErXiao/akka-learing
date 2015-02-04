package com.akka.hotswap;

import akka.actor.UntypedActor;
import akka.japi.Procedure;

import java.util.concurrent.TimeUnit;

/**
 * Created by root on 15-2-4.
 */
public class PingPongActor extends UntypedActor {
    static String PING="PING";
    static String PONG="PONG";
    int count=0;

    @Override
    public void onReceive(Object o) throws Exception {
        if(o instanceof String){
            if(((String) o).matches(PING)){
                System.out.println(PING);
                TimeUnit.SECONDS.sleep(1);
                getSelf().tell(PONG, getSelf());
                count++;
                getContext().become(new Procedure<Object>() {
                    @Override
                    public void apply(Object o) throws Exception {
                        if(o instanceof String){
                            if(((String) o).matches(PONG)){
                                System.out.println(PONG);
                                getSelf().tell(PING,getSelf());
                                count++;
                                getContext().unbecome();
                            }
                        }
                    }
                });
            }
        }

        if(count>10){
            getContext().stop(getSelf());
        }




    }
}
