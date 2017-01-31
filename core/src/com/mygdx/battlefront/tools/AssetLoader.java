package com.mygdx.battlefront.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {
	
	//this class loads all the assets for the game such as sound, and images
	
	//all the textures for the sprite
	public static Texture tanks, flare, blueTank;
	
	//these texture regions are cut out from the textures
	public static TextureRegion chassis, turret, cannonFlare;
	
	//sound variable
	public static Sound cannonFiring;
	
	public static void load() {
		//loads all the textures
		tanks = new Texture(Gdx.files.internal("tanks.png"));
		flare = new Texture(Gdx.files.internal("cannon_flare.png"));
		blueTank = new Texture(Gdx.files.internal("blue_tank.png"));
		
		//cuts out the tank chassis and turret from the texture
		chassis = new TextureRegion(blueTank, 741, 139, 485, 614);
		turret = new TextureRegion(blueTank, 1296, 80, 233, 532);
		
		//muzzle flash image
		cannonFlare = new TextureRegion(flare, 143, 34, 555, 364);
		
		//shooting sound effect
		cannonFiring = Gdx.audio.newSound(Gdx.files.internal("cannon_sound.mp3"));
		
	}
	
	//disposes of resources
	public static void dispose() {
		tanks.dispose();
		flare.dispose();
		blueTank.dispose();
	}

}
