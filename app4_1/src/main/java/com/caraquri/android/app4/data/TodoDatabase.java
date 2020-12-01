package com.caraquri.android.app4.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.caraquri.android.app4.data.entity.Todo;

@Database(version = 1, entities = Todo.class)
public abstract class TodoDatabase extends RoomDatabase {
  public abstract TodoDao todoDao();
}
