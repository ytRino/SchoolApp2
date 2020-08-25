package com.caraquri.android.schoolapp.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.caraquri.android.schoolapp.R;
import com.caraquri.android.schoolapp.databinding.ViewThreadFragmentBinding;
import org.jetbrains.annotations.NotNull;

public class ViewThreadFragment extends Fragment {

  public static ViewThreadFragment newInstance() {
    return new ViewThreadFragment();
  }

  private ViewThreadFragmentBinding binding;

  public ViewThreadFragment() {
    super(R.layout.view_thread_fragment);
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    binding = ViewThreadFragmentBinding.bind(view);

    binding.addSync.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        addMessage2("メインスレッドで追加");
      }
    });
    binding.addAsync.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        new Thread(new Runnable() {
          @Override public void run() {
            addMessage2("別スレッドで追加");
          }
        }).start();
      }
    });
  }

  private void addMessage2(String text) {
    Log.d("Thread", Thread.currentThread().toString() + ", " + text);
  }

  private void addMessage(String text) {
    TextView textView = createTextView(text);
    binding.getRoot().addView(textView);
  }

  @NotNull private TextView createTextView(String text) {
    TextView textView = new TextView(getContext());
    textView.setText(text);
    return textView;
  }
}
