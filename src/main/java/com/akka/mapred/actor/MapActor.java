package com.akka.mapred.actor;

import akka.actor.UntypedActor;
import com.akka.mapred.model.MapData;
import com.akka.mapred.model.WordCount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by root on 15-2-3.
 */
public class MapActor extends UntypedActor {

    public final static String[]STOP_WORDS={};
    public final static List<String> STOP_WORDS_LIST= Arrays.asList(STOP_WORDS);

    @Override
    public void onReceive(Object o) throws Exception {
        if(o instanceof String){
            String work=(String)o;
            getSender().tell(evaluteExpression(work),getSelf());
        }else{
            unhandled(o);
        }
    }
    private MapData evaluteExpression(String line){
        List<WordCount> dataList=new ArrayList<>();
        StringTokenizer parser=new StringTokenizer(line);

        while(parser.hasMoreTokens()){
            String word=parser.nextToken().toLowerCase();
            if(!STOP_WORDS_LIST.contains(word)){
                dataList.add(new WordCount(word,Integer.valueOf(1)));
            }
        }
        return new MapData(dataList);
    }
}
