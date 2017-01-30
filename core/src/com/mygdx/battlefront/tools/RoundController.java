package com.mygdx.battlefront.tools;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.battlefront.Enemy;
import com.mygdx.battlefront.screens.PlayScreen;

public class RoundController {
	
	boolean inRound = false;
	
	public Enemy[] enemies;
	
	public void startRound(World world, PlayScreen screen) {
		inRound = true;
		enemies = new Enemy[15];
		
		for (int i = 0; i < enemies.length; i++) {
			enemies[i] = new Enemy(world, screen, 40, MathUtils.random(-14, 9.5f));
			enemies[i].setHealth(MathUtils.random(100, 500));
		}
		insertionSort(enemies);
		
		for (int i = 0; i < enemies.length; i ++) {
			System.out.println(enemies[i].health);
		}
		
		
	}
	
	public void endRound() {
		inRound = false;
	}
	
	public void update() {
		
	}
	
	public void insertionSort(Enemy[] enemies) {
		
		for (int top = 1; top < enemies.length; top++) {
			Enemy item = enemies[top]; 
	
			int i = top;
			while (i > 0 && item.health < enemies[i - 1].health) { 
				enemies[i] = enemies[i - 1];
			
				i--;
			}
			enemies[i] = item;
		
		}
		
	}


}
