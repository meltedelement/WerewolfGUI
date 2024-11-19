package com.example.wwgui.gameLogic;

import java.util.ArrayList;
import java.util.Arrays;


public class Game{

    public static boolean arsonistIgnite = false;
    ArrayList<Player> recentlyDead = new ArrayList<>();


    public enum GameStates{
    DAY,
    NIGHT,
    STARTING,
    FINISHED
    }

    public static void main(){

    }

    //Returns a list of all living players
    public ArrayList<Player> isAliveTest(ArrayList<Player> players){
        ArrayList<Player> internalList = new ArrayList<>();
        for(Player x : players){
            if (x.getAlive()){
                internalList.add(x);
            }
        }
        return internalList;
    }


    //Returns a list of all players matching the team of the first input player
    public ArrayList<Player> matchTeamTest(Player player1, ArrayList<Player> players) {
        ArrayList<Player> internalList = new ArrayList<>();
        for (Roles[] team : Player.teamsList) {
            for (Player player2 : players) {
                if (Arrays.asList(team).contains(player1.getRole()) && Arrays.asList(team).contains(player2.getRole())) {
                    internalList.add(player2);
                }
            }
        }
        return(internalList);
    }

    //returns a list of all players NOT on the team of the first player
    public ArrayList<Player> oppTeamTest(Player player1, ArrayList<Player> players) {
        ArrayList<Player> internalList = new ArrayList<>();
        for (Roles[] team : Player.teamsList) {
            for (Player player2 : players) {
                if (Arrays.asList(team).contains(player1.getRole()) && ! Arrays.asList(team).contains(player2.getRole())) {
                    internalList.add(player2);
                }
            }
        }
        return(internalList);
    }

    public ArrayList<Player> notMeTest(Player player, ArrayList<Player> players){
        ArrayList<Player> internalList = new ArrayList<>();
        for (Player x : players){
            if (player != x){
                internalList.add(x);
            }
        }
        return internalList;
    }

    // public Player playerPick(){  // A placeholder function to allow a player to be selected in command line until i have a frontend
    //     Scanner scan = new Scanner(System.in);
    //     for(Player x : launchy.players){
    //         if (x.getAlive()){
    //             System.out.println(launchy.players.indexOf(x));
    //             System.out.println(x.getName());
    //         }
    //     }
    //     System.out.println("Enter the number of the selected player");
    //     return(launchy.players.get(Integer.valueOf(scan.nextLine())));   //Get the player object indicated by the index input by the user
    // }
    private boolean finished;
    public ArrayList<Player> processDeaths(ArrayList<Player> players){
        recentlyDead.clear();

        finished = false;
        for (Player x : players){
            for (Roles y : Player.werewolfKillPriority){
                if (x.getRole() == y && x.getAlive()){
                    x.setKillwolf(true);
                }
            }
        }
        for(Player x : players){
            if (x.getAttacked() && ! x.getDefended() || arsonistIgnite && x.getDoused()){
                x.kill();
                x.unAttack();
                recentlyDead.add(x);
                if (x.getRole() == Roles.HUNTER){
                    x.setHunterShootReady(true);
                }
            }
            if (!Arrays.asList(Player.basicDefenseRoles).contains(x.getRole())){
                x.unDefend();

            }
        }
        arsonistIgnite = false;
        return recentlyDead;
    }

    public ArrayList<Player> nightActions(ArrayList<Player> players){
        ArrayList<Player> playerNightOrder = new ArrayList<>();
        Roles[] nightOrder = {Roles.WEREWOLF, Roles.HEXWOLF, Roles.SEER, Roles.AURASEER, Roles.SORCERER, Roles.BODYGUARD, Roles.ARSONIST};
        for (Roles role : nightOrder){
            for(Player player : players){
                if (! player.skipVisit && player.getAlive() && player.getRole() == role){
                    playerNightOrder.add(player);
                }
            }
        }
    return(playerNightOrder);
    }

    public void dayActions(){
        
    }


//    public void gameStart(ArrayList<Player> players){
//        GameStates gameState = GameStates.NIGHT;
//        while (gameState != GameStates.FINISHED){
//            switch (gameState) {
//                case NIGHT:
//                nightActions(players);
//                nightNum += 1;
//                processDeaths(players);
//                gameState = GameStates.DAY;
//                break;
//
//                case DAY:
//                    dayActions();
//                    gameState = GameStates.NIGHT;
//                    break;
//
//
//                default:
//                    break;
//            }
//        }
//    }





}