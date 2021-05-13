package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.todolist.Adapter.ToDoAdapter;
import com.example.todolist.Model.ToDoModel;
import com.example.todolist.Utils.DatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogCloseListener {

    /**
     * RecyclerView = a container for UI
     */
    private RecyclerView tasksRecyclerView;
    private ToDoAdapter taskAdapter;
    private List<ToDoModel> taskList;
    private DatabaseHandler db;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide(); // To hide the topmost application bar in the app

        /*
         * Define taskList
         */
        taskList = new ArrayList<>();
        db = new DatabaseHandler(this);
        db.openDatabase();

        tasksRecyclerView = findViewById(R.id.taskRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*
         * Set adapter to a recyclerview
         */
        taskAdapter = new ToDoAdapter(db, this);
        tasksRecyclerView.setAdapter(taskAdapter);

//        /*
//         * Dummy Data
//         */
//        ToDoModel task = new ToDoModel();
//        task.setTask("Sample tasks ...");
//        task.setStatus(0);
//        task.setId(1);
//
//        /*
//         * Add task to the list
//         */
//        taskList.add(task);
//        taskList.add(task);
//
//        /*
//         * Update Recyclerview to show the dummy tasks above
//         */
//        taskAdapter.setTasks(taskList);

        fab = findViewById(R.id.fab);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(taskAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        taskAdapter.setTasks(taskList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });
    }

    @Override
    public void handleDialogClose(DialogInterface dialogInterface) {
        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        taskAdapter.setTasks(taskList);
        taskAdapter.notifyDataSetChanged();
    }
}