package com.caraquri.android.app4.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Todo {

  @PrimaryKey(autoGenerate = true)
  public int uid;

  @ColumnInfo(name = "date")
  public String deadline;

  @ColumnInfo
  public String title;

  public Todo(String deadline, String title) {
    this.deadline = deadline;
    this.title = title;
  }

  // auto generated equals and hashCode

  @Override public boolean equals(Object o) {
    if (this == o) { return true; }
    if (o == null || getClass() != o.getClass()) { return false; }

    Todo todo = (Todo) o;

    if (uid != todo.uid) { return false; }
    if (deadline != null ? !deadline.equals(todo.deadline) : todo.deadline != null) {
      return false;
    }
    return title != null ? title.equals(todo.title) : todo.title == null;
  }

  @Override public int hashCode() {
    int result = uid;
    result = 31 * result + (deadline != null ? deadline.hashCode() : 0);
    result = 31 * result + (title != null ? title.hashCode() : 0);
    return result;
  }
}
