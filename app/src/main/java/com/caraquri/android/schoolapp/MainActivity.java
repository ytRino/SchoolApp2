package com.caraquri.android.schoolapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.caraquri.android.schoolapp.ui.main.MainFragment;

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
