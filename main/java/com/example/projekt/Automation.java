package com.example.projekt;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Automation extends AppCompatActivity {

    public static  String activeAuto = "";
    private ListView listView;

    private static final int REQUEST_CODE = 1000;
    public static List<Auto> autoList = new ArrayList<>();
    private CustomAdapter customAdapter;
    private DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.automation);

        Button button = findViewById(R.id.add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Automation.this, AddActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        Button button2 = findViewById(R.id.Cancel);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Automation.this, MainActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        listView = findViewById(R.id.ListView);
        List <Auto> list = db.getAllAuto();
        autoList.addAll(list);
        customAdapter = new CustomAdapter(getApplicationContext(), autoList);
        listView.setAdapter(customAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            boolean needRefresh = data.getExtras().getBoolean("needRefresh");
            if(needRefresh){
                autoList.clear();
                List <Auto> list = db.getAllAuto();
                autoList.addAll(list);
                customAdapter.notifyDataSetChanged();
            }
        }
    }


}
