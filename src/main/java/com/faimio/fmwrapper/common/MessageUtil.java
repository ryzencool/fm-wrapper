package com.faimio.fmwrapper.common;


import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

public class MessageUtil {

    public final static ConcurrentLinkedDeque<Integer> MESSAGE_IDS = new ConcurrentLinkedDeque<>(Arrays.asList(1,2,3,4,5,6,7,8,9));


    public static  Integer getId() {
       if (MESSAGE_IDS.size() < 10)  {
           for (int i = 1; i < 10; i++) {
               MESSAGE_IDS.add(MESSAGE_IDS.getLast() + 1);
           }
       }
       return MESSAGE_IDS.pollFirst();
    }

    public static void main(String[] args) {

    }
}
