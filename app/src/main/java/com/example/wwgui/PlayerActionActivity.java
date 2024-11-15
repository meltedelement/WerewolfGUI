package com.example.wwgui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import com.example.wwgui.gameLogic.Game;
import com.example.wwgui.gameLogic.Player;

public class PlayerActionActivity extends AppCompatActivity {

    private TextView textViewActivePlayerName;
    private TextView textViewActivePlayerRole;
    private LinearLayout linearLayoutPlayerButtons;
    private Button buttonProceedToDaytime;  // Button for proceeding to daytime actions
    private Game gameLogic;
    private ArrayList<Player> players;
    private int currentPlayerIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_action);

        textViewActivePlayerName = findViewById(R.id.textViewActivePlayerName);
        textViewActivePlayerRole = findViewById(R.id.textViewActivePlayerRole);
        linearLayoutPlayerButtons = findViewById(R.id.linearLayoutPlayerButtons);
        buttonProceedToDaytime = findViewById(R.id.buttonProceedToDaytime);  // Initialize button

        gameLogic = new Game();
        players = (ArrayList<Player>) getIntent().getSerializableExtra("players");

        ArrayList<Player> playersOrder = gameLogic.nightActions(players);
        displayPlayerActions(playersOrder);

        // Set up the proceed button
        buttonProceedToDaytime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlayerActionActivity.this, DaytimeActionsActivity.class);
                intent.putExtra("players", players);  // Pass players if needed
                startActivity(intent);
            }
        });
    }

    private void displayPlayerActions(ArrayList<Player> playersOrder) {
        if (currentPlayerIndex < playersOrder.size()) {
            Player currentPlayer = playersOrder.get(currentPlayerIndex);


            textViewActivePlayerName.setText("Active Player: " + currentPlayer.getName());
            textViewActivePlayerRole.setText("Role: " + currentPlayer.getRole());

            linearLayoutPlayerButtons.removeAllViews();

            for (Player otherPlayer : players) {
                if (otherPlayer.getAlive()){
                    Button playerButton = new Button(this);
                    playerButton.setText(otherPlayer.getName());

                    playerButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            currentPlayer.performNightAction(otherPlayer);
                            displayNextPlayer(playersOrder);  // Move to the next player
                        }
                    });

                    linearLayoutPlayerButtons.addView(playerButton);
                }
            }
        } else {
            Intent intent = new Intent(PlayerActionActivity.this, EndNightActionsActivity.class);
            intent.putExtra("players", players); // Pass the updated players list if needed
            startActivity(intent);
            finish();
        }
    }

    private void displayNextPlayer(ArrayList<Player> playersOrder) {
        currentPlayerIndex++;
        displayPlayerActions(playersOrder);
    }
}
