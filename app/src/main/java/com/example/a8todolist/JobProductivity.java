package com.example.a8todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

public class JobProductivity extends AppCompatActivity {
   private static EditText messageBox_name_of_activity;
   private static EditText messageBox_starttime_of_activity;
    private static EditText messageBox_endtime_of_activity;
    private static EditText messageBox_profit_of_activity;
    private Button submitBtn;
    private Button clearBtn;
    private String output_text;
    private String outputString;
    private static TextView output;
    private static int names_len;
    private static int starttime_len;
    private static int endtime_len;
    private static int profits_len;
    private static boolean rightInputGiven=true;
    private static String string_names[];
    private static String string_starttime[];
    private static String string_endtime[];
    private static String string_profit[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_productivity);
        messageBox_name_of_activity=(EditText)findViewById(R.id.messageBox_of_name_of_activity);
        messageBox_starttime_of_activity=(EditText) findViewById(R.id.messageBox_of_starttime_of_activity);
        messageBox_endtime_of_activity=(EditText) findViewById(R.id.messageBox_of_endtime_of_activity);
        messageBox_profit_of_activity=(EditText) findViewById(R.id.messageBox_of_profit_of_activity);
        Toast.makeText(this,"Job Scheduler suggest you the jobs which you should perform to acheive maximum profitability and increase productivity",Toast.LENGTH_LONG).show();
        output=(TextView) findViewById(R.id.output);
        submitBtn=(Button)findViewById(R.id.SubmitBtn);
        clearBtn=(Button) findViewById(R.id.ClearBtn);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        output.setText("Output will be displayed here");
         submitBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 getValues();
//                 Job jobs[]=convertToJobs();
//                 Job result=schedule(jobs);
                 if(names_len!=starttime_len||names_len!=endtime_len||names_len!=profits_len||profits_len!=starttime_len||profits_len!=endtime_len||
                 starttime_len!=endtime_len)
                 {
                     output.setText("You have not entered equal number of feilds");
                 }
                 checkInputs();
                 if(!rightInputGiven)
                 {
                     Toast.makeText(JobProductivity.this, "Error! wrong input given. You have not enetered number inside start time, end time or profit input box", Toast.LENGTH_LONG).show();
                     output.setText("You have not enetered number inside start time, end time or profit input box");
                 }
                 else
                 {
                     Job jobs[]=convertToJobs();
                     Job result=schedule(jobs);
                     output_text="The maximum profit will be "+String.valueOf(result.profit);
                     output_text+="\n"+"The order in which the jobs to be performed for maximum profit are >>>>"+"\n";
                     List<String> templist=result.list;
                     int m= templist.size();
                     for(int i=0;i<m-1;i++)
                     {
                         output_text+= templist.get(i)+" --> ";
                     }
                     output_text+=templist.get(m-1);
                     output.setText(output_text);
                 }

             }
         });
         clearBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 messageBox_name_of_activity.setText("");
                 messageBox_starttime_of_activity.setText("");
                 messageBox_endtime_of_activity.setText("");
                 messageBox_profit_of_activity.setText("");
                 output.setText("Output will be displayed here");
             }
         });

    }
    static Job schedule(Job jobs[])
    {
        Arrays.sort(jobs,new JobComparator());
        int n=jobs.length;
        Job dp[]=new Job[n];
        List<String> list=new ArrayList<>();
        List<String> mlist=new ArrayList<>();
        list.add(jobs[0].name);
        dp[0]=new Job(list,jobs[0].name,jobs[0].start,jobs[0].end,jobs[0].profit);
        for(int i=1;i<n;i++)
        {
            mlist=new ArrayList<>();
            int inclProf=jobs[i].profit;
            int l=binarySearch(jobs,i);
            if(l!=-1)
            {
                inclProf+=dp[l].profit;
                mlist.addAll(dp[l].list);
            }
            if(inclProf>dp[i-1].profit)
            {
                mlist.add(jobs[i].name);
                dp[i]=new Job(mlist,jobs[i].name,jobs[i].start,jobs[i].end,inclProf);
            }
            else{
                dp[i]=dp[i-1];
            }
        }
        return dp[n-1];
    }
    static int binarySearch(Job jobs[],int index)
    {
        int low=0;
        int high=index-1;
        while(low<=high)
        {
            int mid=low+(high-low)/2;
            if(jobs[mid].end<=jobs[index].start)
            {
                if(jobs[mid+1].end<=jobs[index].start)
                {
                    low=mid+1;
                }
                else{
                    return mid;
                }
            }
            else
            {
                high=mid-1;
            }
        }
        return -1;
    }
    static Job[] convertToJobs()
    {
//        string_names=messageBox_name_of_activity.getText().toString().trim().split("\\s");
//        string_starttime=messageBox_starttime_of_activity.getText().toString().trim().split("\\s");
//        string_endtime=messageBox_endtime_of_activity.getText().toString().trim().split("\\s");
//        string_profit = messageBox_profit_of_activity.getText().toString().trim().split("\\s");
        int n=string_starttime.length;
//        starttime_len=n;
//        endtime_len=string_endtime.length;
//        profits_len=string_profit.length;
//        names_len=string_names.length;
        int start[]=new int[n];
        int end[]=new int[n];
        int profit[]=new int[n];
        for(int i=0;i<n;i++)
        {
            start[i]=Integer.parseInt(string_starttime[i]);
            end[i]=Integer.parseInt(string_endtime[i]);
            profit[i]=Integer.parseInt(string_profit[i]);
        }
        Job jobs[]=new Job[n];
        List<String> list=new ArrayList<>();
        for(int i=0;i<n;i++)
        {
            jobs[i]=new Job(list,string_names[i],start[i], end[i], profit[i]);
        }
        return jobs;
    }
    static void getValues()
    {
        string_names=messageBox_name_of_activity.getText().toString().trim().split("\\s");
        string_starttime=messageBox_starttime_of_activity.getText().toString().trim().split("\\s");
        string_endtime=messageBox_endtime_of_activity.getText().toString().trim().split("\\s");
        string_profit = messageBox_profit_of_activity.getText().toString().trim().split("\\s");
        int n=string_names.length;
        starttime_len=string_starttime.length;
        endtime_len=string_endtime.length;
        profits_len=string_profit.length;
        names_len=string_names.length;
    }
    static void checkInputs()
    {
        int n=starttime_len;
        try{
            for(int i=0;i<n;i++)
            {
                int temp_variable=Integer.parseInt(string_starttime[i]);
                 temp_variable=Integer.parseInt(string_endtime[i]);
                 temp_variable=Integer.parseInt(string_profit[i]);
                 rightInputGiven=true;
            }
        }
        catch (NumberFormatException e)
        {
            rightInputGiven=false;
        }
    }
}