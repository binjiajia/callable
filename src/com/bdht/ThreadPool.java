package com.bdht;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.*;

public class ThreadPool {
    public  static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        File file = new File(scan.next());
        String key = scan.next();
        ExecutorService pool = Executors.newCachedThreadPool();
        MatchCount count = new MatchCount(file,key,pool);
        Future<Integer> result = pool.submit(count);

        try{
            System.out.println(result.get());
            int n = ((ThreadPoolExecutor)pool).getLargestPoolSize();
            System.out.println(n);
        }catch(Exception e)
        {
            e.printStackTrace();
        }

    }
}

class MatchCount implements Callable<Integer>
{
    MatchCount(File file , String str,ExecutorService pool)
    {
        this.dir = file;
        this.key = str;
        this.count = 0;
        this.pool = pool;
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
                    MatchCount count = new MatchCount(file,key,pool);
                    results.add(pool.submit(count));
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
    private ExecutorService pool;
}

