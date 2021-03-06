package edu.cnm.deepdive.fasttime.controller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import edu.cnm.deepdive.fasttime.R;
import edu.cnm.deepdive.fasttime.fragment.FastFragment;
import edu.cnm.deepdive.fasttime.fragment.GymFragment;
import edu.cnm.deepdive.fasttime.fragment.NotificationsFragment;

/**
 * A subclass to handle the main activity.
 */
public class MainActivity extends AppCompatActivity
    implements OnNavigationItemSelectedListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    BottomNavigationView navigation = findViewById(R.id.navigation);
    navigation.setOnNavigationItemSelectedListener(this);

    loadFragment(new FastFragment());
  }

  private boolean loadFragment(Fragment fragment) {
    if (fragment != null) {

      getSupportFragmentManager()
          .beginTransaction()
          .replace(R.id.fragment_container, fragment)
          .commit();

      return true;
    }
    return false;
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

    Fragment fragment = null;

    switch (menuItem.getItemId()) {

      case R.id.navigation_gym:
        loadFragment(new GymFragment());
        break;

      case R.id.navigation_fast:
        loadFragment(new FastFragment());
        break;

      case R.id.navigation_notifications:
        loadFragment(new NotificationsFragment());
        break;
    }

    return false;
  }

}
