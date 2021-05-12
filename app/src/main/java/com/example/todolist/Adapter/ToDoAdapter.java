package com.example.todolist.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.MainActivity;
import com.example.todolist.Model.ToDoModel;
import com.example.todolist.R;

import java.util.List;

/**
 * An adapter creates a ViewHolder that would then take an item from the list and show
 * it in a RecyclerView layout
 * - Adapter = manages collection and ViewHolder objects
 */

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    /**
     * Represents a collection of objects that you want to represent/show in the RecyclerView
     */
    private List<ToDoModel> todoList;
    private MainActivity mainActivity;

    public ToDoAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        ToDoModel item = todoList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    /*
     * Helper function to convert integer to boolean
     * - If n != 0, return TRUE
     * - FALSE otherwise
     */
    private boolean toBoolean(int n) {
        return n != 0;
    }

    public void setTasks(List<ToDoModel> todoList) {
        this.todoList = todoList;
        notifyDataSetChanged(); // To update the RecyclerView
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox task;
        ViewHolder(View view) {
            super(view);
            task = view.findViewById(R.id.todoCheckBox);
        }
    }
}
