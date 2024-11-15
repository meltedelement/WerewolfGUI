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
import com.example.wwgui.gameLogic.Roles;
import com.example.wwgui.gameLogic.Launcher;

public class RoleSelectionActivity extends AppCompatActivity {

    private TextView textViewPlayerName;
    private LinearLayout linearLayoutRoleButtons;
    private int currentPlayerIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_selection);

        textViewPlayerName = findViewById(R.id.textViewPlayerName);
        linearLayoutRoleButtons = findViewById(R.id.linearLayoutRoleButtons);

        // Retrieve the ArrayList<Player> from the intent
        ArrayList<String> temp = (ArrayList<String>) getIntent().getSerializableExtra("playerNames");
        ArrayList<Player> players = Launcher.playersCreate(temp);
        // List of available roles
        Roles[] roles = Roles.values();
        // Display the first player and set up role buttons
        displayPlayerAndRoles(roles, players);
    }

    private void displayPlayerAndRoles(Roles[] roles, ArrayList<Player> playy) {
        // Check if there are remaining players to assign roles
        if (currentPlayerIndex < playy.size()) {
            // Display the current player's name
            Player currentPlayer = playy.get(currentPlayerIndex);
            textViewPlayerName.setText("Assign Role to: " + currentPlayer.getName());

            // Clear any previous role buttons
            linearLayoutRoleButtons.removeAllViews();

            // Dynamically create a button for each role
            for (Roles role : roles) {
                Button roleButton = new Button(this);
                roleButton.setText(role.toString());

                // Set a click listener to assign the role to the current player
                roleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Assign the selected role to the current player
                        currentPlayer.setRole(role);

                        // Move to the next player
                        currentPlayerIndex++;
                        displayPlayerAndRoles(roles, playy); // Recursive call to show the next player
                    }
                });

                // Add the button to the layout
                linearLayoutRoleButtons.addView(roleButton);
            }
        } else {
            // All players have roles, move to PlayerActionActivity
            Intent intent = new Intent(RoleSelectionActivity.this, PlayerActionActivity.class);
            intent.putExtra("players", playy); // Pass the updated players list
            startActivity(intent);
        }
    }
}
