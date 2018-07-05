package edu.cnm.deepdive.fasttime.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(
    indices = {
        @Index(value = {"id"}, unique = true),
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

  @NonNull
  public long getStart() {
    return start;
  }

  @NonNull
  public void setStart(long start) {
    this.start = start;
  }

  @NonNull
  public long getStop() {
    return stop;
  }

  @NonNull
  public void setStop(long stop) {
    this.stop = stop;
  }

  @NonNull
  public long getId() {
    return id;
  }

  @NonNull
  public void setId(long id) {
    this.id = id;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  @NonNull
  public boolean isCompleted() {
    return completed;
  }

  public void setCompleted(boolean completed) {
    this.completed = completed;
  }

}
