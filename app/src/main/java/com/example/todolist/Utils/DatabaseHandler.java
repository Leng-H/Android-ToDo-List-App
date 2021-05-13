package com.example.todolist.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.todolist.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles with deleting and adding tasks
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int VERSION = 1; // To define the version of the current database
    private static final String NAME = "toDoListDatabase"; // Name of the database
    private static final String TODO_TABLE = "todo";
    private static final String ID = "id";
    private static final String TASK = "task";
    private static final String STATUS = "status";
    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + TODO_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TASK + "TEXT, " + STATUS + " INTEGER)";
    private SQLiteDatabase db;

    private DatabaseHandler(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the older tables
        db.execSQL(new StringBuilder().append("DROP TABLE IF EXISTS").append(TODO_TABLE).toString());
        // Recreate the table
        onCreate(db);
    }

    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    public void insertTask(ToDoModel task) {
        ContentValues cv = new ContentValues();
        cv.put(TASK, task.getTask());
        cv.put(STATUS, 0); // Make 0 as the default for all new task
        db.insert(TODO_TABLE, null, cv);
    }

    public List<ToDoModel> getAllTasks() {
        List<ToDoModel> taskList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
            try{
                cur = db.query(TODO_TABLE, null, null, null, null, null, null);
                if (cur != null) {
                    if (cur.moveToFirst()) {
                        do {
                            ToDoModel task = new ToDoModel();
                            task.setId(cur.getInt(cur.getColumnIndex(ID)));
                            task.setTask(cur.getString(cur.getColumnIndex(TASK)));
                            task.setStatus(cur.getInt(cur.getColumnIndex(STATUS)));
                            taskList.add(task);
                        } while (cur.moveToNext());
                    }
                }
            } finally {
                db.endTransaction();
                cur.close();
            }
        return taskList;
    }

    public void updateStatus(int id, int statusVal) {
        ContentValues cv = new ContentValues();
        cv.put(STATUS, statusVal);
        db.update(TODO_TABLE, cv, ID + "=?", new String[] {String.valueOf(id)});
    }

    public void updateTask(int id, String task) {
        ContentValues cv = new ContentValues();
        cv.put(TASK, task);
        db.update(TODO_TABLE, cv, ID + "=?", new String[] {String.valueOf(id)});
    }

    public void deleteTask(int id) {
        db.delete(TODO_TABLE, ID + "=?", new String[] {String.valueOf(id)});
    }
}
