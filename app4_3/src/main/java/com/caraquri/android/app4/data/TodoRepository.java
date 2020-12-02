package com.caraquri.android.app4.data;

import androidx.lifecycle.LiveData;
import com.caraquri.android.app4.data.entity.Todo;
import java.util.List;

public interface TodoRepository {

    /**
     * リストの更新を受け取る事ができるTODOリストのデータ
     */
    LiveData<List<Todo>> getAll();

    void insert(Todo todo);

    /**
     * TODOを削除する
     */
    void delete(Todo todo);
}
