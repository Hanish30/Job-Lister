package com.example.a8todolist;

import java.util.List;

public class Job {
     List<String> list;
     String name;
     int start;
     int end;
     int profit;
    Job(List<String> list,String name,int start,int end,int profit)
    {
        this.list=list;
        this.name=name;
        this.start=start;
        this.end=end;
        this.profit=profit;
    }
}
