package com.caraquri.android.scool.app3.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.caraquri.android.scool.app3.data.App3Database;
import com.caraquri.android.scool.app3.data.TodoDao;
import com.caraquri.android.scool.app3.data.entity.Todo;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RoomViewModel extends ViewModel {

  // LiveData等を返さない普通のDaoメソッドはメインスレッドで実行すると例外が発生する
  // とりあえずinsert,deleteを別スレッドで実行させる
  private final Executor notRecommended = Executors.newSingleThreadExecutor();

  private final TodoDao todoDao;

  private LiveData<List<Todo>> todos;

  public RoomViewModel(TodoDao todoDao) {
    this.todoDao = todoDao;
  }

  public void insert(String title, String date) {
    Todo todo = new Todo(date, title);
    notRecommended.execute(() -> todoDao.insert(todo));
  }

  public void delete(int position) {
    notRecommended.execute(() -> todoDao.delete(todos.getValue().get(position)));
  }

  public LiveData<List<Todo>> getTodos() {
    if (todos == null) {
      // LiveDataを返すDaoメソッドは自動的にバックグラウンドスレッドでDBへのクエリが実行される
      todos = todoDao.todos();
    }
    return todos;
  }

  /**
   * ViewModelProviderにわたす、RoomViewModelを生成するためのクラス
   */
  public static class Factory implements ViewModelProvider.Factory {

    private final App3Database db;

    public Factory(App3Database db) {
      this.db = db;
    }

    @NonNull @Override public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
      //noinspection unchecked
      return (T) new RoomViewModel(db.todoDao());
    }
  }
}
