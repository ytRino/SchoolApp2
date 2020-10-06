package com.caraquri.android.scool.app3.worker;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class UploadWorker extends Worker {

  public UploadWorker(@NonNull Context context, @NonNull WorkerParameters params) {
    super(context, params);
  }

  @NonNull @Override public Result doWork() {
    uploadImages();
    Log.i("UploadWorker", "Work finished.");
    return Result.success();
  }

  private void uploadImages() {
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      Log.e("UploadWorker", "Sleep interrupted!", e);
    }
  }
}
