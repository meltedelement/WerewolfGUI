package com.example.wwgui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import com.example.wwgui.gameLogic.Player;

public class DaytimeActionsActivity extends AppCompatActivity {

    private LinearLayout linearLayoutPlayerButtons;
    private TextView textViewSelectedPlayerInfo;
    private Button buttonContinue;
    private ArrayList<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daytime_actions);

        // Initialize views
        linearLayoutPlayerButtons = findViewById(R.id.linearLayoutPlayerButtons);
        textViewSelectedPlayerInfo = findViewById(R.id.textViewSelectedPlayerInfo);
        buttonContinue = findViewById(R.id.buttonContinue);

        // Get the list of players from the intent
        players = (ArrayList<Player>) getIntent().getSerializableExtra("players");

        // Populate buttons for each player
        displayPlayerButtons();

        // Initially hide the "Continue" button and player info text
        textViewSelectedPlayerInfo.setVisibility(View.GONE);
        buttonContinue.setVisibility(View.GONE);

        // Set up the "Continue" button to return to PlayerActionActivity
        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DaytimeActionsActivity.this, PlayerActionActivity.class);
                intent.putExtra("players", players);
                startActivity(intent);
                finish(); // Optional: finish this activity so it doesn't stay in the back stack
            }
        });
    }

    private void displayPlayerButtons() {
        // Clear previous buttons
        linearLayoutPlayerButtons.removeAllViews();

        for (Player player : players) {
            // Create a button for each player
            Button playerButton = new Button(this);
            playerButton.setText(player.getName());

            // Set up a click listener for the button
            playerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Display selected player's name and role
                    textViewSelectedPlayerInfo.setText("Name: " + player.getName() + "\nRole: " + player.getRole());
                    textViewSelectedPlayerInfo.setVisibility(View.VISIBLE);

                    // Show the "Continue" button
                    buttonContinue.setVisibility(View.VISIBLE);

                    // Perform the action to "kill" the player
                    player.kill();
                }
            });

            // Add the button to the layout
            linearLayoutPlayerButtons.addView(playerButton);
        }
    }
}
