package edu.cnm.deepdive.fasttime.model;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

/**
 * A database Subclass for the application to store data.
 */
@Database(entities = {User.class, Weight.class, FastTimer.class, GymTimer.class},
    version = FastDatabase.DATABASE_VERSION, exportSchema = true)
public abstract class FastDatabase extends RoomDatabase {

  private static final String DATABASE_NAME = "fast_time_db";
  static final int DATABASE_VERSION = 1;

  private static FastDatabase instance = null;

  public static FastDatabase getInstance(Context context) {
    if (instance == null) {
      instance = Room
          .databaseBuilder(context.getApplicationContext(), FastDatabase.class, DATABASE_NAME)
          .addCallback(new Callback(context)) // FIXME
          .build();
    }
    return instance;
  }

  public static void forgetInstance(Context context) {
    instance = null;
  }

  public abstract GymTimerDao getGymTimerDao();

  public abstract FastTimerDao getFastTimerDao();

  private static class Callback extends RoomDatabase.Callback {

    private Context context;

    private Callback(Context context) {
      this.context = context;
    }

    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db) {
      super.onCreate(db);
      new DataPrepopulate().execute(context);
    }

    @Override
    public void onOpen(@NonNull SupportSQLiteDatabase db) {
      super.onOpen(db);
    }
  }

  private static class DataPrepopulate extends AsyncTask<Context, Void, Void> {

    @Override
    protected Void doInBackground(Context... contexts) {
      FastDatabase database = FastDatabase.getInstance(contexts[0]);
      GymTimer timer = new GymTimer();
      timer.setName("Tabata");
      timer.setRound(7);
      timer.setWork(20);
      timer.setRest(10);
      database.getGymTimerDao().insert(timer);
      timer = new GymTimer();
      timer.setName("HIIT");
      timer.setRound(10);
      timer.setWork(30);
      timer.setRest(15);
      database.getGymTimerDao().insert(timer);
      return null;
    }
  }

}
