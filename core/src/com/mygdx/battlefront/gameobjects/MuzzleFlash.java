package com.mygdx.battlefront.gameobjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MuzzleFlash extends Sprite{
	
	public MuzzleFlash(TextureRegion region, float width, float height) {
		
		//constructor that sets that has the sprite, and the dimensions as parameters
		this.setRegion(region);
		this.setSize(width, height);
	}
}
