package com.mygdx.battlefront;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.battlefront.screens.PlayScreen;

public class Battlefront extends Game {
	
	public PlayScreen playScreen;
	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		playScreen = new PlayScreen(this);
		setScreen(playScreen);
		
	}

	@Override
	public void render () {
		super.render();
	
	}
	
	@Override
	public void dispose () {
		
	}
}
