package com.caraquri.android.scool.app3;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import com.caraquri.android.scool.app3.ui.MainFragment;

public class MainActivity extends AppCompatActivity {

  public MainActivity() {
    super(R.layout.main_activity);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.container, new MainFragment())
          .commitNow();
    }
  }
}
