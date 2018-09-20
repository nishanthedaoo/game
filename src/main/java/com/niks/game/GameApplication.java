package com.niks.game;

import com.niks.game.model.Player;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class GameApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameApplication.class, args);

		//Player p1=new Player(Arrays.asList(new int[]{0, 6, 6, 6, 6, 6}));


	}
}
