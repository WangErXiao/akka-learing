package com.akka.mapred.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.akka.mapred.model.MapData;
import com.akka.mapred.model.ReduceData;
import com.akka.mapred.model.Result;

/**
 * Created by root on 15-2-3.
 */
public class MasterActor extends UntypedActor{
    ActorRef mapActor=getContext().actorOf(Props.create(MapActor.class),"mapActor");
    ActorRef reduceActor=getContext().actorOf(Props.create(ReduceActor.class),"reduceActor");
    ActorRef aggregateActor=getContext().actorOf(Props.create(AggregateActor.class),"aggregateActor");

    @Override
    public void onReceive(Object o) throws Exception {

        if(o instanceof String){
            mapActor.tell(o,getSelf());
        }else if(o instanceof MapData){
            reduceActor.tell(o,getSelf());
        }else if(o instanceof ReduceData){
            aggregateActor.tell(o,getSelf());
        }else if(o instanceof Result){
            aggregateActor.forward(o,getContext());
        }else{
            unhandled(o);
        }

    }
}
