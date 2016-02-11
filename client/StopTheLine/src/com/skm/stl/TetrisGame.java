package com.skm.stl;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;


public class TetrisGame extends Game implements ApplicationListener {
	@Override
	public void create() {		
		Assets.load();
		
		this.setScreen(new MenuScreen(this));
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}