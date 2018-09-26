package com.niks.game;

import com.niks.game.model.BoardView;
import com.niks.game.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class GameController {

    @Autowired
    GameService gameService;


    @RequestMapping("/hello")
    public void hello(){
        System.out.println("hello");
    }

    @RequestMapping("/startGame")
    @ResponseBody
    public BoardView start(@RequestParam String player1, @RequestParam String player2){
      return  new BoardView().getPlayerView(gameService.startGame(player1,player2));

    }

    @RequestMapping("/play")
    @ResponseBody
        public BoardView play(String boardID, String currentPlayerID, int selectedPit) throws Exception {
        Board board=gameService.play(boardID,currentPlayerID,selectedPit);

         return new BoardView().getPlayerView(board);

    }
}
