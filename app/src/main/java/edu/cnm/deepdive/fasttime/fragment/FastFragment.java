package edu.cnm.deepdive.fasttime.fragment;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;
import edu.cnm.deepdive.fasttime.R;
import edu.cnm.deepdive.fasttime.fragment.DateTimePickerFragment.Mode;
import edu.cnm.deepdive.fasttime.fragment.DateTimePickerFragment.OnChangeListener;
import edu.cnm.deepdive.fasttime.model.FastDatabase;
import edu.cnm.deepdive.fasttime.model.FastTimer;
import java.util.Calendar;
import java.util.List;

/**
 * A subclass fragment for Fast Timer Lifecycle
 */
public class FastFragment extends Fragment {

  private ListView fastList;
  private TextView timeElapsed;
  private ToggleButton fast;
  private Calendar calendar;
  private boolean checkedByCode;
  private boolean running;
  private long previousElapsedTime;
  private long fastStart;
  private long totalDuration;
  private Timer monitor;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_fast, null);
    fastList = view.findViewById(R.id.fast_list);
    timeElapsed = view.findViewById(R.id.time_remaining);
    timeElapsed.setText(getString(R.string.fast_time, 0, 0, 0));
    fast = view.findViewById(R.id.fast);

    fast.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!checkedByCode) {
          if (isChecked) {
            checkedByCode = true;
            fast.setChecked(false);
            calendar = Calendar.getInstance();
            DateTimePickerFragment fragment = new DateTimePickerFragment();
            fragment.setMode(Mode.DATE);
            fragment.setPassthrough(false);
            fragment.setCalendar(calendar);
            fragment.setOnChangeListener(new DateChangeListener());
            fragment.show(getFragmentManager(), fragment.getClass().getName());
          } else {
            running = false;
            new FastInsert().execute(System.currentTimeMillis());
          }
        } else {
          checkedByCode = false;
        }
      }
    });
    new GetTimers(fastList).execute(getContext());
    return view;
  }

  private void update() {
    long now = System.currentTimeMillis();
    long elapsedTime = now - fastStart;
    long seconds = Math.round(elapsedTime / 1000d);
    long minutes = seconds / 60;
    long hours = minutes / 60;
    minutes %= 60;
    seconds %= 60;
    timeElapsed.setText(getString(R.string.fast_time, hours, minutes, seconds));
  }

  private class DateChangeListener implements OnChangeListener {

    @Override
    public void onChange(Calendar calendar) {
      DateTimePickerFragment fragment = new DateTimePickerFragment();
      fragment.setMode(Mode.TIME);
      fragment.setPassthrough(false);
      fragment.setCalendar(calendar);
      fragment.setOnChangeListener(new TimeChangeListener(calendar));
      fragment.show(getFragmentManager(), fragment.getClass().getName());
    }
  }

  private class TimeChangeListener implements OnChangeListener {

    private Calendar dateCalendar;

    public TimeChangeListener(Calendar dateCalendar) {
      this.dateCalendar = dateCalendar;
    }

    @Override
    public void onChange(Calendar calendar) {
      checkedByCode = true;
      fast.setChecked(true);
      dateCalendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
      dateCalendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
      fastStart = dateCalendar.getTimeInMillis();
      // TODO compute fast end time.
      running = true;
      monitor = new Timer();
      monitor.start();
    }
  }

  private static class GetTimers extends AsyncTask<Context, Void, List<FastTimer>> {

    private Context context;
    private ListView listView;

    private GetTimers(ListView listView) {
      this.listView = listView;
    }

    @Override
    protected List<FastTimer> doInBackground(Context... contexts) {
      this.context = contexts[0];
      return FastDatabase.getInstance(contexts[0]).getFastTimerDao().select();
    }

    @Override
    protected void onPostExecute(List<FastTimer> fastTimers) {
      ArrayAdapter<FastTimer> adapter = new ArrayAdapter<>(
          context, android.R.layout.simple_list_item_1, fastTimers);
      listView.setAdapter(adapter);
      listView = null;
      context = null;
    }

    @Override
    protected void onCancelled(List<FastTimer> fastTimers) {
      super.onCancelled(fastTimers);
      listView = null;
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

  private class FastInsert extends AsyncTask<Long, Void, Long> {

    @Override
    protected Long doInBackground(Long... longs) {
      FastTimer fastTimer = new FastTimer();
      fastTimer.setCompleted(true);
      fastTimer.setStart(fastStart);
      fastTimer.setStop(longs[0]);
      return FastDatabase.getInstance(getContext()).getFastTimerDao().insert(fastTimer);
    }

    @Override
    protected void onPostExecute(Long aLong) {
      new GetTimers(fastList).execute(getContext());
    }
  }

}

