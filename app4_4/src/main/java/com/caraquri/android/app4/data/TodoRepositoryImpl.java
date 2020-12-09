package com.caraquri.android.app4.data;

import androidx.lifecycle.LiveData;
import com.caraquri.android.app4.data.entity.Todo;
import com.caraquri.android.app4.executor.AppExecutors;
import java.util.List;
import javax.inject.Inject;

public class TodoRepositoryImpl implements TodoRepository {

  private final TodoDao todoDao;

  private final AppExecutors executors;

  // TodoDaoをコンストラクタで受け取るようにしたことでContextやRoomへの直接の依存がなくなった
  @Inject
  public TodoRepositoryImpl(TodoDao todoDao, AppExecutors executors) {
    this.todoDao = todoDao;
    this.executors = executors;
  }

  @Override public LiveData<List<Todo>> getAll() {
    // LiveDataを返すDaoメソッドは自動的にバックグラウンドスレッドでDBへのクエリが実行される
    return todoDao.todos();
  }

  @Override public void insert(Todo todo) {
    executors.ioExecutor.execute(() -> todoDao.insert(todo));
  }

  @Override public void delete(Todo todo) {
    executors.ioExecutor.execute(() -> todoDao.delete(todo));
  }
}
