package com.example.wwgui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;

import com.example.wwgui.gameLogic.Game;
import com.example.wwgui.gameLogic.Launcher;
import com.example.wwgui.gameLogic.Player;
import com.example.wwgui.gameLogic.Roles;

public class PlayerActionActivity extends AppCompatActivity {

    private TextView textViewActivePlayerName;
    private TextView textViewActivePlayerRole;
    private LinearLayout linearLayoutPlayerButtons;
    private Game gameLogic;
    private ArrayList<Player> players;
    private ArrayList<Player> playersOrder; // Store the night action order
    private int currentPlayerIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_action);

        // Initialize views
        textViewActivePlayerName = findViewById(R.id.textViewActivePlayerName);
        textViewActivePlayerRole = findViewById(R.id.textViewActivePlayerRole);
        linearLayoutPlayerButtons = findViewById(R.id.linearLayoutPlayerButtons);

        // Initialize game logic
        gameLogic = new Game();

        // Get the ArrayList of Player objects from the intent
        players = (ArrayList<Player>) getIntent().getSerializableExtra("players");

        // Determine the night action order
        playersOrder = gameLogic.nightActions(players);

        // Start displaying the first player's actions
        displayPlayerActions();
    }

    private void displayPlayerActions() {
        // Check if there are remaining players to display actions for
        if (currentPlayerIndex < playersOrder.size()) {
            Player currentPlayer = playersOrder.get(currentPlayerIndex);

            // Display current player's name and role
            textViewActivePlayerName.setText("Active Player: " + currentPlayer.getName());
            textViewActivePlayerRole.setText("Role: " + currentPlayer.getRole());

            // Clear previous buttons
            linearLayoutPlayerButtons.removeAllViews();

            // Create a button for each other player
            for (Player otherPlayer : players) {
                if (!otherPlayer.getAlive()) continue; // Skip dead players

                Button playerButton = new Button(this);
                playerButton.setText(otherPlayer.getName());

                // Set click listener to handle player's action
                playerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Perform night action
                        currentPlayer.performNightAction(otherPlayer);

                        // Handle special Seer case
                        handleResponsePrompt(currentPlayer, otherPlayer);

                        // Move to the next player
                        currentPlayerIndex++;
                        displayPlayerActions(); // Recursive call for the next player
                    }
                });

                // Add the button to the layout
                linearLayoutPlayerButtons.addView(playerButton);
            }
            Button skipButton = new Button(this);
            skipButton.setText("Skip");
            skipButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                currentPlayerIndex++;
                displayPlayerActions();
                }
            });
            linearLayoutPlayerButtons.addView(skipButton);
        } else {
            // All players have completed their actions, move to EndNightActionsActivity
            Intent intent = new Intent(PlayerActionActivity.this, EndNightActionsActivity.class);
            intent.putExtra("players", players); // Pass the updated players list
            startActivity(intent);
        }
    }

    private void handleResponsePrompt(Player currentPlayer, Player selectedPlayer) {
        // Check if the current player is a Seer and the selected player is a Werewolf
        if (currentPlayer.getRole() == Roles.SEER && Arrays.asList(Player.seerVisibleRoles).contains(selectedPlayer.getRole())) {
            // Show a dialog to inform the Seer
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Seer Vision")
                    .setMessage(selectedPlayer.getName() + " is a Werewolf/Hexed/Arsonist!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss(); // Close the dialog
                        }
                    })
                    .show();
        }
    }
}
