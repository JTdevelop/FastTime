package edu.cnm.deepdive.fasttime.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;

@Dao
public interface GymTimerDao {

  @Insert
  long insert(GymTimer timer);

  @Insert
  List<Long> insert(List<GymTimer> timers);

  @Insert
  List<Long> insert(GymTimer... timers);

  @Query("SELECT * FROM GymTimer ORDER BY name ASC")
  List<GymTimer> select();

  @Query("SELECT * FROM GymTimer WHERE id = :id")
  GymTimer selectById(long id);

  @Query("SELECT * FROM GymTimer WHERE name = :name")
  GymTimer selectByName(String name);

}
