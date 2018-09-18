package com.bdht;

import java.util.*;

public class collect {
    public  static  void main(String[] args)
    {
        List<Integer> list = new ArrayList<Integer>();
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        List<Integer> ll = Collections.unmodifiableList(list);

        List<Integer> sub = list.subList(0,2);
        sub.clear();
        System.out.print(list);
    }
}
