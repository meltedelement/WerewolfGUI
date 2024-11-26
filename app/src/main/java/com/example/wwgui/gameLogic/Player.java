package com.example.wwgui.gameLogic;

import com.example.wwgui.PlayerActionActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Player implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private Roles role;
    private boolean alive = true;
    private boolean attacked = false;
    private boolean defended = false;
    private boolean silenced = false;
    private boolean doused = false;
    public boolean skipVisit = false;
    private  boolean hexed = false;
    private boolean alert = false;
    private Player doppledTarget;
    private boolean hunterShootReady = false;
    private boolean hexNight = true;
    private boolean killWolf = false;

    public static final Roles[] townRoles = {Roles.BODYGUARD, Roles.SEER, Roles.VETERAN, Roles.VILLAGER};
    public static final Roles[] neutraRoles = {Roles.ARSONIST, Roles.DOPPLEGANGER};
    public static final Roles[] werewolfRoles = {Roles.CUBWOLF, Roles.MISTWOLF, Roles.WEREWOLF, Roles.LYCAN};
    public static final Roles[][] teamsList = {townRoles, neutraRoles, werewolfRoles};

    public static final Roles[] seerVisibleRoles = {Roles.WEREWOLF, Roles.CUBWOLF, Roles.SORCERER, Roles.MISTWOLF, Roles.ARSONIST, Roles.LYCAN, Roles.HEXWOLF};
    public static final Roles[] basicDefenseRoles = {Roles.ARSONIST};
    public static final Roles[] noVisitRoles = {Roles.VILLAGER, Roles.VETERAN, Roles.LYCAN, Roles.MISTWOLF};
    public static final Roles[] werewolfKillPriority = {Roles.CUBWOLF, Roles.WEREWOLF, Roles.MISTWOLF, Roles.HEXWOLF, Roles.SORCERER};

    public Player(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public void setRole(Roles roleIn) {
        this.role = roleIn;
        if (Arrays.asList(Player.noVisitRoles).contains(this.getRole())) {
            this.skipVisit = true;
        }
        if (Arrays.asList(Player.basicDefenseRoles).contains(this.getRole())) {
            this.defended = true;
        }
    }

    public String getName() {
        return this.name;
    }

    public boolean getDoused() {
        return this.doused;
    }

    public int getRoleVisitCount() {
        switch (this.role) {
            case WEREWOLF:
                return 1;

            case BODYGUARD:
                return 1;

            case ARSONIST:
                return 1;

            case SEER:
                return 1;

            case DOPPLEGANGER:
                return 1;

            case HEXWOLF:
                return 1;

            case VILLAGER:
                return 0;

            case AURASEER:
                return 2;

            default:
                return 0;
        }
    }
    public boolean getSilenced(){return this.silenced; }

    public boolean getHexed(){return this.hexed; }

    public void setHexed(boolean input){ this.hexed = input; }

    public boolean getHunterShootReady(){return this.hunterShootReady;}

    public void setHunterShootReady(boolean input){this.hunterShootReady = input;}

    public Roles getRole() {
        return this.role;
    }

    public boolean getKillwolf(){return this.killWolf;}

    public void setKillwolf(boolean input){this.killWolf = input;}

    public boolean getAttacked() {
        return this.attacked;
    }

    public boolean getDefended() {
        return this.defended;
    }

    public boolean getAlive() {
        return this.alive;
    }

    public boolean getOnAlert(){return this.alert;}

    public Player getDoppledTarget(){return this.doppledTarget; }

    public void attack() {
        this.attacked = true;
    }

    public void defend() {
        this.defended = true;
    }

    public void unAttack(){ this.attacked = false;}

    public void unDefend() {
        this.defended = false;
    }

    public void kill() {
        this.alive = false;
    }

    public void silence() {
        this.silenced = true;
    }

    public void douse() {
        this.doused = true;
    }




    public void performNightAction(Player selectedPlayer) {

        if (selectedPlayer.getRole() == Roles.ARSONIST && selectedPlayer != this) {
            this.douse();
        }
        else if (selectedPlayer.getOnAlert()) {
            this.kill();
        }

        switch (this.role) {
            case WEREWOLF:
                nightActionWerewolf(selectedPlayer);
                break;

            case BODYGUARD:
                nightActionBodyguard(selectedPlayer);
                break;

            case ARSONIST:
                nightActionArsonist(selectedPlayer);
                break;

            case SEER:
                nightActionSeer(selectedPlayer);
                break;

            case DOPPLEGANGER:
                if (this.getDoppledTarget() == null){
                    nightActionDoppleganger(selectedPlayer);
                }
                break;

            case HEXWOLF:
                nightActionHexwolf(selectedPlayer);
                break;


            case VILLAGER:
                break;

            case AURASEER:
                nightActionAuraSeer(selectedPlayer);
                break;
            default:
                break;
        }
    }

    private boolean nightActionSeer(Player selectedPlayer) {
        return Arrays.asList(seerVisibleRoles).contains(selectedPlayer.getRole());
    }

    private boolean nightActionAuraSeer(Player selectedPlayer){

        return(true);
    }

    public void nightActionWerewolf(Player selectedPlayer) {
        selectedPlayer.attack();
    }

    private void nightActionArsonist(Player selectedPlayer) {
        if (selectedPlayer == this) {
            Game.arsonistIgnite = true;
        }
        else{
            selectedPlayer.douse();
        }
    }

    private void nightActionHexwolf(Player selectedPlayer){
        if (hexNight){
            selectedPlayer.setHexed(true);
            hexNight = false;
        }
        else{
            hexNight = true;
        }

    }

    private void nightActionBodyguard(Player selectedPlayer) {
        selectedPlayer.defend();
    }

    private void nightActionDoppleganger(Player selectedPlayer) {
        this.doppledTarget = selectedPlayer;
    }

    private void nightActionVillager() {
    }

    private void nightActionSorcerer(Player selectedPlayer) {
        selectedPlayer.silence();
    }
}
