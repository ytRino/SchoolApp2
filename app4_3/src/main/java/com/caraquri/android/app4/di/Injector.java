package com.caraquri.android.app4.di;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.room.Room;
import com.caraquri.android.app4.data.TodoDao;
import com.caraquri.android.app4.data.TodoDatabase;
import com.caraquri.android.app4.data.TodoRepository;
import com.caraquri.android.app4.data.TodoRepositoryImpl;
import com.caraquri.android.app4.executor.AppExecutors;
import java.util.concurrent.Executors;

public class Injector {

  private final Context context;

  public Injector(Context context) {
    this.context = context;
  }

  // singleton
  private ViewModelInjector viewModelInjector;

  public ViewModelInjector getViewModelInjector() {
    if (viewModelInjector == null) {
      viewModelInjector = new ViewModelInjector(this);
    }
    return viewModelInjector;
  }

  // singleton
  private TodoRepository todoRepository;

  public TodoRepository getTodoRepository() {
    if (todoRepository == null) {
      todoRepository = new TodoRepositoryImpl(getTodoDao(), getAppExecutors());
    }
    return todoRepository;
  }

  // singleton
  private TodoDatabase database;

  public TodoDatabase getDatabase() {
    if (database == null) {
      database = Room.databaseBuilder(context, TodoDatabase.class, "todo")
          .fallbackToDestructiveMigration()
          .build();
    }
    return database;
  }

  public TodoDao getTodoDao() {
    return getDatabase().todoDao();
  }

  // singleton
  private AppExecutors appExecutors;

  public AppExecutors getAppExecutors() {
    if (appExecutors == null) {
      appExecutors = new AppExecutors(Executors.newSingleThreadExecutor(),
          ContextCompat.getMainExecutor(context));
    }
    return appExecutors;
  }
}
