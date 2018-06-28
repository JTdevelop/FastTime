package edu.cnm.deepdive.fasttime.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import java.sql.Date;


@Entity(
    indices = {
        @Index(value = {"work"}, unique = true),
        @Index(value = {"rest"}, unique = true),
        @Index(value = {"round"}, unique = true),
        @Index(value = {"timer_name"}, unique = true)
    }
)

public class GymTimer {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "timer_name")
    private int work;
    private int rest;
    private int round;

    @NonNull
    @ColumnInfo(name = "timer_name")
    private String TimerName;


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
    public String getTimerName() {
        return TimerName;
    }

    public void setTimerName(@NonNull String timerName) {
        TimerName = timerName;
    }
}
