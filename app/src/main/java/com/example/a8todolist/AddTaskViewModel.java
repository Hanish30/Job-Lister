package com.example.a8todolist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import Database.AppDatabase;
import Database.TaskEntry;

public class AddTaskViewModel extends ViewModel {
    private LiveData<TaskEntry> task;
    public AddTaskViewModel(AppDatabase database,int taskId)
    {
        task=database.taskDao().loadTaskById(taskId);
    }
    public LiveData<TaskEntry> getTask()
    {
        return task;
    }
}
