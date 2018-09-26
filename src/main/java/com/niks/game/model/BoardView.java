package com.niks.game.model;

import com.niks.game.Board;

public class BoardView {

    private Player player1;
    private Player player2;
    private int pitSize;
    private  String winner;

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
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

    public int getPitSize() {
        return pitSize;
    }

    public void setPitSize(int pitSize) {
        this.pitSize = pitSize;
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

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    private Status gameStatus;
    private String boardId;
    private Player currentPlayer;

    public BoardView getPlayerView(Board board){
        this.boardId=board.getBoardId();
        this.currentPlayer=board.getCurrentPlayer();
        this.gameStatus=board.getGameStatus();
        this.pitSize=board.getPitSize();
        this.player1=new Player();
        this.player2=board.getPlayer2();
        player1.setPlayerId(board.getPlayer1().getPlayerId());
        int[] pits_reversed = new int[board.getPitSize()];
        for (int i = 0; i < board.getPitSize(); i++)
            pits_reversed[i] = board.getPlayer1().getPits()[board.getPitSize() - 1 - i];
        this.getPlayer1().setPits(pits_reversed);

        if(Status.FINISHED.equals(board.getGameStatus())){
            if(player1.getPits()[0] > player2.getPits()[6]){
                this.winner=player1.getPlayerId();
            }else if(player1.getPits()[0] < player2.getPits()[6])
            {
                this.winner=player2.getPlayerId();
            }else {
                this.winner="TIE";
            }

        }
        return  this;
    }
}
