package com.bdht;

import  java.util.*;
import  java.io.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Scanner scan = new Scanner(System.in);
        String dir = scan.next();
        File file = new File(dir);
        String key = scan.next();
        MatchCounter thread  = new MatchCounter(file,key);
        FutureTask<Integer> task = new FutureTask<Integer>(thread);
        new Thread(task).start();
        try{
            int count = task.get();
            System.out.print(count);
        }catch(ExecutionException e)
        {
            e.printStackTrace();
        }catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}

class MatchCounter implements Callable<Integer>
{
    MatchCounter(File file ,String str)
    {
        this.dir = file;
        this.key = str;
        this.count = 0;
    }

    public Integer call()
    {
        count = 0;
        try{
            File[] files = dir.listFiles();
            ArrayList<Future<Integer>> results = new ArrayList<Future<Integer>>();
            for (File file : files)
            {
                if(file.isDirectory())
                {
                    MatchCounter count = new MatchCounter(file,key);
                    FutureTask<Integer> task = new FutureTask<Integer>(count);
                    results.add(task);
                    new Thread(task).start();

                }
                else
                {
                    count++;
                }

            }

            for (Future<Integer> result:results)
            {
                try{
                    count += result.get();
                }catch(ExecutionException e)
                {
                    e.printStackTrace();
                }
            }

        }catch(InterruptedException  e)
        {
            e.printStackTrace();
        }
        return  count;
    }

    private File dir;
    private String key;
    private int count;
}
