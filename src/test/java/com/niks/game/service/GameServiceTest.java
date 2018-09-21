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
    public void test() throws Exception {
        gameService= new GameService();
        Board board= new Board(new Player("nishant"),new Player("karishma"),7);
        BoardManager boardManager  =  new BoardManager();
        boardManager.boards.put(board.getBoardId(),board);
        gameService.boardManager=boardManager;
        Board board1=gameService.play(board.getBoardId(),"nishant",0);
        System.out.println(board1);
        Board board2=gameService.play(board1.getBoardId(),"nishant",1);
        System.out.println(board2);
        Board board3=gameService.play(board1.getBoardId(),"karishma",2);
        System.out.println(board3);
        Board  board4=gameService.play(board1.getBoardId(),"nishant",2);
        System.out.println(board4);
        Board  board5=gameService.play(board1.getBoardId(),"karishma",2);
        System.out.println(board5);
        Board  board6=gameService.play(board1.getBoardId(),"nishant",1);
        System.out.println(board6);
        //Board board3=gameService.play(board2.getBoardId(),"nishant", )

        System.out.println("fssdf new");




    }

}
