package com.shuqi.connect4;

/**
 * Connect 4 game board
 *
 * @author zsq
 * @since 1/10/2018
 */
public class Board {
    // column boundary char
    private final char BORDER_CHAR = '|';

    // dimensions of board
    private int rowNum, colNum;

    // matrix to store tokens dropped to the board
    private char[][] dataMatrix;

    public Board(int rowNum, int colNum)
    {
        this.rowNum = rowNum;
        this.colNum = colNum;
        dataMatrix = new char[rowNum][colNum];

        // initialize board with empty token (i.e. whitespace)
        for (int i=0; i<rowNum; i++) {
            for (int j=0; j<colNum; j++) {
                dataMatrix[i][j] = ' ';
            }
        }
    }

    // drop a token if it's within the board and the column is not full, return true
    // else return false
    public boolean drop(int col, char targetChar)
    {
        if (col > -1 && col < colNum) {
            for (int i=0; i<rowNum; i++) {
                if (dataMatrix[rowNum-1-i][col] == ' ') {
                    dataMatrix[rowNum-1-i][col] = targetChar;
                    return true;
                }
            }
        }
        return false;
    }

    // print to console
    public void render()
    {
        for (int i=0; i<rowNum; i++) {
            System.out.print(BORDER_CHAR);
            for (int j=0; j<colNum; j++) {
                System.out.print(dataMatrix[i][j]);
                System.out.print(BORDER_CHAR);
            }
            System.out.print('\n');
        }
    }

    public char[][] getDataMatrix() {
        return dataMatrix;
    }
}
