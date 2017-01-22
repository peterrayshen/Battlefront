package com.mygdx.battlefront.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {
	
	public static Texture tanks, flare, bg, blueTank;
	
	public static TextureRegion playerChassis, playerTurret, cannonFlare;
	
	public static Sound cannonFiring;
	
	public static void load() {
		tanks = new Texture(Gdx.files.internal("tanks.png"));
		flare = new Texture(Gdx.files.internal("cannon_flare.png"));
		bg = new Texture(Gdx.files.internal("bg.png"));
		blueTank = new Texture(Gdx.files.internal("blue_tank.png"));
		
		cannonFiring = Gdx.audio.newSound(Gdx.files.internal("cannon_sound.mp3"));
		
		playerChassis = new TextureRegion(blueTank, 741, 139, 485, 614);
		playerTurret = new TextureRegion(blueTank, 1296, 80, 233, 532);
		
		cannonFlare = new TextureRegion(flare, 143, 34, 555, 364);
		
	}
	
	public static void dispose() {
		tanks.dispose();
		flare.dispose();
		bg.dispose();
		blueTank.dispose();
	}

}
