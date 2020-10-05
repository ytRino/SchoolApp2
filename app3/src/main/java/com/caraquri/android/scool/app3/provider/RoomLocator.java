package com.caraquri.android.scool.app3.provider;

import android.content.Context;
import androidx.room.Room;
import com.caraquri.android.scool.app3.data.App3Database;
import java.util.concurrent.Executors;

public class RoomLocator {

  private RoomLocator() {
    // private
  }

  public static App3Database getDatabase(Context appContext) {
    return Room.databaseBuilder(appContext, App3Database.class, "app3")
        .fallbackToDestructiveMigration()
        .setTransactionExecutor(Executors.newSingleThreadExecutor())
        .build();
  }
}
