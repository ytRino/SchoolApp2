package com.caraquri.android.app4.ui.main;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.caraquri.android.app4.data.entity.Todo;
import com.caraquri.android.app4.databinding.ItemUserBinding;
import java.util.Locale;

public class TodoAdapter extends ListAdapter<Todo, TodoAdapter.ViewHolder> {

  private final OnButtonClickListener onButtonClickListener;

  protected TodoAdapter(@NonNull OnButtonClickListener listener) {
    super(new DiffCallback());
    onButtonClickListener = listener;
  }

  @NonNull @Override public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ItemUserBinding binding =
        ItemUserBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
    ViewHolder viewHolder = new ViewHolder(binding);
    binding.delete.setOnClickListener(v -> {
      int position = viewHolder.getAdapterPosition();
      // 追加削除アニメーション中にNO_POSITIONになる場合がある
      if (position != RecyclerView.NO_POSITION) {
        onButtonClickListener.onClick(position);
      }
    });
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

    public void bind(Todo todo) {
      String content =
          String.format(Locale.US, "ID: %d 期限: %s\n%s", todo.uid, todo.deadline, todo.title);
      binding.content.setText(content);
    }
  }

  /**
   * リストが更新されたときに差分を計算するクラス
   */
  static class DiffCallback extends DiffUtil.ItemCallback<Todo> {

    @Override public boolean areItemsTheSame(@NonNull Todo oldItem, @NonNull Todo newItem) {
      return oldItem.uid == newItem.uid;
    }

    @Override public boolean areContentsTheSame(@NonNull Todo oldItem, @NonNull Todo newItem) {
      return oldItem.equals(newItem);
    }
  }

  public interface OnButtonClickListener {
    void onClick(int position);
  }
}
