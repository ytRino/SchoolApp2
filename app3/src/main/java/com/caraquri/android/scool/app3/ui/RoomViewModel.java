package com.caraquri.android.scool.app3.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.caraquri.android.scool.app3.data.entity.User;
import java.util.List;

class RoomViewModel extends ViewModel {

  public void insert(String first, String last) {
    User user = new User(first, last);
  }

  public void delete(int position) {

  }

  public LiveData<List<User>> getUsers() {
    return null;
  }
}
