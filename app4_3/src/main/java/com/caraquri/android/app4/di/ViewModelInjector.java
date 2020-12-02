package com.caraquri.android.app4.di;

import com.caraquri.android.app4.ui.main.MainViewModel;

public class ViewModelInjector {

  private final Injector injector;

  public ViewModelInjector(Injector injector) {
    this.injector = injector;
  }

  public MainViewModel.Factory getMainViewModelFactory() {
    return new MainViewModel.Factory(injector.getTodoRepository());
  }
}
