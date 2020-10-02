package com.caraquri.android.scool.app3.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

  @PrimaryKey(autoGenerate = true)
  public int uid;

  @ColumnInfo(name = "first_name")
  public String firstName;

  @ColumnInfo(name = "last_name")
  public String lastName;

  public User(String first, String last) {
    firstName = first;
    lastName = last;
  }

  // auto generated equals and hashCode

  @Override public boolean equals(Object o) {
    if (this == o) { return true; }
    if (o == null || getClass() != o.getClass()) { return false; }

    User user = (User) o;

    if (uid != user.uid) { return false; }
    if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) {
      return false;
    }
    return lastName != null ? lastName.equals(user.lastName) : user.lastName == null;
  }

  @Override public int hashCode() {
    int result = uid;
    result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
    result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
    return result;
  }
}
