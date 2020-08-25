package com.caraquri.android.schoolapp.ui.main;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.caraquri.android.schoolapp.R;
import com.caraquri.android.schoolapp.databinding.MainFragmentBinding;

public class MainFragment extends Fragment {

  public MainFragment() {
    super(R.layout.main_fragment);
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    MainFragmentBinding binding = MainFragmentBinding.bind(view);

    binding.viewThread.setOnClickListener(v -> open(new ViewThreadFragment()));
    binding.anr.setOnClickListener(v -> open(new AnrFragment()));
  }

  private void open(Fragment fragment) {
    getFragmentManager().beginTransaction()
        .replace(R.id.container, fragment)
        .addToBackStack(null)
        .commit();
  }
}
