package com.example.a8todolist;

import java.util.Comparator;

import com.example.a8todolist.Job;

class JobComparator implements Comparator<Job>
{
    public int compare(Job a, Job b)
    {
        return Integer.valueOf(a.end).compareTo(Integer.valueOf(b.end));
    }
}
