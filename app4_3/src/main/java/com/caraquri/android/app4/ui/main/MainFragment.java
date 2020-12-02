package com.caraquri.android.app4.ui.main;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;
import com.caraquri.android.app4.R;
import com.caraquri.android.app4.data.TodoDao;
import com.caraquri.android.app4.data.TodoDatabase;
import com.caraquri.android.app4.data.TodoRepository;
import com.caraquri.android.app4.data.TodoRepositoryImpl;
import com.caraquri.android.app4.data.entity.Todo;
import com.caraquri.android.app4.databinding.MainFragmentBinding;
import com.caraquri.android.app4.executor.AppExecutors;
import java.util.List;
import java.util.concurrent.Executors;
import org.jetbrains.annotations.NotNull;

/**
 * 4-2<br>
 * 役割を分割しMVVM+リポジトリパターンに移行した
 */
public class MainFragment extends Fragment {

  private TodoAdapter adapter;

  public static MainFragment newInstance() {
    return new MainFragment();
  }

  public MainFragment() {
    super(R.layout.main_fragment);
  }

  @Override
  public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    MainFragmentBinding binding = MainFragmentBinding.bind(view);

    // ViewModelに必要な依存 に必要な依存 に必要な…を生成
    TodoDatabase database =
        Room.databaseBuilder(requireContext().getApplicationContext(), TodoDatabase.class, "todo")
            .fallbackToDestructiveMigration()
            .build();
    TodoDao todoDao = database.todoDao();
    AppExecutors executors = new AppExecutors(Executors.newSingleThreadExecutor(), ContextCompat.getMainExecutor(requireContext()));
    TodoRepository repository = new TodoRepositoryImpl(todoDao, executors);
    MainViewModel.Factory factory = new MainViewModel.Factory(repository);

    // Get ViewModel!
    MainViewModel viewModel = new ViewModelProvider(this, factory).get(MainViewModel.class);

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
