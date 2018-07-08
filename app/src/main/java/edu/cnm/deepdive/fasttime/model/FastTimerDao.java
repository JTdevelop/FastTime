package edu.cnm.deepdive.fasttime.model;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.provider.ContactsContract.CommonDataKinds.Note;
import java.util.List;

public interface FastTimerDao {

  @Insert
  long insert(FastTimer timer);

  @Insert
  List<Long> insert(List<FastTimer> timers);

  @Insert
  List<Long> insert(FastTimer... timers);

  @Insert
  long insert(Note note);


  @Query("SELECT * FROM FastTimer ORDER BY name ASC")
  List<FastTimer> select();

  @Query("SELECT * FROM FastTimer WHERE id = :id")
  FastTimer selectById(long id);

  @Query("SELECT * FROM FastTimer WHERE name = :name")
  FastTimer selectByName(String name);

}
