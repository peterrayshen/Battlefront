package com.mygdx.battlefront.tools;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.battlefront.Enemy;
import com.mygdx.battlefront.screens.PlayScreen;

public class RoundController {

	boolean inRound = false;

	//array of enemies
	public Enemy[] enemies;

	//variables to help keep track of the round
	private float roundTimer;
	private int enemyCounter;
	private int maxEnemiesPerRound = 15;

	private World world;
	private PlayScreen screen;
	
	//round number
	public int round;
	

	//constructor
	public RoundController(World world, PlayScreen screen) {
		this.world = world;
		this.screen = screen;
		round = 0;
	
	}

	//starts the round
	public void startRound() {
		round++;
		
		//resets the round variables
		roundTimer = 0;
		enemyCounter = 0;
		inRound = true;
		
		//creates an array of enemies
		enemies = new Enemy[maxEnemiesPerRound];

		//initiates each enemy in the array
		for (int i = 0; i < enemies.length; i++) {
			//spawns an enemy with a random y coordinate
			enemies[i] = new Enemy(world, screen, 40, MathUtils.random(-14, 9.5f));
			//sets their health from 100-500
			enemies[i].setInitialHealth(MathUtils.random(100, 500));
			//sets their speed to be random and scales depending on the round
			enemies[i].speed = MathUtils.random(450 + round * 50, 850 + round * 50);
		}
		
		//sorts the array of enemies from lowest health to highest health
		sortByHealth(enemies);


	}

	//ends the round
	public void endRound() {
		inRound = false;
	}

	//updates the round many times a second
	public void update(float delta) {
		roundTimer += delta;
		
		if (inRound) {
			//if all enemies have spawned, end the round
			if (enemyCounter >= maxEnemiesPerRound) {
				endRound();
			} else if (MathUtils.round(roundTimer) == 3) {
				//spawns an enemy every 3 seconds
				screen.enemies.add(enemies[enemyCounter]);
				enemyCounter++;
				roundTimer = 0;
			}
		}
		else {
			if (roundTimer > 5) {
				//counts 5 seconds after each round before a new round starts
				startRound();
			}
		}

	}

	//insertion sort that sorts the array of enemies by their health
	public void sortByHealth(Enemy[] enemies) {

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
