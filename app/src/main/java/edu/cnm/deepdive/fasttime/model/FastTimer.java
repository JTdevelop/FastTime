package edu.cnm.deepdive.fasttime.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import java.util.Date;


@Entity(
    indices = {
        @Index(value = {"user_id", "start", "stop"}),
        @Index(value = {"user_id", "start"}, unique = true),
        @Index(value = {"user_id", "completed"})
    }
)
/**
 *  A class to handle the duration of daily Fasts
 */
public class FastTimer {

  @PrimaryKey(autoGenerate = true)
  private long id;

  @ColumnInfo(name = "user_id", index = true)
  private long userId;

  private long start;
  private long stop;
  private String note;
  private boolean completed;

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

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

  @Override
  public String toString() {
    Date startDate = new Date(start);
    Date stopDate = new Date(stop);
    long duration = stop - start;
    long hours = duration / 1000 / 3600;
    long minutes = duration / 1000 / 60 % 60;

    return String.format("%1$02d:%2$02d", hours, minutes);
  }
}
