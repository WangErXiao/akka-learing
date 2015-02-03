package com.akka.mapred.actor;

import akka.actor.UntypedActor;
import com.akka.mapred.model.MapData;
import com.akka.mapred.model.ReduceData;
import com.akka.mapred.model.WordCount;

import java.util.HashMap;
import java.util.List;

/**
 * Created by root on 15-2-3.
 */
public class ReduceActor extends UntypedActor {
    @Override
    public void onReceive(Object o) throws Exception {
            if(o instanceof MapData){
                MapData mapData=(MapData)o;
                getSender().tell(reduce(mapData.getDataList()),getSelf());
            }else{
                unhandled(o);
            }
    }
    private ReduceData reduce(List<WordCount> dataList) {
        HashMap<String,Integer>reduceMap=new HashMap<>();
        for (WordCount wordCount:dataList){
            if(reduceMap.containsKey(wordCount.getWord())){
                wordCount.setCount(wordCount.getCount() + 1);
            }else{
                reduceMap.put(wordCount.getWord(),Integer.valueOf(1));
            }
        }
        return  new ReduceData(reduceMap);
    }

}
