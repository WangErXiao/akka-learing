package com.akka.mapred.model;

/**
 * Created by root on 15-2-3.
 */
public class WordCount {

    private String word;

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setWord(String word) {
        this.word = word;
    }

    private  Integer count;

    public WordCount(String word, Integer count){
        this.word=word;
        this.count=count;
    }


    public String getWord() {
        return word;
    }

    public Integer getCount() {
        return count;
    }

}
