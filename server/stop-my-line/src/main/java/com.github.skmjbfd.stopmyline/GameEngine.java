/**
 * This file is part of WANTED: Bad-ou-Alyve.
 *
 * WANTED: Bad-ou-Alyve is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WANTED: Bad-ou-Alyve is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WANTED: Bad-ou-Alyve.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.skmjbfd.stopmyline;

import com.github.skmjbfd.stopmyline.model.User;
import com.github.skmjbfd.stopmyline.model.TetrisGame;
import com.github.skmjbfd.stopmyline.exceptions.NoGameAvailableException;
import com.github.skmjbfd.stopmyline.exceptions.FullGameException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GameEngine {

    // Utils
    private static final Random random = new SecureRandom();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private String db_path;
    private List<User> userList;
    private List<TetrisGame> gameList;
    private File saveFile;

    public GameEngine(String dbPath) {
        this.db_path = dbPath;
        userList = new ArrayList<>();
        gameList = new ArrayList<>();
        saveFile = new File(dbPath);
        load();
    }

    public TetrisGame createGame(){
        String name = new BigInteger(130, random).toString(32).replace(" ", "").toString();
        TetrisGame game = new TetrisGame(name, 4, 18, 9);
        gameList.add(game);
        this.save();
        return game;
    }

    public User generateNewUser(String name) {
        String token = new BigInteger(130, random).toString(32).replace(" ", "");
        System.out.println("token ok");
        String pname =this.getFreeName(name);
        System.out.println("name ok");
        User user = new User(pname, token);
        System.out.println("user ok");
        userList.add(user);
        System.out.println("user added");
        this.save();
        System.out.println("save");
        return user;
    }

    private String getFreeName(String preferredName) {
        String name = preferredName;
        while (userAlreadyExists(name))
            name = preferredName + random.nextInt(999);
        return name;
    }

    private boolean userAlreadyExists(String name) {
        if(userList.size() > 0) {
            for (User u : userList) {
                if (u.name.equals(name)) return false;
            }
        }else{
            return false;
        }

        return true;
    }

    public User findUser(String name) {
        for(User u : userList){
            if(u.name.equals(name)) return u;
        }

        return null;
    }

    public List<TetrisGame> findGame() throws NoGameAvailableException {
        // Find the first game where the player can play (Game not full)
        List<TetrisGame> gameAvailable = new ArrayList<TetrisGame>();
        for(TetrisGame tg : gameList){
            if(!tg.isFull()){
                gameAvailable.add(tg);
            }
        }

        if(gameAvailable.size() == 0) {
            throw new NoGameAvailableException("There's currently no game available. Create a new one and play with your friends !");
        }

        return gameAvailable;
    }

    public TetrisGame getGame(String name) throws NoGameAvailableException{
        for(TetrisGame tg : gameList){
            if(tg.gameName.equals(name)) {
                return tg;
            }
        }

        throw new NoGameAvailableException("No game found");
    }


    public void joinGame(User player, TetrisGame game){
        if(!game.isFull()){
            try{
                game.addPlayerToGame(player);
                if(game.isFull()){
                    game.status = game.Status.Active;
                }
            }catch (FullGameException e){
                e.printStackTrace();
            }
        }
    }

    public void load() {
        try {
            String s = FileUtils.readFileToString(saveFile, "UTF-8");

            Type listType = new TypeToken<ArrayList<User>>(){}.getType();
            userList = new Gson().fromJson(s, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            FileUtils.writeStringToFile(saveFile, this.toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String toJson() {
        return gson.toJson(userList);
    }
}
