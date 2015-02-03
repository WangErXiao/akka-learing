package com.akka.mapred.model;

import java.util.HashMap;

/**
 * Created by root on 15-2-3.
 */
public class ReduceData {


    private final HashMap<String,Integer> reduceList;

    public ReduceData(HashMap<String,Integer> reduceList){
        this.reduceList=reduceList;
    }

    public HashMap<String, Integer> getReduceList() {
        return reduceList;
    }
}
