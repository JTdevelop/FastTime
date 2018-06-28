package edu.cnm.deepdive.fasttime.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import java.util.TimerTask;

@Entity(
    indices = {
        @Index(value = {"start"}, unique = true),
        @Index(value = {"stop"}, unique = true)
    }
)

public class FastTimer {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "fast_time_id")
  private long id;

  private long start;
  private long stop;

  public long getStart() {
    return start;
  }

  public void setStart(long start) {
    this.start = start;
  }

  public long getStop() {
    return stop;
  }

  public void setStop(long stop) {
    this.stop = stop;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

}
