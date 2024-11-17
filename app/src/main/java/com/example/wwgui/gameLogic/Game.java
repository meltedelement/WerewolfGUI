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


    public void getCurrentKillWolf(ArrayList<Player> players){
        Roles[] wolfKillPriority = {Roles.WEREWOLF, Roles.MISTWOLF, Roles.CUBWOLF, Roles.SORCERER};
        boolean wolfSelected = false;
        for (Roles x : wolfKillPriority){
            for (Player y : players){
                if (y.getRole() == x && ! wolfSelected){
                    y.setKillWolf();
                }

            }
        }
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

    public ArrayList<Player> processDeaths(ArrayList<Player> players){
        recentlyDead.clear();
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
        Roles[] nightOrder = {Roles.SEER, Roles.AURASEER, Roles.WEREWOLF, Roles.SORCERER, Roles.BODYGUARD, Roles.ARSONIST};
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