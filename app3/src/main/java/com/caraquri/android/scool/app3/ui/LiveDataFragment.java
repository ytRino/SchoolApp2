package com.caraquri.android.scool.app3.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import com.caraquri.android.scool.app3.R;
import com.caraquri.android.scool.app3.databinding.LivedataFragmentBinding;
import com.caraquri.android.scool.app3.task.TimerTask;

public class LiveDataFragment extends Fragment {

  public LiveDataFragment() {
    super(R.layout.livedata_fragment);
  }

  private long baseTime = System.currentTimeMillis();

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    LivedataFragmentBinding binding = LivedataFragmentBinding.bind(view);

    TimerTask task = new TimerTask();
    LiveData<Long> timeLiveData = task.getTimeLiveData();

    // timeLiveDataの変更を監視して、
    // timeLiveDataにセットされた時刻、変更を受け取った時刻、その差を表示
    timeLiveData.observe(getViewLifecycleOwner(),
        time -> binding.getRoot().addView(createTextView(time)));

    // ボタンを押すと3秒カウントして時刻をLiveDataにセット
    binding.button.setOnClickListener(v -> task.execute());
  }

  private TextView createTextView(long time) {
    long resultTime = time - baseTime; // LiveDataにセットされた値(時刻)
    long currentTime = System.currentTimeMillis() - baseTime; // 現在時刻
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
