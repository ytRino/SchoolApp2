package com.caraquri.android.scool.app3.ui;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.caraquri.android.scool.app3.R;
import com.caraquri.android.scool.app3.databinding.MainFragmentBinding;

public class MainFragment extends Fragment {

  public MainFragment() {
    super(R.layout.main_fragment);
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    MainFragmentBinding binding = MainFragmentBinding.bind(view);
    // navigation : 自分で画面遷移する場合
    binding.lifecycle.setOnClickListener(v -> open(new LifecycleFragment()));
    // navigation : NavControllerを使う場合
    binding.livedata.setOnClickListener(v -> open(R.id.action_main_to_livedata));
    // navigation : createNavigateOnClickListenerを使う場合
    binding.viewmodel.setOnClickListener(navigationListener(R.id.action_main_to_viewmodel));
    binding.room.setOnClickListener(navigationListener(R.id.action_main_to_room));
    binding.work.setOnClickListener(navigationListener(R.id.action_main_to_work));
  }

  private void open(Fragment fragment) {
    getParentFragmentManager().beginTransaction()
        .replace(R.id.nav_host_fragment, fragment)
        .addToBackStack(null)
        .commit();
  }

  private void open(@IdRes int actionResId) {
    Navigation.findNavController(getView()).navigate(actionResId);
  }

  private View.OnClickListener navigationListener(@IdRes int actionResId) {
    return Navigation.createNavigateOnClickListener(actionResId);
  }
}
