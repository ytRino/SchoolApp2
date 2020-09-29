package com.caraquri.android.scool.app3.ui;

import android.os.Handler;
import android.os.Looper;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * 3秒後に現在時刻をセットするタスク<br>
 * セットされた値は{@link TimerTask#getTimeLiveData}で取得する
 */
class TimerTask {

  private MutableLiveData<Long> resultLiveData = new MutableLiveData<>();

  private Handler handler = new Handler(Looper.getMainLooper());

  public void execute() {
    // 3秒後に現在時刻をLiveDataに設定する
    handler.postDelayed(() -> resultLiveData.setValue(System.currentTimeMillis()), 3000);
  }

  public LiveData<Long> getTimeLiveData() {
    return resultLiveData;
  }
}
