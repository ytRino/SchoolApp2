package com.caraquri.android.schoolapp.ui.main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.caraquri.android.schoolapp.R;
import com.caraquri.android.schoolapp.databinding.HandlerFragmentBinding;
import io.reactivex.Observable;

public class HandlerFragment extends Fragment {

  private HandlerFragmentBinding binding;

  private Handler handler;

  public HandlerFragment() {
    super(R.layout.handler_fragment);
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    binding = HandlerFragmentBinding.bind(view);

    handler = new Handler(Looper.getMainLooper());

    binding.button.setOnClickListener(v -> {
      new Thread(new Runnable() {
        @Override public void run() {
          String result = someHeavyTask();
          //TextView textView = createTextView(result);
          //binding.log.addView(textView);
          handler.post(new Runnable() {
            @Override public void run() {
              TextView textView = createTextView(result);
              binding.log.addView(textView);
            }
          });
        }
      }).start();
    });
  }

  private String someHeavyTask() {
    return "Time: " + System.currentTimeMillis();
  }

  private TextView createTextView(String text) {
    TextView textView = new TextView(getContext());
    textView.setText(text);
    return textView;
  }
}
