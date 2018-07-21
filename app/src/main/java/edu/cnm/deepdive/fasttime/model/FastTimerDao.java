package edu.cnm.deepdive.fasttime.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.provider.ContactsContract.CommonDataKinds.Note;
import java.util.List;

@Dao
public interface FastTimerDao {

  @Insert
  long insert(FastTimer timer);

  @Insert
  List<Long> insert(List<FastTimer> timers);

  @Insert
  List<Long> insert(FastTimer... timers);

  @Query("SELECT * FROM FastTimer ORDER BY stop DESC")
  List<FastTimer> select();

  @Query("SELECT * FROM FastTimer WHERE id = :id")
  FastTimer selectById(long id);

}
