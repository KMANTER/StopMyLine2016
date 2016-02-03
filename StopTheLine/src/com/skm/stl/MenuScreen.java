package com.skm.stl;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class MenuScreen implements Screen{
	SpriteBatch batch = new SpriteBatch();
	
	Vector3 touchPoint = new Vector3();

	OrthographicCamera cam;
	Sprite mm_sprite;
	Game game;
	
	GameType gameType = GameType.Classic;
	private static String titreBoiteDialogueRegister = "Inscription"; 
	private static String texteInitialRegister = "votre NICKNAME"; 
	private static String titreBoiteDialogueLogin = "Se connecter"; 
	private static String texteInitialLogin = "votre NICKNAME"; 	
	private String message = "Pour jouer, veuillez se connecter";
	private String message2 = "ou inscrivez-vous";
	User us;
	
	public MenuScreen(Game game) {
		this.game = game;
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, 800, 480);
		
		mm_sprite = new Sprite(Assets.mainScreen);
		mm_sprite.setPosition(0, 0);
		gameType = GameType.Classic;
		// width and height are automatic...
	}

	boolean touched(com.badlogic.gdx.math.Rectangle r){
		if (!Gdx.input.justTouched())
			return false;
		
		// If this could possibly be slow, I could move it t...
		// It won't be slow
		cam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
		
		return r.contains(touchPoint.x, touchPoint.y);
	}
	
	@Override
	public void render(float delta) {
		
	     Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

         
		if (touched(Assets.mainScreenMode1)){
				  Gdx.input.getTextInput(new TextInputListener() {
					
	                  @Override
	                  public void input(String texteSaisi) {
	                	  us = new User();
	                	  us.setName(texteSaisi);
	                	  boolean exitUser = UsersManger.existUser(us);
	                	  if(exitUser){
			                  message = "Bonjour, <"+texteSaisi+"> !";
			                  message2 = "Vous êtes connecté !";
	                	  }
	                	  else{
			                  message = "Utilisateur <"+texteSaisi+"> n'existe pas!";
			                  message2 = "Veuillez réessayez !";	                		  
	                	  }
	                  }
	                 
	                  @Override
	                  public void canceled() {

	                  }
	           }, titreBoiteDialogueLogin, texteInitialLogin);
		}

		if (touched(Assets.mainScreenMode2)){
			  Gdx.input.getTextInput(new TextInputListener() {
				
                  @Override
                  public void input(String texteSaisi) {
                	  us = new User();
                	  us.setName(texteSaisi);
                	  UsersManger.addUser(us);
                	  
	                  message = "Bonjour, <"+texteSaisi+"> !";
	                  message2 = "vous êtes bien inscrit !";
                  }
                 
                  @Override
                  public void canceled() {

                  }
           }, titreBoiteDialogueRegister, texteInitialRegister);
		}

		if (touched(Assets.mainScreenMode3)){
			gameType = GameType.ThreesAndFives;
		}

		if (touched(Assets.mainScreenStart)){
			game.setScreen(new GameScreen(game, gameType));
		}

		if (touched(Assets.mainScreenTest)){
			gameType = GameType.Test;
		}

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(cam.combined);
		
		batch.begin();
		
		batch.setColor(Assets.color2);
		
		batch.draw(mm_sprite, 0, 0);

		//String typeName1 = gameType.title();
		//String typeName2 = gameType.description();
		Assets.font.setScale(0.45F, 0.45F);
		Assets.font.draw(batch, message, 410, 420);
		Assets.font.draw(batch, message2, 410, 360);
		

		//mm_sprite.draw(batch);
		
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}


}
