package edu.cnm.deepdive.fasttime.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;

@Dao
public interface WeightDao {

  @Insert
  long insert(Weight weight);

  @Query("SELECT * FROM Weight ORDER BY timestamp DESC")
  List<Weight> select();

}
