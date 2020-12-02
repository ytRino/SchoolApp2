package com.caraquri.android.app4;

import android.app.Application;
import com.caraquri.android.app4.di.Injector;

public class SchoolApp extends Application {

  // !! 通常はstatic変数は使わないこと !!
  private static Injector injector;

  public static Injector getInjector() {
    return injector;
  }

  @Override public void onCreate() {
    super.onCreate();

    injector = new Injector(this);
  }
}
