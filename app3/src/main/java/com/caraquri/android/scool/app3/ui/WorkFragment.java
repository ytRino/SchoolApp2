package com.caraquri.android.scool.app3.ui;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import com.caraquri.android.scool.app3.R;
import com.caraquri.android.scool.app3.databinding.WorkFragmentBinding;
import com.caraquri.android.scool.app3.worker.UploadWorker;

public class WorkFragment extends Fragment {

  public WorkFragment() {
    super(R.layout.work_fragment);
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    WorkFragmentBinding binding = WorkFragmentBinding.bind(view);
    binding.work.setOnClickListener(v -> {
      WorkRequest request = new OneTimeWorkRequest.Builder(UploadWorker.class).build();
      WorkManager.getInstance(requireContext()).enqueue(request);
    });
    binding.work.setOnClickListener(v -> {
      Constraints constraints = new Constraints.Builder() //
          .setRequiredNetworkType(NetworkType.UNMETERED) //
          .setRequiresCharging(true) //
          .build();
      WorkRequest request = new OneTimeWorkRequest.Builder(UploadWorker.class) //
          .setConstraints(constraints) //
          .build();
      WorkManager.getInstance(requireContext()).enqueue(request);
    });
  }
}
