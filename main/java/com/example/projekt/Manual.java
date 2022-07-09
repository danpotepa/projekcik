package com.example.projekt;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Manual extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual);
                Button button2 = findViewById(R.id.wroc);
                button2.setOnClickListener(v -> {

                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);

                });
}

}
