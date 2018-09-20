package com.niks.game;

import com.niks.game.exception.InvalidGameActionException;
import com.niks.game.model.Player;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class BoardTest {


    @Test
    public void testPlayGameActivePlayerChanges() throws Exception {
        Board board= createBoard("Nishant", "Karishma");
        Assert.assertThat(board.getCurrentPlayer().getPlayerId(), Matchers.equalTo("Nishant"));
        board.play(board.getPlayerById("Nishant"), 1);
        Assert.assertThat(board.getCurrentPlayer().getPlayerId(), Matchers.equalTo("Karishma"));
        Player karishma = board.getPlayerById("Karishma");
        Player nishant = board.getPlayerById("Nishant");

        int pitSize = board.getPitSize();
        //1st pit is as it is
        Assert.assertThat(nishant.getPits()[0], Matchers.equalTo(6));
        // selected pit is emptied
        Assert.assertThat(nishant.getPits()[1], Matchers.equalTo(0));
        //all other pits are increased with 1
        for (int i = 2; i < pitSize - 1; i++) {
            Assert.assertThat(nishant.getPits()[i], Matchers.equalTo(7));
        }
        // 1 stone in the big pit of nishant
        Assert.assertThat(nishant.getPits()[pitSize - 1], Matchers.equalTo(1));

        // check karishma 1st pits is cremented by 1
        Assert.assertThat(karishma.getPits()[0], Matchers.equalTo(7));
        //other pits are increased by 1
        for (int i = 1; i < board.getPitSize() - 1; i++) {
            Assert.assertThat(karishma.getPits()[i], Matchers.equalTo(6));
        }
        // big pit is empty
        Assert.assertThat(karishma.getPits()[pitSize - 1], Matchers.equalTo(0));
    }
    @Test
    public void testPlayGameActivePlayerNotChanges() throws Exception {
        Board board = createBoard("Nishant", "Karishma");
        Assert.assertThat(board.getCurrentPlayer().getPlayerId(), Matchers.equalTo("Nishant"));

        board.play(board.getPlayerById("Nishant"), 0);

        int pitSize = board.getPitSize();

        Assert.assertThat(board.getCurrentPlayer().getPlayerId(), Matchers.equalTo("Nishant"));
        //1st pit is empty
        Assert.assertThat(board.getCurrentPlayer().getPits()[0], Matchers.equalTo(0));
        //other pits incresed by 1
        for (int i = 1; i < pitSize - 1; i++) {
            Assert.assertThat(board.getCurrentPlayer().getPits()[i], Matchers.equalTo(7));
        }
        // big pit is 1
        Assert.assertThat(board.getCurrentPlayer().getPits()[pitSize - 1], Matchers.equalTo(1));
        // opp all pits are unchanged
        for (int i = 0; i < pitSize - 1; i++) {
            Assert.assertThat(board.getOpponentPlayer().getPits()[i], Matchers.equalTo(6));
        }
        //opp big pit is 0
        Assert.assertThat(board.getOpponentPlayer().getPits()[pitSize - 1], Matchers.equalTo(0));
    }

    @Test
    public void testLastStoneLandsInEmptyOwnPit() throws Exception {
        Board board= createBoard("Nishant", "Karishma");
        Player nishant = board.getPlayerById("Nishant");
        nishant.setPits(new int[] {1, 0, 6, 6, 6, 6, 7});

        board.play(nishant, 0);

        // 1 + 6 (opponent's stones) + 7 = 14
        Assert.assertThat(nishant.getPits()[1], Matchers.equalTo(7));
        Assert.assertThat(board.getPlayerById("Karishma").getPits()[4], Matchers.equalTo(0));
    }
    @Test(expected = InvalidGameActionException.class)
    public void testNotPlayerTurn() throws Exception {
        Board  board= createBoard("Nishant", "Karishma");
        board.play(board.getPlayerById("Karishma"), 2);
    }
    private Board createBoard(String userId1, String userId2) {
        Player player = new Player(userId1);
        Player opponentPlayer = new Player(userId2);
        return new Board(player,opponentPlayer);
    }
}
