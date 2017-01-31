package com.mygdx.battlefront.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.battlefront.screens.LoseScreen;
import com.mygdx.battlefront.screens.PlayScreen;
import com.mygdx.battlefront.tools.AssetLoader;

public class Battlefront extends Game {
	
	public PlayScreen playScreen;
	public LoseScreen loseScreen;
	public SpriteBatch batch;
	
	//storing filter indexes for collision management
	public static final short PLAYER_INDEX = -1;
	public static final short ENEMY_INDEX = -2;
	public static final short PROJECTILE_INDEX = -1;
	
	@Override
	//this method is called the moment the game is launched
	public void create () {
		batch = new SpriteBatch();
		
		//loads all assets from AssetLoader class (images, sounds etc)
		AssetLoader.load();
		
		//loads the background music
		loadMusic();
		
		//initiates game screens, one for while the user is playing and one for when he/she loses
		playScreen = new PlayScreen(this);
		loseScreen = new LoseScreen(batch);
		
		//game starts off with play screen
		setScreen(playScreen);
		
	}
	
	//method that loads and plays the background music
	public void loadMusic() {
		AssetManager assetManager = new AssetManager();
		assetManager.load("song.mp3", Music.class);
		assetManager.finishLoading();
		
		Music music = assetManager.get("song.mp3", Music.class);
		music.setLooping(true);
		music.setVolume(30);
		music.play();
	}

	@Override
	//calls the screen to render
	//this method is called many times each and every second
	public void render () {
		super.render();
	
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
