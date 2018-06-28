package edu.cnm.deepdive.fasttime.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(
    indices = {
        @Index(value = {"last_name", "first_name"}, unique = true),
        @Index(value = {"display_name"}, unique = true),
        @Index(value = {"calendar"}, unique = true),
        @Index(value = {"weight"}, unique = true)



    }
)

public class User {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
