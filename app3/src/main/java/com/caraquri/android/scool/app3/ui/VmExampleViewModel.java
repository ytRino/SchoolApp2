package com.caraquri.android.scool.app3.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.caraquri.android.scool.app3.task.TimerTask;

public class VmExampleViewModel extends ViewModel {

  public final long baseTime = System.currentTimeMillis();

  private TimerTask task = new TimerTask();

  public void updateTime() {
    task.execute();
  }

  public LiveData<Long> getTime() {
    return task.getTimeLiveData();
  }
}
