package com.example.projekt;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Calendar;
import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private List<Auto> autoList;
    private LayoutInflater layoutInflater;

    public CustomAdapter(Context c, List<Auto> autoList) {
        this.context = c;
        this.autoList = autoList;
        layoutInflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return autoList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.item, null);
        final Auto selectedAuto = autoList.get(position);
        final TextView nameTV = convertView.findViewById(R.id.nameTextView);
        final TextView alarmTV = convertView.findViewById(R.id.timeTextView);
        final AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        nameTV.setText(selectedAuto.getName());
        alarmTV.setText((selectedAuto.toString()));

        final Intent serviceIntent = new Intent(context, AutoReceiver.class);

        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, selectedAuto.getHour());
        calendar.set(Calendar.MINUTE, selectedAuto.getMinute());
        calendar.set(Calendar.SECOND, 0);
        if (calendar.getTimeInMillis() < System.currentTimeMillis()){
            calendar.add(Calendar.DATE, 1);
        }
        ToggleButton toggleButton = convertView.findViewById(R.id.toggle);
        toggleButton.setChecked(selectedAuto.getStatus());
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                selectedAuto.setStatus(isChecked);
                DatabaseHelper db = new DatabaseHelper(context);
                db.updateAuto(selectedAuto);

                Automation.autoList.clear();
                List <Auto> list = db.getAllAuto();
                Automation.autoList.addAll(list);
                notifyDataSetChanged();

                if (!isChecked && selectedAuto.toString().equals(Automation.activeAuto)){
                    serviceIntent.putExtra("extra", "off");
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, position, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.cancel(pendingIntent);
                    context.sendBroadcast(serviceIntent);

                }
            }
        });

        return convertView;
    }
}
