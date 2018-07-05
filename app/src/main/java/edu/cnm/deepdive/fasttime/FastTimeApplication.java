package edu.cnm.deepdive.fasttime;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import com.facebook.stetho.Stetho;
import edu.cnm.deepdive.fasttime.model.FastDatabase;
import edu.cnm.deepdive.fasttime.model.GymTimer;
import java.util.List;

public class FastTimeApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
    new ForceDBCreation().execute(this);
  }

  private static class ForceDBCreation extends AsyncTask<Context, Void, GymTimer> {

    @Override
    protected GymTimer doInBackground(Context... contexts) {
      GymTimer timer = FastDatabase.getInstance(contexts[0]).getGymTimerDao().selectByName("tabata");
      return timer;
    }

  }

}
