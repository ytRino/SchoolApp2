package com.caraquri.android.scool.app3.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.caraquri.android.scool.app3.data.entity.Todo;

@Database(version = 1, entities = Todo.class)
public abstract class App3Database extends RoomDatabase {
  public abstract TodoDao todoDao();
}
