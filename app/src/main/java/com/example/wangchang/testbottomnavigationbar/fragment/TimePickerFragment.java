package com.example.wangchang.testbottomnavigationbar.fragment;

import android.app.Dialog;

import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.app.Dialog;
import android.app.DialogFragment;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.wangchang.testbottomnavigationbar.R;

import java.util.Calendar;

/**
 * Created by ACM on 2017/7/3.
 */

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener
{
    private EditText startTime;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //得到日期对象
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute, true);
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        startTime = (EditText)getActivity().findViewById(R.id.starttime);
        Toast.makeText(getActivity(), "当前时间设置为： " + hourOfDay + " : " + minute, Toast.LENGTH_LONG).show();
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);

    }
}