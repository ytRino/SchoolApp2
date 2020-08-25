package com.caraquri.android.schoolapp.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.caraquri.android.schoolapp.R;
import com.caraquri.android.schoolapp.databinding.AnrFragmentBinding;

public class AnrFragment extends Fragment {

  public AnrFragment() {
    super(R.layout.anr_fragment);
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    AnrFragmentBinding binding = AnrFragmentBinding.bind(view);

    binding.anr.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        int count = 0;
        while (true) {
          Log.d("Thread", Thread.currentThread().toString() + ", " + count);
          try {
            Thread.sleep(1000);
            count++;
          } catch (InterruptedException e) {
            Log.e("Thread", "InterruptedException", e);
          }
        }
      }
    });
  }
}
