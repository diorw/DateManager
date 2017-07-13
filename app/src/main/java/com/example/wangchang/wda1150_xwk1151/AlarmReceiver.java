package com.example.wangchang.wda1150_xwk1151;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by ACM on 2017/7/9.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"设置闹铃的时间到了",Toast.LENGTH_LONG).show();
        Intent alarmIntent =new Intent(context,ClockActivity.class);
        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(alarmIntent);
    }
}
