package edu.cnm.deepdive.fasttime.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import java.util.TimerTask;

@Entity(
    indices = {
        @Index(value = {"stop"}, unique = true),
        @Index(value = {"start"}, unique = true),
        @Index(value = {"notes"}, unique = true),
        @Index(value = {"completed"}, unique = true)

    }
)

public class FastTimer {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "fast_time_id")
  private long id;
  private long time_remain;
  private long start;
  private long stop;
  private String note;
  private boolean completed;

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

  public long getTime_remain() {
    return time_remain;
  }

  public void setTime_remain(long time_remain) {
    this.time_remain = time_remain;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public boolean isCompleted() {
    return completed;
  }

  public void setCompleted(boolean completed) {
    this.completed = completed;
  }
  
}
