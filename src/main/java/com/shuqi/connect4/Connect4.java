package com.shuqi.connect4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Console version Connect 4 game
 *
 * @author zsq
 * @since 1/9/2018
 */
public class Connect4 {
    private static final Logger logger = LoggerFactory.getLogger(Connect4.class);
    private final int CONNECT_NUM = 4;

    // Dimensions of the board
    private int rowNum;
    private int colNum;

    private Board board;
    private Map<Integer, User> userList = new HashMap<>();
    private int currentUserKey; // indicate user turn

    private int dropCount; // total tokens that has been dropped

    public Connect4(int rowNum, int colNum)
    {
        this.rowNum = rowNum;
        this.colNum = colNum;
    }

    // Initialize game
    // 1. create game board
    // 2. add two users
    // 3. initialize current user key and drop count
    public void init()
    {
        board = new Board(rowNum, colNum);

        userList.put(0, new User("RED", 'R'));
        userList.put(1, new User("GREEN", 'G'));
        currentUserKey = 0;

        dropCount = 0;
    }

    // toggle current user key
    // 0 - user "Red"
    // 1 - user "Green"
    private void toggleUserKey()
    {
        currentUserKey = currentUserKey ^ 1;
    }

    private boolean checkWin()
    {
        char targetToken = userList.get(currentUserKey).getToken();
        char[][] dataMatrix = board.getDataMatrix();

        // check horizontal, vertical, forward and backward diagonal
        // return true if any consecutive four target tokens detected
        if (checkHorizontal(dataMatrix, targetToken)
                || checkVertical(dataMatrix, targetToken)
                || checkForwardDiagonal(dataMatrix, targetToken)
                || checkBackwardDiagonal(dataMatrix, targetToken))
            return true;

        return false;
    }

    public boolean checkHorizontal(char[][] dataMatrix, char targetToken)
    {
        for(int i=0; i<rowNum; i++) {
            int count = 0;
            for (int j=0; j<colNum; j++) {
                if (dataMatrix[i][j] == targetToken) {
                    count += 1;
                } else {
                    count = 0;
                }

                if (count == 4) return true;
            }
        }
        return false;
    }

    public boolean checkVertical(char[][] dataMatrix, char targetToken)
    {
        for(int j=0; j<colNum; j++) {
            int count = 0;
            for (int i=0; i<rowNum; i++) {
                if (dataMatrix[i][j] == targetToken) {
                    count += 1;
                } else {
                    count = 0;
                }

                if (count == 4) return true;
            }
        }
        return false;
    }

    public boolean checkForwardDiagonal(char[][] dataMatrix, char targetToken)
    {
        // check along left and lower boundary points as starting points
        for (int i=0; i<rowNum; i++) {
            int iLocal = i;
            int j = 0;
            int count = 0;
            while (iLocal<rowNum && j<colNum) {
                if (dataMatrix[iLocal][j] == targetToken) {
                    count += 1;
                } else {
                    count = 0;
                }

                if(count == 4) return true;

                iLocal += 1;
                j += 1;
            }
        }

        for (int j=1; j<colNum; j++) {
            int jLocal = j;
            int i = 0;
            int count = 0;
            while (i<rowNum && jLocal<colNum) {
                if (dataMatrix[i][jLocal] == targetToken) {
                    count += 1;
                } else {
                    count = 0;
                }

                if(count == 4) return true;

                jLocal += 1;
                i += 1;
            }
        }

        return false;
    }

    public boolean checkBackwardDiagonal(char[][] dataMatrix, char targetToken)
    {
        // check along right and lower boundary points as starting points
        for (int i=0; i<rowNum; i++) {
            int iLocal = i;
            int j = colNum-1;
            int count = 0;
            while (iLocal<rowNum && j>-1) {
                if (dataMatrix[iLocal][j] == targetToken) {
                    count += 1;
                } else {
                    count = 0;
                }

                if(count == 4) return true;

                iLocal += 1;
                j -= 1;
            }
        }

        for (int j=1; j<colNum-1; j++) {
            int jLocal = j;
            int i = 0;
            int count = 0;
            while (i<rowNum && jLocal>-1) {
                if (dataMatrix[i][jLocal] == targetToken) {
                    count += 1;
                } else {
                    count = 0;
                }

                if(count == 4) return true;

                jLocal -= 1;
                i += 1;
            }
        }

        return false;
    }

    private void start()
    {
        init();

        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                // Ask for player input
                System.out.format("PLAYER %d [%s] - CHOOSE COLUMN (1-7): ", currentUserKey+1, userList.get(currentUserKey).getUserName());
                String input = br.readLine().trim();

                // Allow the user to exit game by entering 'q'
                if ("q".equals(input.toLowerCase())) {
                    System.out.println("EXIT GAME");
                    System.exit(0);
                }

                // For each round, do the following tasks
                // 1. Drop token to the chosen column
                // 2. For valid drop, check if it's a win
                // 3. For valid drop, check if the board is full
                // 4. Exit game when a player wins or it is a draw
                // 5. Toggle user turn after a valid drop
                try {
                    int col = Integer.parseInt(input);
                    if (board.drop(col-1, userList.get(currentUserKey).getToken())) {
                        board.render();

                        if (checkWin()) {
                            System.out.format("PLAYER %d [%s] WINS\n", currentUserKey+1, userList.get(currentUserKey).getUserName());
                            System.exit(0);
                        }

                        dropCount += 1;
                        if (dropCount >= rowNum * colNum) {
                            System.out.println("DRAW");
                            System.exit(0);
                        }

                        toggleUserKey();

                    } else {
                        System.out.format("INVALID DROP AT COLUMN %d\n", col);
                    }


                } catch (NumberFormatException e) {
                    System.out.println("INVALID INPUT");
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Connect4 newGame = new Connect4(6, 7);
        newGame.start();
    }

}
