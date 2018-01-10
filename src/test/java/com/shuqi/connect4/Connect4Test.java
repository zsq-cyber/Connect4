package com.shuqi.connect4;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.testng.Assert.*;

/**
 * @author zsq
 * @since 1/10/2018
 */
public class Connect4Test {
    private Connect4 newGame;

    @BeforeMethod
    public void setUp() throws Exception
    {
        newGame = new Connect4(6, 7);
    }

    @Test
    public void testCheckHorizontal() throws Exception
    {
        char[][] dataMatrix = new char[6][7];
        char targetToken = 'R';

        // test false
        dataMatrix[0][0] = 'G';
        dataMatrix[0][1] = 'R';
        dataMatrix[0][2] = 'R';
        dataMatrix[0][3] = 'R';
        dataMatrix[0][4] = 'G';
        dataMatrix[0][5] = 'R';
        dataMatrix[0][6] = 'R';
        assertFalse(newGame.checkHorizontal(dataMatrix, targetToken));

        // test true
        dataMatrix[0][4] = 'R';
        assertTrue(newGame.checkHorizontal(dataMatrix, targetToken));
    }

    @Test
    public void testCheckVertical() throws Exception
    {
        char[][] dataMatrix = new char[6][7];
        char targetToken = 'R';

        // test false
        dataMatrix[0][1] = 'G';
        dataMatrix[1][1] = 'R';
        dataMatrix[2][1] = 'G';
        dataMatrix[3][1] = 'R';
        dataMatrix[4][1] = 'R';
        dataMatrix[5][1] = 'R';
        assertFalse(newGame.checkVertical(dataMatrix, targetToken));

        // test true
        dataMatrix[1][1] = 'G';
        dataMatrix[2][1] = 'R';
        assertTrue(newGame.checkVertical(dataMatrix, targetToken));
    }

    @Test
    public void testCheckForwardDiagonal() throws Exception
    {
        char[][] dataMatrix = new char[6][7];
        char targetToken = 'R';

        // test false
        dataMatrix[1][0] = 'G';
        dataMatrix[2][1] = 'R';
        dataMatrix[3][2] = 'G';
        dataMatrix[4][3] = 'R';
        dataMatrix[5][4] = 'R';
        assertFalse(newGame.checkForwardDiagonal(dataMatrix, targetToken));

        // test true
        dataMatrix[3][2] = 'R';
        assertTrue(newGame.checkForwardDiagonal(dataMatrix, targetToken));
    }

    @Test
    public void testCheckBackwardDiagonal() throws Exception
    {
        char[][] dataMatrix = new char[6][7];
        char targetToken = 'R';

        // test false
        dataMatrix[5][1] = 'G';
        dataMatrix[4][2] = 'R';
        dataMatrix[3][3] = 'G';
        dataMatrix[2][4] = 'R';
        dataMatrix[1][5] = 'R';
        dataMatrix[0][6] = 'R';
        assertFalse(newGame.checkBackwardDiagonal(dataMatrix, targetToken));

        // test true
        dataMatrix[3][3] = 'R';
        assertTrue(newGame.checkBackwardDiagonal(dataMatrix, targetToken));
    }

    @Test
    public void testToggleUserKey() throws Exception
    {
        newGame.init();

        Method toggleUserKey = newGame.getClass().getDeclaredMethod("toggleUserKey");
        toggleUserKey.setAccessible(true);

        Field currentUserKey = newGame.getClass().getDeclaredField("currentUserKey");
        currentUserKey.setAccessible(true);

        // test toggling current user key (i.e., toggle 0 and 1)
        assertEquals(currentUserKey.getInt(newGame), 0);

        toggleUserKey.invoke(newGame);
        assertEquals(currentUserKey.getInt(newGame), 1);

        toggleUserKey.invoke(newGame);
        assertEquals(currentUserKey.getInt(newGame), 0);

        toggleUserKey.invoke(newGame);
        assertEquals(currentUserKey.getInt(newGame), 1);

    }
}