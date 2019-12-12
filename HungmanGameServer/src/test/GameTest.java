package test;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import server.GameLogic;

public class GameTest {

    private static GameLogic gameLogic;

    @BeforeClass
    public static void init() {
        gameLogic = new GameLogic();
    }

    @Test
    public void gameLogicTest() {
        Assert.assertTrue(gameLogic.keyWord.length() > 0);
    }

}
