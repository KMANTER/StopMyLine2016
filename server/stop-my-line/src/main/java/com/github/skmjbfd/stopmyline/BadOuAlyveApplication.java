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

@SpringBootApplication
@RestController
public class BadOuAlyveApplication extends WebMvcConfigurerAdapter {

    private static String DB_PATH = System.getProperty("java.io.tmpdir") + "stopmyline_" + System.currentTimeMillis() + ".json";
    private static GameEngine gameHandler = new GameEngine(DB_PATH);
    private final String LOGGER_NAME = "Stop My Line";

    /** This method is called when accessing the API root, display usage */
    @RequestMapping("/")
    @ResponseBody
    public HomeMessage home() {
        Logger.getLogger(LOGGER_NAME).info("home()");

        HomeMessage homeMessage = new HomeMessage("Welcome to Tetris: Stop My Line server!");
        homeMessage.addOperation("/connect/{name}", "Connect to the server as {name}");
        homeMessage.addOperation("/users/{name}", "Get self as {name}");
        homeMessage.addOperation("/users/{name}/game", "Game as {name}");
        return homeMessage;
    }

    /** Connect a new user on the server */
    @RequestMapping("/connect/{name}")
    @ResponseBody
    public User connect(@PathVariable final String name) throws IOException {
        Logger.getLogger(LOGGER_NAME).info("connect(): " + name);

        User user = gameHandler.generateNewUser(name);
        gameHandler.save();
        return user;
    }

    /** Return the user's data after updating his stats */
    @RequestMapping("/users/{name}")
    @ResponseBody
    public User user(@PathVariable final String name) throws IOException {
        Logger.getLogger(LOGGER_NAME).info("user(): " + name);

        User user = gameHandler.findUser(name);

        return user;
    }

    @RequestMapping("/users/{name}/game")
    @ResponseBody
    public void userGame(@PathVariable final String name) throws IOException {
        Logger.getLogger(LOGGER_NAME).info("userFight(): " + name);

        User user = gameHandler.findUser(name); // Retrieve user
        try{
            TetrisGame game = gameHandler.findGame(user);

        }catch(NoGameAvailableException e){
            Logger.getLogger(e.getMessage());
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(BadOuAlyveApplication.class, args);
        Logger.getAnonymousLogger().info("SERVER LAUNCHED");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // Configure spring to use Gson to map object to json for response
        GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
        converters.add(gsonHttpMessageConverter);
    }
}
