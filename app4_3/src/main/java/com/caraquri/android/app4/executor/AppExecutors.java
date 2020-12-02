package com.caraquri.android.app4.executor;

import java.util.concurrent.Executor;

public class AppExecutors {

  /**
   * IO処理を実行するExecutor
   */
  public final Executor ioExecutor;

  /**
   * Mainスレッドで処理を実行するExecutor
   */
  public final Executor mainExecutor;

  public AppExecutors(Executor io, Executor main) {
    ioExecutor = io;
    mainExecutor = main;
  }
}
