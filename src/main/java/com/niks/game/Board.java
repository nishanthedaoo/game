package com.niks.game;

import com.niks.game.exception.InvalidGameActionException;
import com.niks.game.model.Player;
import com.niks.game.model.Status;

public class Board {

    private Player player;
    private Player opponentPlayer;
    private int pitSize;
    private Status gameStatus;
    private String boardId;
    private Player currentPlayer;

    public Board(Player player, Player opponentPlayer) {

        this.player = player;
        this.opponentPlayer = opponentPlayer;
        this.pitSize = 7;
        this.boardId = player.getPlayerId() + "-" + opponentPlayer.getPlayerId();
        currentPlayer = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getOpponentPlayer() {
        return opponentPlayer;
    }

    public void setOpponentPlayer(Player opponentPlayer) {
        this.opponentPlayer = opponentPlayer;
    }

    public Status getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(Status gameStatus) {
        this.gameStatus = gameStatus;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }



    public int getPitSize() {
        return pitSize;
    }

    public void setPitSize(int pitSize) {
        this.pitSize = pitSize;
    }



    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }



    public Board play(Player player, int seletedPit) throws InvalidGameActionException {

        if (currentPlayer != player) {
            throw new InvalidGameActionException("not your turn");
        }
        // selected pit should not include the player's big pit
        if (seletedPit >= 0 && seletedPit <= pitSize - 2) {
            int stones = player.getPits()[seletedPit];
            int lastSow = 0;
            if (stones > 0) {

                // empty the stones in the selected pit
                player.getPits()[seletedPit] = 0;

                // sow the stones by putting them in other pits
                int startIndex = seletedPit + 1;
                int lastIndex;
                Player sowStonePlayer = player;
                do {
                    lastIndex = sowStones(stones, startIndex, sowStonePlayer);
                    stones = stones - (lastIndex - startIndex + 1);
                    // if there are stones remained, switch player for sowing stones
                    if (stones > 0) {
                        sowStonePlayer = opponentPlayer;
                    }
                    startIndex = 0;
                } while (stones > 0);

                // if last stone lands in an empty pit of the active player, the one stone and all stones from the opposite pit are added to the players own pit
                if (currentPlayer.equals(sowStonePlayer) && currentPlayer.getPits()[lastIndex] == 1) {
                    int oppositePit = pitSize - 2 - lastIndex;
                    if (oppositePit >= 0) {
                        currentPlayer.getPits()[lastIndex] = opponentPlayer.getPits()[oppositePit] + 1;
                        opponentPlayer.getPits()[oppositePit] = 0;
                    }
                }
                setNextPlayer(player, lastIndex);
                updateGameStatus();
            }


        }
        return this;

    }

    private void updateGameStatus() {
        if (arePitsEmpty(currentPlayer) || arePitsEmpty(opponentPlayer)) {
            gameStatus = Status.FINISHED;
            moveAllPitsToLargePit(currentPlayer);
            moveAllPitsToLargePit(opponentPlayer);
        }
    }

    private boolean arePitsEmpty(Player player) {
        int[] pits = player.getPits();
        for (int i = 0; i < pitSize - 1; i++) {
            if (pits[i] > 0) {
                return false;
            }
        }
        return true;
    }

    private void moveAllPitsToLargePit(Player player) {
        for (int i = 0; i < pitSize - 1; i++) {
            if (player.getPits()[i] > 0) {
                player.getPits()[pitSize - 1] += player.getPits()[i];
                player.getPits()[i] = 0;
            }
        }
    }


    private void setNextPlayer(Player player, int lastSow) {
        if (currentPlayer.equals(player)) {
            if (lastSow == pitSize - 1) {
                return;
            }
        }
        currentPlayer = opponentPlayer;
    }

    public int sowStones(int stones, int start, Player player) {
        int endIndex;
        // if the player is the active player, stone can be put in the big pit too
        if (currentPlayer.equals(player)) {
            endIndex = pitSize - 1;
        } else {
            endIndex = pitSize - 2;
        }

        int index = start;
        while (stones > 0 && index <= endIndex) {
            player.getPits()[index]++;
            stones--;
            index++;
        }

        // return the index of the last pit that a stone was located in
        return index - 1;
    }

    public Player getPlayerById(String playerId) {
        if (player.getPlayerId().equals(playerId)) {
            return player;
        }
        if (opponentPlayer.getPlayerId().equals(playerId)) {
            return opponentPlayer;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Board{" +
                "player=" + player +
                ", opponentPlayer=" + opponentPlayer +
                ", pitSize=" + pitSize +
                ", gameStatus=" + gameStatus +
                ", boardId='" + boardId + '\'' +
                ", currentPlayer=" + currentPlayer +
                '}';
    }
}
