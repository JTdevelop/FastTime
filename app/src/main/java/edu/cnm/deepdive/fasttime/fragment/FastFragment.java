package edu.cnm.deepdive.fasttime.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;
import edu.cnm.deepdive.fasttime.R;
import edu.cnm.deepdive.fasttime.fragment.DateTimePickerFragment.Mode;
import edu.cnm.deepdive.fasttime.fragment.DateTimePickerFragment.OnChangeListener;
import java.util.Calendar;

public class FastFragment extends Fragment {

  private ListView fastList;
  private TextView timeRemaining;
  private ToggleButton fast;
  private Calendar calendar;
  private boolean checkedByCode;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_fast, null);
    fastList = view.findViewById(R.id.fast_list);
    timeRemaining = view.findViewById(R.id.time_remaining);
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
          }
        } else {
          checkedByCode = false;
        }
      }
    });
    return view;
  }

  private class DateChangeListener implements OnChangeListener {

    @Override
    public void onChange(Calendar calendar) {
      DateTimePickerFragment fragment = new DateTimePickerFragment();
      fragment.setMode(Mode.TIME);
      fragment.setPassthrough(false);
      fragment.setCalendar(calendar);
      fragment.setOnChangeListener(new TimeChangeListener());
      fragment.show(getFragmentManager(), fragment.getClass().getName());
    }
  }

  private class TimeChangeListener implements OnChangeListener {

    @Override
    public void onChange(Calendar calendar) {
      checkedByCode = true;
      fast.setChecked(true);
    }
  }

}

