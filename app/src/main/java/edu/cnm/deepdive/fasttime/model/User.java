package edu.cnm.deepdive.fasttime.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(
    indices = {
        @Index(value = {"email"}, unique = true)
    }
)
/**
 * A User class that allows access and saving of users and email addresses.
 */
public class User {

  @PrimaryKey(autoGenerate = true)
  private long id;

  private String email;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


}
