package com.shuqi.connect4;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author zsq
 * @since 1/10/2018
 */
public class BoardTest {
    private Board board;

    @BeforeMethod
    public void setUp() throws Exception
    {
        board = new Board(6, 7);
    }

    @Test
    public void testDropAndRender()
    {
        // test failed drop out of the board boundaries (0-6)
        assertFalse(board.drop(-1, 'R'));
        assertFalse(board.drop(7, 'R'));

        // fully fill a column with 6 valid drops
        for (int i=0; i<6; i++) {
            assertTrue(board.drop(2, 'G'));
        }

        // test failed drop to a full column
        assertFalse(board.drop(2, 'G'));

        // test valid drop to other columns
        assertTrue(board.drop(0, 'R'));
        assertTrue(board.drop(1, 'G'));
        assertTrue(board.drop(3, 'G'));
        assertTrue(board.drop(4, 'R'));
        assertTrue(board.drop(5, 'G'));
        assertTrue(board.drop(6, 'R'));

        // test render
        board.render();
    }
}