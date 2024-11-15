package com.example.wwgui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import com.example.wwgui.gameLogic.Launcher;

public class takeInPlayerScreen extends AppCompatActivity {

    private EditText editTextPlayerName;
    private Button buttonAddPlayer;
    private Button buttonStartGame;
    private ListView listViewPlayers;
    private ArrayList<String> playerNames;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_in_player_screen);

        // Initialize views
        editTextPlayerName = findViewById(R.id.editTextPlayerName);
        buttonAddPlayer = findViewById(R.id.buttonAddPlayer);
        buttonStartGame = findViewById(R.id.buttonStartGame);
        listViewPlayers = findViewById(R.id.listViewPlayers);

        // Initialize the list and adapter
        playerNames = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, playerNames);
        listViewPlayers.setAdapter(adapter);

        // Set listener to add player
        buttonAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String playerName = editTextPlayerName.getText().toString().trim();
                if (!playerName.isEmpty()) {
                    playerNames.add(playerName);
                    adapter.notifyDataSetChanged(); // Refresh list view
                    editTextPlayerName.setText(""); // Clear input
                }
            }
        });

        // Set listener to start game
        buttonStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(takeInPlayerScreen.this, RoleSelectionActivity.class);
                intent.putExtra("playerNames", playerNames);
                setResult(RESULT_OK, intent);
                startActivity(intent);
            }
        });
    }
}
