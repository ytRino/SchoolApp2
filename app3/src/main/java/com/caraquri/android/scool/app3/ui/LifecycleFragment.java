package com.caraquri.android.scool.app3.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.caraquri.android.scool.app3.R;
import com.caraquri.android.scool.app3.databinding.LifecycleFragmentBinding;
import com.caraquri.android.scool.app3.task.LoopTask;

public class LifecycleFragment extends Fragment {

  public LifecycleFragment() {
    super(R.layout.lifecycle_fragment);
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    LifecycleFragmentBinding binding = LifecycleFragmentBinding.bind(view);

    // タスクで定期的にTextViewを追加する。
    // タスクを停止しないと、FragmentがDestroy状態になったときにクラッシュ
    LoopTask.TaskCallback taskCallback = time -> binding.getRoot().addView(createTextView(time));
    // タスクをライフサイクルに連動させる
    getLifecycle().addObserver(new LoopTask(taskCallback));
  }

  private TextView createTextView(long time) {
    TextView textView = new TextView(requireContext());
    textView.setText("Task " + time);
    return textView;
  }
}
