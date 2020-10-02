package com.caraquri.android.scool.app3.task;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

/**
 * onResumeからonPauseのあいだまで動作する疑似タスククラス
 * <pre>
 * <code>
 *   lifecycle.addObserver(new Task(time -> yourTask()))
 * </code>
 * </pre>
 */
public class LoopTask implements DefaultLifecycleObserver {

  /**
   * タスクが動いている間定期的に実行する処理を登録するコールバッククラス
   */
  public interface TaskCallback {
    void doWork(long time);
  }

  public LoopTask(TaskCallback work) {
    this.work = work;
  }

  private TaskCallback work;

  private Handler handler = new Handler(Looper.getMainLooper());

  /**
   * 実行されたあと1秒後に自身を再実行するようhandlerに登録する。
   */
  private Runnable worker = new Runnable() {
    @Override public void run() {
      long time = System.currentTimeMillis() - start;
      Log.v("Task", "working! " + time);
      work.doWork(time);
      handler.postDelayed(this, 1000);
    }
  };

  private long start = 0;

  @Override public void onResume(@NonNull LifecycleOwner owner) {
    Log.v("Task", "onResume: starting task...");
    start = System.currentTimeMillis();
    // 仕事開始
    handler.postDelayed(worker, 1000);
  }

  @Override public void onPause(@NonNull LifecycleOwner owner) {
    Log.v("Task", "onPause: stopping task...");
    // 仕事終了
    // ⚠️ コメントアウトするとクラッシュorリーク
    // java.lang.IllegalStateException: Fragment LifecycleFragment… not attached to a context.
    handler.removeCallbacks(worker);
  }
}
