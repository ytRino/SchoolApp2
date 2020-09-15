package com.caraquri.android.schoolapp.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import com.caraquri.android.schoolapp.R;
import com.caraquri.android.schoolapp.databinding.RxFragmentBinding;

public class LiveDataFragment extends Fragment {

  private MutableLiveData<String> data = new MutableLiveData<>();

  public LiveDataFragment() {
    super(R.layout.rx_fragment);
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    RxFragmentBinding binding = RxFragmentBinding.bind(view);

    binding.button.setOnClickListener(v -> //
        new Thread(() -> {
          String s = heavyTask();
          data.postValue(s);
        }).start());

    data.observe(getViewLifecycleOwner(), s -> {
      TextView textView = createTextView(s);
      binding.log.addView(textView);
    });
  }

  private String heavyTask() {
    return "Click! " + System.currentTimeMillis();
  }

  private TextView createTextView(String text) {
    TextView textView = new TextView(getContext());
    textView.setText(text);
    return textView;
  }
}
