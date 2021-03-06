package edu.cnm.deepdive.fasttime.controller;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import com.facebook.stetho.Stetho;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import edu.cnm.deepdive.fasttime.model.FastDatabase;
import edu.cnm.deepdive.fasttime.model.GymTimer;

/**
 * This subclass of Application has fields for google sign in.
 * It includes on OnCreate method that will request google sign in.
 */
public class FastTimeApplication extends Application {

  private GoogleSignInClient signInClient;
  private GoogleSignInAccount signInAccount;

  @Override
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
    new ForceDBCreation().execute(this);
    GoogleSignInOptions options = new GoogleSignInOptions.Builder()
        .requestEmail()
        .requestId()
//        .requestIdToken("904153293464-0qc36597j6ie0htqdol3bu96404pftvn.apps.googleusercontent.com")
        .build();
    signInClient = GoogleSignIn.getClient(this, options);
  }

  /**
   * This method accesses the GoogleSignInClient
   * @return returns the Client to sign in
   */
  public GoogleSignInClient getSignInClient() {
    return signInClient;
  }

  public void setSignInClient(GoogleSignInClient signInClient) {
    this.signInClient = signInClient;
  }

  /**
   * This method accesses the GoogleSignInAccount
   * @return this method returns the sign in account
   */
  public GoogleSignInAccount getSignInAccount() {
    return signInAccount;
  }

  public void setSignInAccount(GoogleSignInAccount signInAccount) {
    this.signInAccount = signInAccount;
  }

  private static class ForceDBCreation extends AsyncTask<Context, Void, GymTimer> {

    @Override
    protected GymTimer doInBackground(Context... contexts) {
      GymTimer timer = FastDatabase.getInstance(contexts[0]).getGymTimerDao()
          .selectByName("tabata");
      return timer;
    }
  }

}
