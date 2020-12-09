package com.caraquri.android.app4.ui.main;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import com.caraquri.android.app4.R;
import com.caraquri.android.app4.data.entity.Todo;
import com.caraquri.android.app4.databinding.MainFragmentBinding;
import dagger.hilt.android.AndroidEntryPoint;
import java.util.List;

/**
 * 4-4<br>
 * Dagger Hilt版
 * ✔ @HiltAndroidApp @AndroidEntryPoint @ViewModelInject
 * @Inject @Provides @Binds
 */
@AndroidEntryPoint
public class MainFragment extends Fragment {

  private TodoAdapter adapter;

  public static MainFragment newInstance() {
    return new MainFragment();
  }

  public MainFragment() {
    super(R.layout.main_fragment);
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    MainFragmentBinding binding = MainFragmentBinding.bind(view);

    // Get ViewModel!
    MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);

    TodoAdapter.OnButtonClickListener onDeleteClick = position -> {
      // 削除
      viewModel.delete(position);
    };

    adapter = new TodoAdapter(onDeleteClick);
    binding.list.setAdapter(adapter);

    binding.button.setOnClickListener(v -> {
      // 追加
      String title = binding.inputTitle.getText().toString();
      String date = binding.inputDate.getText().toString();
      viewModel.insert(title, date);
    });

    binding.list.setAdapter(adapter);

    // 更新の監視
    LiveData<List<Todo>> todos = viewModel.getTodos();
    todos.observe(getViewLifecycleOwner(), list -> adapter.submitList(list));
  }
}
