package com.caraquri.android.app4.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.caraquri.android.app4.data.TodoRepository;
import com.caraquri.android.app4.data.entity.Todo;
import java.util.List;

public class MainViewModel extends ViewModel {

  private final TodoRepository repository;

  private LiveData<List<Todo>> todos;

  // コンストラクタでTodoRepositoryを受け取ることで
  // ContextやTodoRepositoryImplに依存しなくなった
  public MainViewModel(TodoRepository repository) {
    this.repository = repository;
  }

  public void insert(String title, String date) {
    Todo todo = new Todo(date, title);
    repository.insert(todo);
  }

  public void delete(int position) {
    repository.delete(todos.getValue().get(position));
  }

  public LiveData<List<Todo>> getTodos() {
    if (todos == null) {
      todos = repository.getAll();
    }
    return todos;
  }

  /**
   * ViewModelProviderにわたす、MainViewModelを生成するためのクラス<br>
   * ViewModelは直接newせずViewModelProviderを通すため、Factoryが必要
   */
  public static class Factory implements ViewModelProvider.Factory {

    private final TodoRepository repository;

    public Factory(TodoRepository repository) {
      this.repository = repository;
    }

    @NonNull @Override public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
      //noinspection unchecked
      return (T) new MainViewModel(repository);
    }
  }
}
