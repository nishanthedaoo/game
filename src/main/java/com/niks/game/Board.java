package com.niks.game;

import com.niks.game.exception.InvalidGameActionException;
import com.niks.game.model.Player;
import com.niks.game.model.Status;

public class Board {

    private Player player1;
    private Player player2;
    private int pitSize;
    private Status gameStatus;
    private String boardId;
    private Player currentPlayer;

    public Board(Player player1, Player player2,int pitSize) {
        this.gameStatus=Status.RUNNING;
        this.player1 = player1;
        this.player2 = player2;
        this.pitSize = pitSize;
        this.boardId = player1.getPlayerId() + "-" + player2.getPlayerId();
        currentPlayer = player1;
    }

    public Board play(Player selectedPlayer, int seletedPit) throws Exception {

        if (currentPlayer != selectedPlayer) {
            throw new InvalidGameActionException("not your turn");
        }
        // selected pit should not include the player1's big pit
        if (seletedPit >= 0 && seletedPit <= pitSize - 2) {
            int stones = selectedPlayer.getPits()[seletedPit];
            //TODO handle empty pit
            int lastSow = 0;
            if (stones > 0) {

                // empty the stones in the selected pit
                selectedPlayer.getPits()[seletedPit] = 0;

                // sow the stones by putting them in other pits
                int startIndex = seletedPit + 1;
                int lastIndex;
                Player sowStonePlayer = selectedPlayer;
                do {
                    lastIndex = sowStones(stones, startIndex, sowStonePlayer);
                    stones = stones - (lastIndex - startIndex + 1);
                    // if there are stones remained, switch player1 for sowing stones
                    if (stones > 0) {
                        if(selectedPlayer==player1){
                            sowStonePlayer=player2;
                        }else{
                            sowStonePlayer = player1;
                        }

                    }
                    startIndex = 0;
                } while (stones > 0);

                // if last stone lands in an empty pit of the active player1, the one stone and all stones from the opposite pit are added to the players own pit
                if (currentPlayer.equals(sowStonePlayer) && currentPlayer.getPits()[lastIndex] == 1) {
                    int oppositePit = pitSize - 2 - lastIndex;
                    if (oppositePit >= 0) {
                        currentPlayer.getPits()[lastIndex] = player2.getPits()[oppositePit] + 1;
                        player2.getPits()[oppositePit] = 0;
                    }
                }
                setNextPlayer(selectedPlayer, lastIndex);
                updateGameStatus();
            }
            else{
                throw new Exception("sleted pit is zero");
            }


        }
        return this;

    }

    private void updateGameStatus() {
        if (arePitsEmpty(player1) || arePitsEmpty(player2)) {
            gameStatus = Status.FINISHED;
            moveAllPitsToLargePit(player1);
            moveAllPitsToLargePit(player2);
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
        if(currentPlayer==player1){
            currentPlayer=player2;
        }else{
            currentPlayer=player1;
        }

    }

    public int sowStones(int stones, int start, Player player) {
        int endIndex;
        // if the player1 is the active player1, stone can be put in the big pit too
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
        if (player1.getPlayerId().equals(playerId)) {
            return player1;
        }
        if (player2.getPlayerId().equals(playerId)) {
            return player2;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Board{" +
                "player1=" + player1 +
                ", player2=" + player2 +
                ", pitSize=" + pitSize +
                ", gameStatus=" + gameStatus +
                ", boardId='" + boardId + '\'' +
                ", currentPlayer=" + currentPlayer +
                '}';
    }
    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
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
}
