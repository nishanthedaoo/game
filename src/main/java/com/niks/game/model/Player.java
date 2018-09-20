package com.niks.game.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player {

    private String playerId;
    private int[] pits;

    public Player(){}

    @Override
    public String toString() {
        return "Player{" +
                "playerId='" + playerId + '\'' +
                ", pits=" + Arrays.toString(pits) +
                '}';
    }

    public Player(String playerId) {
        this.playerId = playerId;
        pits = new int[]{6, 6, 6, 6, 6, 6, 0};
    }
    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public int[] getPits() {
        return pits;
    }

    public void setPits(int[] pits) {
        this.pits = pits;
    }



}
