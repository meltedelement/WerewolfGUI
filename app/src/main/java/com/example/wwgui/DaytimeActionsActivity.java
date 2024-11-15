package com.example.wwgui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.wwgui.gameLogic.Player;
import java.util.ArrayList;

public class DaytimeActionsActivity extends AppCompatActivity {

    private ArrayList<Player> players;
    private LinearLayout linearLayoutPlayerButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daytime_actions);

        TextView textViewInstructions = findViewById(R.id.textViewDaytimeInstructions);
        linearLayoutPlayerButtons = findViewById(R.id.linearLayoutPlayerButtons);

        // Retrieve players list from intent
        players = (ArrayList<Player>) getIntent().getSerializableExtra("players");

        // Display buttons for each player
        displayPlayerButtons();
    }

    private void displayPlayerButtons() {
        for (Player player : players) {
            Button playerButton = new Button(this);
            playerButton.setText(player.getName());

            playerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Mark player as dead
                    player.kill();
                    // Optionally disable button after killing player
                    playerButton.setEnabled(false);
                }
            });

            linearLayoutPlayerButtons.addView(playerButton);
        }
    }
}
