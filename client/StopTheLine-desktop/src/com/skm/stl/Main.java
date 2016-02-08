package com.skm.stl;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "StopTheLine";
		cfg.width = 780;
		cfg.height = 460;
		
		new LwjglApplication(new TetrisGame(), cfg);
	}
}
