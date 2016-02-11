package com.github.skmjbfd.stopmyline;

import com.github.skmjbfd.stopmyline.exceptions.NoGameAvailableException;
import com.github.skmjbfd.stopmyline.model.HomeMessage;
import com.github.skmjbfd.stopmyline.model.TetrisGame;
import com.github.skmjbfd.stopmyline.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Jbay on 04/02/2016.
 */

@SpringBootApplication
@RestController
public class StopMyLineApplication extends WebMvcConfigurerAdapter {

    private static String DB_PATH = System.getProperty("java.io.tmpdir") + "stopmyline_" + System.currentTimeMillis() + ".json";
    private static GameEngine gameHandler = new GameEngine(DB_PATH);
    private final String LOGGER_NAME = "Stop My Line";


    public static void main(String[] args) {
        SpringApplication.run(StopMyLineApplication.class, args);
        Logger.getAnonymousLogger().info("SERVER LAUNCHED");
    }

    /** This method is called when accessing the API root, display usage */
    @RequestMapping("/")
    @ResponseBody
    public HomeMessage home() {
        Logger.getLogger(LOGGER_NAME).info("home()");

        HomeMessage homeMessage = new HomeMessage("Welcome to Tetris: Stop My Line server!");
        homeMessage.addOperation("/user/create/{name}", "Register to the server as {name}");
        homeMessage.addOperation("/user/{name}", "Get self as {name}");
        homeMessage.addOperation("/game/create", "Create a new Game");
        homeMessage.addOperation("/game/check", "List of game available");
        homeMessage.addOperation("/game/{gname}/user/{uname}", "{uname} join the game {gname}");
        return homeMessage;
    }

    /** Connect a new user on the server */
    @RequestMapping("/user/create/{name}")
    @ResponseBody
    public User register(@PathVariable final String name) throws IOException {
        Logger.getLogger(LOGGER_NAME).info("connect(): " + name);

        User user = gameHandler.generateNewUser(name);
        gameHandler.save();
        return user;
    }

    /** Return the user's data after updating his stats */
    @RequestMapping("/user/{name}")
    @ResponseBody
    public User connect(@PathVariable final String name) throws IOException {
        Logger.getLogger(LOGGER_NAME).info("user(): " + name);

        User user = gameHandler.findUser(name);

        return user;
    }

    @RequestMapping("/game/create")
    @ResponseBody
    public TetrisGame createGame() throws IOException {
        TetrisGame game = gameHandler.createGame();
        Logger.getLogger(LOGGER_NAME).info("game Found : " + game.gameName);
        return game;
    }

    @RequestMapping("/game/check")
    @ResponseBody
    public List<TetrisGame> checkGame() throws IOException {
        try{
            return gameHandler.findGame();
        }catch(NoGameAvailableException e){
            Logger.getLogger(e.getMessage());
            return null;
        }
    }

    @RequestMapping("/game/{gname}/user/{uname}")
    @ResponseBody
    public TetrisGame joinGame(@PathVariable final String gname, @PathVariable final String uname) throws IOException {
        try{
            TetrisGame game = gameHandler.getGame(gname);
            User player = gameHandler.findUser(uname);

            gameHandler.joinGame(player, game);
            return game;
        }catch(NoGameAvailableException e){
            Logger.getLogger(e.getMessage());
            return null;
        }
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // Configure spring to use Gson to map object to json for response
        GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
        converters.add(gsonHttpMessageConverter);
    }
}
