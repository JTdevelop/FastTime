package edu.cnm.deepdive.fasttime.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(
    indices = {
        @Index(value = {"name"}, unique = true)
    }
)
public class GymTimer {

  // Maybe add a description column?

  @PrimaryKey(autoGenerate = true)
  private long id;

  private int work;

  private int rest;

  private int round;

  @NonNull
  @ColumnInfo(collate = ColumnInfo.NOCASE)
  private String name;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public int getWork() {
    return work;
  }

  public void setWork(int work) {
    this.work = work;
  }

  public int getRest() {
    return rest;
  }

  public void setRest(int rest) {
    this.rest = rest;
  }

  public int getRound() {
    return round;
  }

  public void setRound(int round) {
    this.round = round;
  }

  @NonNull
  public String getName() {
    return name;
  }

  public void setName(@NonNull String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return getName();
  }

}
