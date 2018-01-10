package com.shuqi.connect4;

/**
 * Connect 4 User
 *
 * @author zsq
 * @since 1/10/2018
 */
public class User {
    // user name, e.g. "Red", "Green"
    private String userName;

    // token assigned to a user, e.g. 'G', 'R'
    private char token;

    public User(String userName, char token)
    {
        this.userName = userName;
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public char getToken() {
        return token;
    }
}
