package com.example.wwgui.gameLogic;

import java.io.Serializable;
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
    private boolean killWolf = false;
    public boolean skipVisit = false;

    public static final Roles[] townRoles = {Roles.BODYGUARD, Roles.SEER, Roles.VETERAN, Roles.VILLAGER};
    public static final Roles[] neutraRoles = {Roles.ARSONIST, Roles.DOPPLEGANGER};
    public static final Roles[] werewolfRoles = {Roles.CUBWOLF, Roles.MISTWOLF, Roles.WEREWOLF, Roles.LYCAN};

    public static final Roles[] seerVisibleRoles = {Roles.WEREWOLF, Roles.CUBWOLF, Roles.SORCERER, Roles.MISTWOLF, Roles.ARSONIST, Roles.LYCAN};
    public static final Roles[] basicDefenseRoles = {Roles.ARSONIST};
    public static final Roles[] noVisitRoles = {Roles.VILLAGER, Roles.VETERAN, Roles.LYCAN, Roles.MISTWOLF};

    private transient Game gameObj = new Game(); // Mark as transient since Game may not be serializable

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

    public void setKillWolf() {
        this.killWolf = true;
    }

    public String getName() {
        return this.name;
    }

    public Roles getRole() {
        return this.role;
    }

    public boolean getAttacked() {
        return attacked;
    }

    public boolean getDefended() {
        return defended;
    }

    public boolean getAlive() {
        return alive;
    }

    public void attack() {
        this.attacked = true;
    }

    public void defend() {
        this.defended = true;
    }

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
        if (selectedPlayer.getRole() == Roles.ARSONIST) {
            this.douse();
        } else if (selectedPlayer.getRole() == Roles.VETERAN) {
            this.kill();
        }

        switch (this.role) {
            case WEREWOLF:
                nightActionWerewolf(selectedPlayer);
                break;

            case BODYGUARD:
                nightActionBodyguard(selectedPlayer);
                break;

            case VILLAGER:
                break;

            default:
                break;
        }
    }

    private boolean nightActionSeer(Player selectedPlayer) {
        return Arrays.asList(seerVisibleRoles).contains(selectedPlayer.getRole());
    }

    private void nightActionKillwolf(Player selectedPlayer) {
        selectedPlayer.attack();
    }

    private void nightActionWerewolf(Player selectedPlayer) {
        selectedPlayer.attack();
    }

    private void nightActionArsonist(Player selectedPlayer) {
        selectedPlayer.douse();
    }

    private void nightActionBodyguard(Player selectedPlayer) {
        selectedPlayer.defend();
    }

    private void nightActionVillager() {
    }

    private void nightActionSorcerer(Player selectedPlayer) {
        selectedPlayer.silence();
    }
}
