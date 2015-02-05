package com.akka.typeactor;

import akka.japi.Option;
import scala.concurrent.Future;


/**
 * Created by root on 15-2-4.
 */
public interface CalculatorInt {

    public Future<Integer> add(Integer first,Integer second);

    public Future<Integer> subtract(Integer first,Integer second);

    public void incrementCount();

    public Option<Integer> incrementAndReturn();



}







