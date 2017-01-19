package com.mygdx.battlefront.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {
	
	public static Texture tanks;
	
	public static TextureRegion playerChassis, playerTurret;
	
	public static void load() {
		tanks = new Texture(Gdx.files.internal("tanks.png"));
		
		playerChassis = new TextureRegion(tanks, 0, 5, 428, 280);
		playerTurret = new TextureRegion(tanks, 458, 5, 501, 153);
		
	}
	
	public static void dispose() {
		tanks.dispose();
	}

}
