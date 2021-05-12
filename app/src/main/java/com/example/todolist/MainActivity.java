package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.todolist.Adapter.ToDoAdapter;
import com.example.todolist.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /**
     * RecyclerView = a container for UI
     */
    private RecyclerView tasksRecyclerView;
    private ToDoAdapter taskAdapter;
    private List<ToDoModel> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide(); // To hide the topmost application bar in the app

        /*
         * Define taskList
         */
        taskList = new ArrayList<>();

        tasksRecyclerView = findViewById(R.id.taskRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*
         * Set adapter to a recyclerview
         */
        taskAdapter = new ToDoAdapter(this);
        tasksRecyclerView.setAdapter(taskAdapter);

        /*
         * Dummy Data
         */
        ToDoModel task = new ToDoModel();
        task.setTask("Sample tasks ...");
        task.setStatus(0);
        task.setId(1);

        /*
         * Add task to the list
         */
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);

        /*
         * Update Recyclerview to show the dummy tasks above
         */
        taskAdapter.setTasks(taskList);
    }
}