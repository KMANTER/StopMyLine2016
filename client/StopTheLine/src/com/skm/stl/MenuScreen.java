package com.skm.stl;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.skm.stl.network.HttpCommunicationMngr;

public class MenuScreen implements Screen{
	  HttpCommunicationMngr requestManager;

	SpriteBatch batch = new SpriteBatch();
	
	Vector3 touchPoint = new Vector3();

	OrthographicCamera cam;
	Sprite mm_sprite,ms_logged_sprite;
	Game game;
	
	GameType gameType = GameType.Classic;
	private static String titreBoiteDialogueRegister = "Inscription"; 
	private static String texteInitialRegister = "NICKNAME"; 
	private static String titreBoiteDialogueLogin = "Se connecter"; 
	private static String texteInitialLogin = "NICKNAME"; 	
	private String message = "Pour jouer, veuillez se connecter";
	private String message2 = "ou inscrivez-vous";
	private String errorMsg = "";
	User us;
	
	public MenuScreen(Game game) {
		this.game = game;
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, 800, 480);
		
		mm_sprite = new Sprite(Assets.mainScreen);
		mm_sprite.setPosition(0, 0);
		
		ms_logged_sprite = new Sprite(Assets.mainScreenLogged);
		mm_sprite.setPosition(0, 0);
		
		gameType = GameType.StopMyLineType;
		
		requestManager = new HttpCommunicationMngr();

	}

	boolean touched(com.badlogic.gdx.math.Rectangle r){
		if (!Gdx.input.justTouched())
			return false;
		
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
	                	  errorMsg = "";
	                	  us = new User();
	                	  us.setName(texteSaisi);
	                	  SessionsManager.setSession(us);
	             /*   	  User existUser = requestManager.Connect(us);
	                	  
	                	  if(existUser != null){
			                  SessionsManager.setSession(us);
	                	  }
	                	  else{

	                		   errorMsg = "Utilisateur <"+texteSaisi+"> n'existe pas!";
	                		   SessionsManager.cleanSession();
	                	  }
	                	  */
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
                	  errorMsg = "";
                	  us = new User();
                	  us.setName(texteSaisi);
                	  requestManager.register(us);
                	  
                	  SessionsManager.setSession(us);
                	  
             /*   	  User registredUser = requestManager.Connect(us);
                	  
                	  if(registredUser != null){
		                  SessionsManager.setSession(us);
                	  }
                	  else{

                		   errorMsg = "probleme lors de l inscription !";
                		   SessionsManager.cleanSession();
                	  }
                	  */
                  }
                 
                  @Override
                  public void canceled() {

                  }
           }, titreBoiteDialogueRegister, texteInitialRegister);
		}

		if (touched(Assets.mainScreenStart)){
			game.setScreen(new GameScreen(game, gameType));
		}

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(cam.combined);
		
		batch.begin();
		
		batch.setColor(Assets.color3);
		
		
		if(SessionsManager.isConnected()){
			User currUsr = SessionsManager.getCurrSession();
            message = "Bonjour, <"+currUsr.getName()+"> !";
            message2 = "VOUS ETES CONNECTE !";
            batch.draw(mm_sprite, 0, 0);
		}
			
		else
			batch.draw(ms_logged_sprite, 0, 0);
			
		
		Assets.font.setScale(0.45F, 0.45F);

		if(errorMsg != ""){
			Assets.font.draw(batch, errorMsg, 410, 420);
		}
		else{
			Assets.font.draw(batch, message, 410, 420);
			Assets.font.draw(batch, message2, 410, 360);			
		}

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
		
	}}
