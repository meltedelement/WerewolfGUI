package com.example.wwgui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class EndNightActionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_night_actions);

        TextView textViewSummary = findViewById(R.id.textViewSummary);
        Button buttonProceedToDay = findViewById(R.id.buttonProceedToDay);

        // Set up the button click listener
        buttonProceedToDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to DaytimeActionsActivity
                Intent intent = new Intent(EndNightActionsActivity.this, DaytimeActionsActivity.class);
                startActivity(intent);
                finish(); // Close this activity
            }
        });
    }
}
