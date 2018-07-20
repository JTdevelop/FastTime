package edu.cnm.deepdive.fasttime.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(
    indices = {
        @Index(value = {"user_id", "timestamp"}, unique = true)
    },
    foreignKeys = {
        @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "user_id")
    }
)

/**
 *  A class for for users to enter their weight.
 */
public class Weight {

  @PrimaryKey(autoGenerate = true)
  private long id;

  @ColumnInfo(name = "user_id", index = true)
  private long userId;

  @ColumnInfo(index = true)
  private float weight;

  private long timestamp;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public float getWeight() {
    return weight;
  }

  public void setWeight(float weight) {
    this.weight = weight;
  }

}
