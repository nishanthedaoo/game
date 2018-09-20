package com.niks.game.service;

import com.niks.game.Board;
import com.niks.game.exception.InvalidGameActionException;
import com.niks.game.model.Player;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class GameServiceTest {

    @Autowired
    GameService gameService;

    @Test
    public void test() throws InvalidGameActionException {
        gameService= new GameService();
        Board board= new Board(new Player("nishant"),new Player("karishma"));
        BoardManager boardManager  =  new BoardManager();
        boardManager.boards.put(board.getBoardId(),board);
        gameService.boardManager=boardManager;
        Board board1=gameService.play(board.getBoardId(),"nishant",1);
        System.out.println(board1);
        Board board2=gameService.play(board1.getBoardId(),"karishma",1);
        System.out.println(board2);

        System.out.println("fsdf");



    }

}
