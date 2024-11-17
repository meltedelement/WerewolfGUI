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
    private ArrayList<Player> recentDeaths;

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

        if (recentDeaths != null) {
            recentDeaths.clear();
        }
        recentDeaths = gaming.processDeaths(players);

        // Display statuses
        displayRecentDeaths(linearLayoutPlayersDied); // Display only recent deaths
        displayPlayers(linearLayoutPlayersSilenced, player -> player.getSilenced());
        displayPlayers(linearLayoutPlayersHexed, player -> player.getHexed());
        displayPlayers(linearLayoutPlayersDoused, player -> player.getDoused());

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

    // Method to display recent deaths
    private void displayRecentDeaths(LinearLayout layout) {
        clearPlayerViews(layout); // Clear only player-specific views
        for (Player player : recentDeaths) {
            TextView playerTextView = new TextView(this);
            playerTextView.setText(player.getName() + " (" + player.getRole() + ")");
            layout.addView(playerTextView);
        }
    }

    private void displayPlayers(LinearLayout layout, PlayerCondition condition) {
        clearPlayerViews(layout); // Clear only player-specific views
        for (Player player : players) {
            if (condition.test(player)) {
                TextView playerTextView = new TextView(this);
                playerTextView.setText(player.getName() + " (" + player.getRole() + ")");
                layout.addView(playerTextView);
            }
        }
    }

    // Helper method to clear player-specific views while preserving the header
    private void clearPlayerViews(LinearLayout layout) {
        int childCount = layout.getChildCount();
        if (childCount > 1) {
            layout.removeViews(1, childCount - 1); // Remove all views except the first (header)
        }
    }

    // Functional interface for player condition
    private interface PlayerCondition {
        boolean test(Player player);
    }
}
