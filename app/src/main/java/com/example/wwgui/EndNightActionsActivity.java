package com.example.wwgui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.wwgui.gameLogic.Player;
import com.example.wwgui.gameLogic.Game;
import java.util.ArrayList;

public class EndNightActionsActivity extends AppCompatActivity {

    private ArrayList<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_night_actions);

        LinearLayout linearLayoutPlayersDied = findViewById(R.id.linearLayoutPlayersDied);
        LinearLayout linearLayoutPlayersSilenced = findViewById(R.id.linearLayoutPlayersSilenced);
        LinearLayout linearLayoutPlayersHexed = findViewById(R.id.linearLayoutPlayersHexed);
        LinearLayout linearLayoutPlayersDoused = findViewById(R.id.linearLayoutPlayersDoused);
        Button buttonProceedToDaytime = findViewById(R.id.buttonProceedToDaytime);
        Game gaming = new Game();
        // Retrieve players list from intent
        players = (ArrayList<Player>) getIntent().getSerializableExtra("players");

        ArrayList<Player> recentDeaths = new ArrayList<>();
        recentDeaths = gaming.processDeaths(players);

        // Display statuses
        ArrayList<Player> finalRecentDeaths = recentDeaths;
        displayPlayers(linearLayoutPlayersDied, "Died", player -> finalRecentDeaths.contains(player));
        displayPlayers(linearLayoutPlayersSilenced, "Silenced", player -> player.getSilenced());
        displayPlayers(linearLayoutPlayersHexed, "Hexed", player -> player.getHexed());
        displayPlayers(linearLayoutPlayersDoused, "Doused", player -> player.getDoused());

        // Proceed to daytime actions
        buttonProceedToDaytime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EndNightActionsActivity.this, DaytimeActionsActivity.class);
                intent.putExtra("players", players); // Pass updated players
                startActivity(intent);
                finish();
            }
        });
    }

    private void displayPlayers(LinearLayout layout, String title, PlayerCondition condition) {
        for (Player player : players) {
            if (condition.test(player)) {
                TextView playerTextView = new TextView(this);
                playerTextView.setText(player.getName() + " (" + player.getRole() + ")");
                layout.addView(playerTextView);
            }
        }
    }

    // Functional interface for player condition
    private interface PlayerCondition {
        boolean test(Player player);
    }
}
