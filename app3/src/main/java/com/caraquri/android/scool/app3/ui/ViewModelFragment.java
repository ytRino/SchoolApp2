package com.caraquri.android.scool.app3.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.caraquri.android.scool.app3.R;
import com.caraquri.android.scool.app3.databinding.ViewmodelFragmentBinding;

public class ViewModelFragment extends Fragment {

  public ViewModelFragment() {
    super(R.layout.viewmodel_fragment);
  }

  private VmExampleViewModel viewModel;

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    ViewmodelFragmentBinding binding = ViewmodelFragmentBinding.bind(view);

    viewModel = new ViewModelProvider(this).get(VmExampleViewModel.class);

    viewModel.getTime()
        .observe(getViewLifecycleOwner(), time -> binding.getRoot().addView(createTextView(time)));

    binding.button.setOnClickListener(v -> viewModel.updateTime());
  }

  private TextView createTextView(long time) {
    // LiveDataFragmentではFragmentにもっていたbaseTimeをViewModelにもっていった理由は?
    // LiveDataFragmentのようにFragmentでbaseTimeを設定するとどうなるか?
    long resultTime = time - viewModel.baseTime; // LiveDataにセットされた値(時刻)
    long currentTime = System.currentTimeMillis() - viewModel.baseTime; // 現在時刻
    long delayedTime = currentTime - resultTime; // 差分(LiveDataに値がセットされてから現在時刻を取得するまでのズレ)
    TextView textView = new TextView(requireContext());
    textView.setText("Current Value: "
        + resultTime
        + ", Current time: "
        + currentTime
        + ", diff: "
        + delayedTime);
    return textView;
  }
}
