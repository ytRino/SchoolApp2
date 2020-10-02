package com.caraquri.android.scool.app3.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.caraquri.android.scool.app3.data.entity.User;
import com.caraquri.android.scool.app3.databinding.ItemUserBinding;
import java.util.Locale;

public class UserAdapter extends ListAdapter<User, UserAdapter.ViewHolder> {

  private final OnItemClickListener onItemClickListener;

  protected UserAdapter(@NonNull OnItemClickListener listener) {
    super(new DiffCallback());
    onItemClickListener = listener;
  }

  @NonNull @Override public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ItemUserBinding binding = ItemUserBinding.inflate(LayoutInflater.from(parent.getContext()));
    ViewHolder viewHolder = new ViewHolder(binding);
    binding.delete.setOnClickListener(
        v -> onItemClickListener.onClick(viewHolder.getAdapterPosition()));
    return viewHolder;
  }

  @Override public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.bind(getItem(position));
  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    private final ItemUserBinding binding;

    public ViewHolder(ItemUserBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    public void bind(User user) {
      String content =
          String.format(Locale.US, "ID: %d\nfirst: %s, last: %s", user.uid, user.firstName,
              user.lastName);
      binding.content.setText(content);
    }
  }

  /**
   * リストが更新されたときに差分を計算するクラス
   */
  static class DiffCallback extends DiffUtil.ItemCallback<User> {

    @Override public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
      return oldItem.uid == newItem.uid;
    }

    @Override public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
      return oldItem.equals(newItem);
    }
  }

  public interface OnItemClickListener {
    void onClick(int position);
  }
}
