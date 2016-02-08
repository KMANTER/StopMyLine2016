package com.github.skmjbfd.stopmyline.model;

/**
 * Created by Jbay on 03/02/2016.
 */
public class GameResult {

    public static final short VICTORY = 1;
    public static final short DEFEAT = 2;
    public static final short NO_GAME_FOUND = -1;

    public short result;
    public User user;

    public GameResult() {

    }

    public GameResult(short result, User user) {
        this.result = result;
        this.user = user;
    }

}
