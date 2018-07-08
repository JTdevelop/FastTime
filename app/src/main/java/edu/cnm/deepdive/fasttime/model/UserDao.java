package edu.cnm.deepdive.fasttime.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;

@Dao
public interface UserDao {

  @Insert
  long insert(User user);

  @Query("SELECT * FROM User ORDER BY user DESC")
  List<User> select();

}
