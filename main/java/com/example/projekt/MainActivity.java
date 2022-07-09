package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button = findViewById(R.id.buttonM);
        button.setOnClickListener(v -> {

            Intent intent = new Intent(this, Manual.class);
                    startActivity(intent);

        });
        Button button2 = findViewById(R.id.buttonA);
        button2.setOnClickListener(v -> {

            Intent intent = new Intent(this, Automation.class);
            startActivity(intent);

        });
    }}

