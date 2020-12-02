package com.caraquri.android.app4.data;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import com.caraquri.android.app4.data.entity.Todo;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TodoRepositoryImpl implements TodoRepository {

  private final TodoDao todoDao;

  private final Executor executor;

  public TodoRepositoryImpl(Context context) {
    todoDao =
        Room.databaseBuilder(context.getApplicationContext(), TodoDatabase.class, "todo")
            .fallbackToDestructiveMigration()
            .build()
            .todoDao();

    executor = Executors.newSingleThreadExecutor();
  }

  @Override public LiveData<List<Todo>> getAll() {
    // LiveDataを返すDaoメソッドは自動的にバックグラウンドスレッドでDBへのクエリが実行される
    return todoDao.todos();
  }

  @Override public void insert(Todo todo) {
    executor.execute(() -> todoDao.insert(todo));
  }

  @Override public void delete(Todo todo) {
    executor.execute(() -> todoDao.delete(todo));
  }
}
