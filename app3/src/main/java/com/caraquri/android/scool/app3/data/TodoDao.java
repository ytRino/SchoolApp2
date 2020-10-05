package com.caraquri.android.scool.app3.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.caraquri.android.scool.app3.data.entity.Todo;
import java.util.List;

@Dao
public interface TodoDao {

  @Query("select * from todo order by uid desc") LiveData<List<Todo>> todos();

  @Query("select * from todo where date = :date ") LiveData<List<Todo>> findByDate(String date);

  @Insert long insert(Todo todo);

  @Update(onConflict = OnConflictStrategy.REPLACE) void update(Todo todo);

  @Delete void delete(Todo todo);
}
