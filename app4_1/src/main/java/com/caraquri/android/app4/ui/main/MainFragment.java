package com.caraquri.android.app4.ui.main;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import com.caraquri.android.app4.R;
import com.caraquri.android.app4.data.TodoDao;
import com.caraquri.android.app4.data.TodoDatabase;
import com.caraquri.android.app4.data.entity.Todo;
import com.caraquri.android.app4.databinding.MainFragmentBinding;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.jetbrains.annotations.NotNull;

/**
 * 4-1<br>
 * ほぼFragmentに書いてある状態
 */
public class MainFragment extends Fragment {

  private TodoAdapter adapter;

  private Executor roomExecutor = Executors.newSingleThreadExecutor();

  public static MainFragment newInstance() {
    return new MainFragment();
  }

  public MainFragment() {
    super(R.layout.main_fragment);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    TodoDao todoDao =
        Room.databaseBuilder(requireContext().getApplicationContext(), TodoDatabase.class, "todo")
            .fallbackToDestructiveMigration()
            .build()
            .todoDao();

    MainFragmentBinding binding = MainFragmentBinding.bind(view);

    TodoAdapter.OnButtonClickListener onDeleteClick = position -> {
      // 削除
      Todo todo = adapter.getCurrentList().get(position);
      roomExecutor.execute(() -> todoDao.delete(todo));
    };

    adapter = new TodoAdapter(onDeleteClick);
    binding.list.setAdapter(adapter);

    binding.button.setOnClickListener(v -> {
      // 追加
      String title = binding.inputTitle.getText().toString();
      String date = binding.inputDate.getText().toString();
      Todo todo = new Todo(date, title);
      roomExecutor.execute(() -> todoDao.insert(todo));
    });

    binding.list.setAdapter(adapter);

    // 更新の監視
    LiveData<List<Todo>> todos = todoDao.todos();
    todos.observe(getViewLifecycleOwner(), list -> adapter.submitList(list));
  }
}
