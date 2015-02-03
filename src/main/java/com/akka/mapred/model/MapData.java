package com.akka.mapred.model;

import java.util.List;

/**
 * Created by root on 15-2-3.
 */
public class MapData {
    private final List<WordCount> dataList;


    public List<WordCount> getDataList() {
        return dataList;
    }

    public MapData(List<WordCount> dataList){
        this.dataList=dataList;
    }


}
