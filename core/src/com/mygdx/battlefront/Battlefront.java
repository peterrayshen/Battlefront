package com.mygdx.battlefront;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.battlefront.screens.PlayScreen;
import com.mygdx.battlefront.tools.AssetLoader;

public class Battlefront extends Game {
	
	public PlayScreen playScreen;
	public SpriteBatch batch;
	
	public static final int PLAYER_INDEX = -1;
	public static final int ENEMY_INDEX = -2;
	public static final int PROJECTILE_INDEX = -1;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		AssetLoader.load();
		playScreen = new PlayScreen(this);
		setScreen(playScreen);
		
	}

	@Override
	public void render () {
		super.render();
	
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
