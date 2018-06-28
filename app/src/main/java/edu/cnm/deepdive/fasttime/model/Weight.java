package edu.cnm.deepdive.fasttime.model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import java.sql.Timestamp;

@Entity(
    indices = {
        @Index(value = {"Id"}, unique = true),
        @Index(value = {"Weight"}, unique = true),
        @Index(value = {"timestamp"}, unique = true)
    }
)

public class Weight {

  private long id;
  private float weight;
  private long Timestamp;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public float getWeight() {
    return weight;
  }

  public void setWeight(float weight) {
    this.weight = weight;
  }
}
