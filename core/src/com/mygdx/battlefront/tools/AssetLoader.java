package com.mygdx.battlefront.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {
	
	public static Texture tanks, flare;
	
	public static TextureRegion playerChassis, playerTurret, cannonFlare;
	
	public static Sound cannonFiring;
	
	public static void load() {
		tanks = new Texture(Gdx.files.internal("tanks.png"));
		flare = new Texture(Gdx.files.internal("cannon_flare.png"));
		
		cannonFiring = Gdx.audio.newSound(Gdx.files.internal("cannon_sound.mp3"));
		
		playerChassis = new TextureRegion(tanks, 0, 5, 428, 280);
		playerTurret = new TextureRegion(tanks, 458, 5, 501, 153);
		
		cannonFlare = new TextureRegion(flare, 143, 34, 555, 364);
		
	}
	
	public static void dispose() {
		tanks.dispose();
	}

}
