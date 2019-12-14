package com.itis.group11801;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class GameTest {

    private static GameLogic gameLogic;

    @BeforeClass
    public static void init() {
        gameLogic = new GameLogic();
    }

    @Test
    public void gameLogicTest() {
        Assert.assertNotNull(gameLogic.keyWord);
    }

    @Test
    public void loaderSingletonTest() {
        Loader loader1 = Loader.getInstance();
        Loader loader2 = Loader.getInstance();
        Assert.assertEquals(loader1, loader2);
    }

    @Test
    public void changeGuessedWordTest() {
        gameLogic.keyWord = "любовь";
        gameLogic.guessedWord = "------";
        gameLogic.changeGuessedWord("л");
        Assert.assertEquals(gameLogic.guessedWord, "л-----");
    }

    @Test
    public void nextStepTest() {
        gameLogic.keyWord = "итис";
        gameLogic.guessedWord = "-т-с";
        Assert.assertTrue(gameLogic.nextStep("и"));
    }

    @Test
    public void nextStepMissTest() {
        gameLogic.missCount = 0;
        gameLogic.keyWord = "счастье";
        gameLogic.guessedWord = "-част--";
        gameLogic.nextStep("я");
        Assert.assertEquals(gameLogic.missCount, 1);
    }

    @Test
    public void nextStepMissIsFalseTest() {
        gameLogic.keyWord = "друзья";
        gameLogic.guessedWord = "д----я";
        Assert.assertFalse(gameLogic.nextStep("м"));
    }

}
