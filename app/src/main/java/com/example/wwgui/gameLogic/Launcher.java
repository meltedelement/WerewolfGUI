package com.example.wwgui.gameLogic;

import java.util.ArrayList;
import java.util.Scanner;

public class Launcher {
    // Step 2: Create a static instance of the class
    private static Launcher instance;
    ArrayList<Player> playerList;

    // Step 1: Make the constructor private to prevent instantiation
    private Launcher() {
    }


    public static ArrayList<Player> playersCreate(ArrayList<String> arrayIn){    //Turn an array of players into player objects using their names
        ArrayList<Player> players = new ArrayList<Player>();
        for (String x : arrayIn){
            players.add(new Player(x));
        }
        return(players);
    }

    public static ArrayList<Player> assignRolesToPlayers(ArrayList<Player> playersIn) {
        Scanner scanner = new Scanner(System.in);
        for (Player x : playersIn){
            System.out.println(x);
            int count = 0;
            for (Roles y : Roles.values()){
                System.out.println(count);
                System.out.println(y);
                count++;
            }
            int userIntIn = Integer.parseInt(scanner.nextLine());
            x.setRole(Roles.values()[userIntIn]);


        }
        scanner.close();
        return(playersIn);
    }




    // Step 3: Provide a public static method to get the instance
    public static Launcher getInstance() {
        if (instance == null) {
            instance = new Launcher();
        }
        return instance;
    }
}