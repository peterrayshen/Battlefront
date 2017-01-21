package com.mygdx.battlefront;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;




public class MuzzleFlash extends Sprite{
	

	
	public MuzzleFlash(TextureRegion region, float width, float height) {
		
		this.setRegion(region);
		this.setSize(width, height);
	}
	
	public void update(SpriteBatch batch) {
		this.draw(batch);
	}

}
