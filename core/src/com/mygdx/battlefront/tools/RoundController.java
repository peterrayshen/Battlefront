package com.mygdx.battlefront.tools;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Logger;
import com.mygdx.battlefront.Enemy;
import com.mygdx.battlefront.screens.PlayScreen;

public class RoundController {

	boolean inRound = false;

	public Enemy[] enemies;

	private float roundTimer;
	private int enemyCounter;
	private int maxEnemiesPerRound = 15;

	private World world;
	private PlayScreen screen;
	
	

	public RoundController(World world, PlayScreen screen) {
		this.world = world;
		this.screen = screen;
	
	}

	public void startRound() {
		roundTimer = 0;
		enemyCounter = 0;

		inRound = true;
		enemies = new Enemy[maxEnemiesPerRound];

		for (int i = 0; i < enemies.length; i++) {
			enemies[i] = new Enemy(world, screen, 40, MathUtils.random(-14, 9.5f));
			enemies[i].setInitialHealth(MathUtils.random(100, 500));
		}
		insertionSort(enemies);


	}

	public void endRound() {
		inRound = false;
	}

	public void update(float delta) {
		roundTimer += delta;
		
		if (inRound) {
			if (enemyCounter >= maxEnemiesPerRound) {
				endRound();
			} else if (MathUtils.round(roundTimer)  == 3) {
				screen.enemies.add(enemies[enemyCounter]);
				System.out.println(enemies[enemyCounter].health);
				enemyCounter++;
				roundTimer = 0;
				
			}
		}
		

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
