package com.akka.mapred.actor;

import akka.actor.UntypedActor;
import com.akka.mapred.model.ReduceData;
import com.akka.mapred.model.Result;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 15-2-3.
 */
public class AggregateActor extends UntypedActor{
    private Map<String,Integer>finalReducedMap=new HashMap<>();

    @Override
    public void onReceive(Object o) throws Exception {
        if(o instanceof ReduceData){
            ReduceData reduceData=(ReduceData)o;
            aggregateInMemoryReduce(reduceData.getReduceList());
        }else if(o instanceof Result){
            getSender().tell(finalReducedMap.toString(),getSelf());
        }else{
            unhandled(o);
        }
    }
    private void aggregateInMemoryReduce(HashMap<String, Integer> reduceList) {
        Integer count=null;
        for(String key:reduceList.keySet()){
            if(finalReducedMap.containsKey(key)){
                count=reduceList.get(key)+finalReducedMap.get(key);
                finalReducedMap.put(key,count);
            }else{
                finalReducedMap.put(key,reduceList.get(key));
            }
        }
    }
}
