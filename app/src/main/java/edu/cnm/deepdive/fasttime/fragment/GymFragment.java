package edu.cnm.deepdive.fasttime.fragment;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;
import edu.cnm.deepdive.fasttime.R;
import edu.cnm.deepdive.fasttime.model.FastDatabase;
import edu.cnm.deepdive.fasttime.model.GymTimer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 *  A subclass fragment for Fast Timer Lifecycle
 */
public class GymFragment extends Fragment {

  private Spinner timerSelect;
  private GymTimer timer;
  private TextView timeRemaining;
  private TextView progress;
  private ListView stepList;
  private ToggleButton run;
  private boolean running;
  private long previousElapsedTime;
  private long lastStartTime;
  private long totalDuration;
  private ArrayAdapter<String> stepsAdapter;
  private List<String> progressSteps;
  private Timer monitor;


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_gym, null);
    timerSelect = view.findViewById(R.id.timer_select);
    timeRemaining = view.findViewById(R.id.time_remaining);
    progress = view.findViewById(R.id.progress);
    stepList = view.findViewById(R.id.step_list);
    run = view.findViewById(R.id.fast);
    timerSelect.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        timer = (GymTimer) parent.getItemAtPosition(position);
        progress.setText(getString(R.string.progress, 0, timer.getRound()));
        int totalSeconds =
            timer.getRound() * timer.getWork() + (timer.getRound() - 1) * timer.getRest();
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        totalDuration = totalSeconds * 1000L;
        timeRemaining.setText(getString(R.string.time_remaining, minutes, seconds));
        progressSteps = new ArrayList<>();

        int workMinutes = timer.getWork() / 60;
        int workSeconds = timer.getWork() % 60;
        int restMinutes = timer.getRest() / 60;
        int restSeconds = timer.getRest() % 60;

        for (int i = 0; i < timer.getRound(); i++) {
          progressSteps.add(getString(R.string.progress_step, i + 1, "Work", workMinutes,
              workSeconds));
          if (i < timer.getRound() - 1) {
            progressSteps.add(getString(R.string.progress_step, i + 1, "Rest", restMinutes,
                restSeconds));
          }

        }

        stepsAdapter = new ArrayAdapter<>(getActivity(),
            R.layout.gym_timer_item, progressSteps);
        stepList.setAdapter(stepsAdapter);
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
    run.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
          startMonitor();
        } else {
          stopMonitor();
        }
      }
    });
    new GetTimers(timerSelect).execute(getActivity());

    return view;
  }

  private void stopMonitor() {
    previousElapsedTime += System.currentTimeMillis();
    previousElapsedTime -= lastStartTime;
    running = false;
    timerSelect.setEnabled(true);
    run.setChecked(false);
    update();
  }

  private void startMonitor() {
    timerSelect.setEnabled(false);
    lastStartTime = System.currentTimeMillis();
    running = true;
    monitor = new Timer();
    monitor.start();
  }

  private void update() {
    long totalElapsedTime = previousElapsedTime;
    if (running) {
      totalElapsedTime += System.currentTimeMillis();
      totalElapsedTime -= lastStartTime;
    }
    long totalRemaining = totalDuration - totalElapsedTime;
    if (totalRemaining <= 0 && running) {
      stopMonitor();
    }
    int secondsRemaning = (int) (totalRemaining / 1000);
    int minutesRemaning = secondsRemaning / 60;
    secondsRemaning %= 60;
    timeRemaining.setText(getString(R.string.time_remaining, minutesRemaning, secondsRemaning));
    int updateRound = timer.getRound() - 1;
    int offset = 0;
    while (totalRemaining > 0) {
      if (totalRemaining <= timer.getWork() * 1000) {
        secondsRemaning = (int) (totalRemaining / 1000);
        minutesRemaning = secondsRemaning / 60;
        secondsRemaning %= 60;
        int position = stepsAdapter.getCount() - 1 - offset;
        stepsAdapter.remove(stepsAdapter.getItem(position));
        stepsAdapter
            .insert(getString(R.string.progress_step, updateRound + 1, "Work", minutesRemaning,
                secondsRemaning), position);
        break;
      }

      offset++;
      updateRound--;
      totalRemaining -= timer.getWork() * 1000;

      if (totalRemaining <= timer.getRest() * 1000) {
        secondsRemaning = (int) (totalRemaining / 1000);
        minutesRemaning = secondsRemaning / 60;
        secondsRemaning %= 60;
        int position = stepsAdapter.getCount() - 1 - offset;
        stepsAdapter.remove(stepsAdapter.getItem(position));
        stepsAdapter
            .insert(getString(R.string.progress_step, updateRound + 1, "Rest", minutesRemaning,
                secondsRemaning), position);
        break;
      }

      offset++;
      totalRemaining -= timer.getRest() * 1000;
    }
    for (int i = 0; i < stepsAdapter.getCount() - 1 - offset; i++) {
      stepsAdapter.remove(stepsAdapter.getItem(0));
    }
    progress.setText(getString(R.string.progress, updateRound + 1, timer.getRound()));
  }

  private static class GetTimers extends AsyncTask<Context, Void, List<GymTimer>> {

    private Spinner spinner;
    private Context context;

    private GetTimers(Spinner spinner) {
      this.spinner = spinner;
    }

    @Override
    protected List<GymTimer> doInBackground(Context... contexts) {
      this.context = contexts[0];
      return FastDatabase.getInstance(contexts[0]).getGymTimerDao().select();
    }

    @Override
    protected void onPostExecute(List<GymTimer> gymTimers) {
      ArrayAdapter<GymTimer> adapter = new ArrayAdapter<>(
          context, android.R.layout.simple_list_item_1, gymTimers);
      spinner.setAdapter(adapter);
      spinner = null;
      context = null;
    }

    @Override
    protected void onCancelled(List<GymTimer> gymTimers) {
      super.onCancelled(gymTimers);
      spinner = null;
      context = null;
    }
  }

  private class Timer extends Thread {

    @Override
    public void run() {
      while (running) {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          // DO NOTHING!
        }
        getActivity().runOnUiThread(new Runnable() {
          @Override
          public void run() {
            update();
          }
        });
      }
    }
  }

}

