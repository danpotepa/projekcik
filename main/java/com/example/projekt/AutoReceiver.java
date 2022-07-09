package com.example.projekt;

import static android.content.Context.ACTIVITY_SERVICE;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AutoReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isRunning = false;
        String string = intent.getExtras().getString("extra");

      ActivityManager manager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {

       }
        Intent mIntent = new Intent(String.valueOf(context));
        if (string.equals("on") && !isRunning){
            context.startService(mIntent);
            Automation.activeAuto = intent.getExtras().getString("active");

        }else if(string.equals("off")){
            context.stopService(mIntent);
            Automation.activeAuto = "";
        }
    }
}
