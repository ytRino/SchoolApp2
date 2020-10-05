package com.caraquri.android.scool.app3.ui;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import com.caraquri.android.scool.app3.R;
import com.caraquri.android.scool.app3.data.entity.Todo;
import com.caraquri.android.scool.app3.databinding.RoomFragmentBinding;
import com.caraquri.android.scool.app3.provider.RoomLocator;
import java.util.List;

public class RoomFragment extends Fragment {

  private RoomViewModel viewModel;

  public RoomFragment() {
    super(R.layout.room_fragment);
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    RoomFragmentBinding binding = RoomFragmentBinding.bind(view);

    RoomViewModel.Factory factory = new RoomViewModel.Factory(
        RoomLocator.getDatabase(requireActivity().getApplicationContext()));
    viewModel = new ViewModelProvider(this, factory).get(RoomViewModel.class);

    TodoAdapter.OnButtonClickListener onDeleteClick = position -> viewModel.delete(position);
    TodoAdapter adapter = new TodoAdapter(onDeleteClick);
    binding.list.setAdapter(adapter);

    binding.button.setOnClickListener(v -> viewModel.insert( //
        binding.inputTitle.getText().toString(), //
        binding.inputDate.getText().toString()) //
    );

    LiveData<List<Todo>> todos = viewModel.getTodos();
    todos.observe(getViewLifecycleOwner(), list -> adapter.submitList(list));
  }
}
