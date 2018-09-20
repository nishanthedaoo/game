package com.niks.game;

import com.niks.game.exception.InvalidGameActionException;
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
    public Board start(@RequestParam String player1,@RequestParam String player2){
      return  gameService.startGame(player1,player2);

    }

    @RequestMapping("/play")
    @ResponseBody
        public Board play(String boardID,String currentPlayerID,int selectedPit) throws InvalidGameActionException {

         return  gameService.play(boardID,currentPlayerID,selectedPit);

    }
}
