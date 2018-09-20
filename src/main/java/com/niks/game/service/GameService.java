package com.niks.game.service;

import com.niks.game.Board;
import com.niks.game.exception.InvalidGameActionException;
import com.niks.game.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameService {
    @Autowired
    BoardManager boardManager;

    public Board startGame(String player1, String player2){

            Player player = new Player(player1);
            Player opponentPlayer = new Player(player2);
        Board board=new Board(player,opponentPlayer);
        boardManager.boards.put(board.getBoardId(),board);
        return board;


    }

    public  Board play(String boardID,String currentPlayer,int selectedPit) throws InvalidGameActionException {
        System.out.println(boardID);

        Board board=boardManager.boards.get(boardID);
        board=board.play(board.getPlayerById(currentPlayer), selectedPit);
        return board;
    }

}
