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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import edu.cnm.deepdive.fasttime.R;
import edu.cnm.deepdive.fasttime.model.FastDatabase;
import edu.cnm.deepdive.fasttime.model.GymTimer;
import java.util.List;

public class GymFragment extends Fragment {

  private Spinner timerSelect;
  private GymTimer timer;
  private TextView timeRemaining;
  private TextView progress;
  private ListView stepList;


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_gym, null);
    timerSelect = view.findViewById(R.id.timer_select);
    timeRemaining = view.findViewById(R.id.time_remaining);
    progress = view.findViewById(R.id.progress);
    stepList = view.findViewById(R.id.step_list);
    timerSelect.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        timer = (GymTimer) parent.getItemAtPosition(position);
        progress.setText(getString(R.string.progress, 0, timer.getRound()));
        int totalSeconds =
            timer.getRound() * timer.getWork() + (timer.getRound() - 1) * timer.getRest();
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        timeRemaining.setText(getString(R.string.time_remaining, minutes, seconds));
        String[] progressSteps = new String[2 * timer.getRound() - 1];

        int workMinutes = timer.getWork() / 60;
        int workSeconds = timer.getWork() % 60;
        int restMinutes = timer.getRest() / 60;
        int restSeconds = timer.getRest() % 60;

        for (int i = 0; i < timer.getRound(); i++) {
          progressSteps[2 * i] = getString(R.string.progress_step, i + 1, "Work", workMinutes,
              workSeconds);
          if (i < timer.getRound() -1) {
            progressSteps[2 * i + 1] = getString(R.string.progress_step, i + 1, "Rest", restMinutes,
                restSeconds);
          }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, progressSteps);
        stepList.setAdapter(adapter);
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
    new GetTimers(timerSelect).execute(getActivity());

    return view;
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

}

