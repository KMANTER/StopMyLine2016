package com.skm.stl;

import com.skm.stl.GameScreen.PieceFactory;

public class GameType {
	public static GameType Test, Classic, StopMyLineType;
	
	static {
		
		Test = new GameType(new EasyFactory(), ".-.", "");
		
		StopMyLineType = new GameType(new FactoryRing(
				new PieceFactory[]{
						new PieceBagFactory(Assets.stopMyLinePieces)
				}),"Specific game type","Stop My Line  game type");
	}
	
	String title, description;
	PieceFactory factory;
	
	public GameType(PieceFactory p, String t, String d){
		factory = p;
		title = t;
		description = d;
	}
	
	boolean[][] nextPiece(){ return factory.nextPiece(); }
	String title(){ return title; }
	String description(){ return description; }
	
	static final int GRID_WIDTH = 11;
	
	static class EasyFactory implements PieceFactory {
		public boolean[][] nextPiece(){
			boolean easy[][] = new boolean[GRID_WIDTH][GRID_WIDTH];
			for(int i = 0; i < GRID_WIDTH;i++){
				easy[0][i] = true;
				easy[1][i] = true;
				easy[2][i] = true;
				easy[3][i] = true;
			}

			return easy;
		}
	}

	// Evenly, randomly from a predetermined list
	static class PieceBagFactory implements PieceFactory {
		// An array of pieces
		boolean[][][] pieces;
		
		PieceBagFactory(boolean[][][] pieces) {
			this.pieces = pieces;
		}
		
		public boolean[][] nextPiece() {
			return pieces[(int) Math.floor(Math.random() * pieces.length)];			
		}
	}
	
	static class FactoryRing implements PieceFactory {
		PieceFactory[] factories;
		int nextFactory = -1;
		
		public FactoryRing(PieceFactory[] fs){
			factories = fs;
		}

		@Override
		public boolean[][] nextPiece() {
			nextFactory = (nextFactory + 1) % factories.length;
			return factories[nextFactory].nextPiece();
		}
	}
}