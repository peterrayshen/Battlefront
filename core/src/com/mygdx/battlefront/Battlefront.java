package com.mygdx.battlefront;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.battlefront.screens.LoseScreen;
import com.mygdx.battlefront.screens.PlayScreen;
import com.mygdx.battlefront.tools.AssetLoader;

public class Battlefront extends Game {
	
	public PlayScreen playScreen;
	public LoseScreen loseScreen;
	public SpriteBatch batch;
	
	public static final short PLAYER_INDEX = -1;
	public static final short ENEMY_INDEX = -2;
	public static final short PROJECTILE_INDEX = -1;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		AssetLoader.load();
		playScreen = new PlayScreen(this);
		loseScreen = new LoseScreen(batch);
		
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
