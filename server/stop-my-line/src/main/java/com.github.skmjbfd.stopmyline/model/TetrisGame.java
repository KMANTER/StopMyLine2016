package com.github.skmjbfd.stopmyline.model;

import java.util.HashMap;
import com.github.skmjbfd.stopmyline.exceptions.FullGameException;

/**
 * Created by Jbay on 03/02/2016.
 */
public class TetrisGame {

    public enum Status {Waiting, Active, Finished}

    public int maxPlayer;
    public String gameName;
    public HashMap<User, Boolean> playerList;
    public int rows;
    public int columns;
    public Status status;

    public TetrisGame(String name, int maxPlayer, int nbRows, int nbCols){
        this.gameName = name;
        this.maxPlayer = maxPlayer;
        this.playerList = new HashMap<>();
        this.rows = nbRows;
        this.columns = nbCols;
        this.status = Status.Waiting;
    }

    public boolean isFull(){
        if(this.playerList.size() == maxPlayer) return true;
        return false;
    }

    // Add the player to the game. Return false if it couldn't.
    public boolean addPlayerToGame(User player) throws FullGameException{
        if(!this.isFull()){
            playerList.put(player, false);
            return true;
        }
        throw new FullGameException("There's no room left in this game");
    }

    public int playersInGame(){
        int stillInGame = 0;
        for (User u : this.playerList.keySet()) {
            if (!this.playerList.get(u)) {
                stillInGame++;
            }
        }

        return stillInGame;
    }

    /**
     * End the game for a player.
     * @param player The player who lost
     * @return The last player
     */
    public User gameOver(User player) {
        int stillInGame = 0;
        this.playerList.put(player, true);
        User last = null;
        for (User u : this.playerList.keySet()) {
            if (!this.playerList.get(u)) {
                last = u;
                stillInGame++;
            }
        }

        if (stillInGame == 1)
            return last;

        return null;
    }

}
