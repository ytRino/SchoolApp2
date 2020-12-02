package com.caraquri.android.app4.ui.main;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.caraquri.android.app4.data.TodoRepository;
import com.caraquri.android.app4.data.TodoRepositoryImpl;
import com.caraquri.android.app4.data.entity.Todo;
import java.util.List;

public class MainViewModel extends ViewModel {

  private TodoRepository repository;

  private LiveData<List<Todo>> todos;

  public void setUp(Context context) {
    // ViewModelがContextに依存している
    // ViewModelがTodoRepositoryImplに依存している
    // ViewModelはContextに依存したくない
    // ViewModelはTodoRepositoryImpl(詳細)に依存したくない
    // ViewModelはTodoRepository(抽象)に依存したい
    repository = new TodoRepositoryImpl(context);
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
}
