package com.caraquri.android.scool.app3.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.caraquri.android.scool.app3.data.entity.Todo;
import java.util.List;

public class RoomViewModel extends ViewModel {

  public void insert(String first, String last) {
    Todo todo = new Todo(first, last);
  }

  public void delete(int position) {

  }

  public LiveData<List<Todo>> getUsers() {
    return null;
  }
}
