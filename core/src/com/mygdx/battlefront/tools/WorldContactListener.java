package com.mygdx.battlefront.tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.battlefront.gameobjects.Bullet;
import com.mygdx.battlefront.gameobjects.Enemy;
import com.mygdx.battlefront.gameobjects.Player;
import com.mygdx.battlefront.screens.PlayScreen;

public class WorldContactListener implements ContactListener {
	
	//class that manages the collision of the bodies in our physics world

	private PlayScreen screen;

	public WorldContactListener(PlayScreen screen) {
		this.screen = screen;
	}

	@Override
	public void beginContact(Contact contact) {
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();

		// if a bullet hits an enemy and vice versa, the enemy loses health and the bullet is removed
		if (fixA.getUserData() instanceof Bullet && fixB.getUserData() instanceof Enemy) {

			((Enemy) fixB.getUserData()).health -= 40;
			((Bullet) fixA.getUserData()).remove = true;
		}
		
		if (fixB.getUserData() instanceof Bullet && fixA.getUserData() instanceof Enemy) {

			((Enemy) fixA.getUserData()).health -= 40;
			((Bullet) fixB.getUserData()).remove = true;
		}

		//if a player hits an enemy or vice versa, the player loses and the game ends
		if (fixB.getUserData() instanceof Player && fixA.getUserData() instanceof Enemy) {

			screen.game.setScreen(screen.game.loseScreen);
		}

		if (fixB.getUserData() instanceof Enemy && fixA.getUserData() instanceof Player) {
			screen.game.setScreen(screen.game.loseScreen);
		}
		// TODO Auto-generated method stub

	}

	@Override
	public void endContact(Contact contact) {
	
		// TODO Auto-generated method stub

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();

		//if a bullet hits anywhere on the enemy, the bullet does not apply a force to that enemy
		if (fixB.getUserData() instanceof Bullet && fixA.getUserData() instanceof Enemy) {
			contact.setEnabled(false);
		}

		if (fixB.getUserData() instanceof Enemy && fixA.getUserData() instanceof Bullet) {
			contact.setEnabled(false);
		}

		if (fixB.getUserData() instanceof Bullet && fixA.getUserData().equals("enemyTurret")) {
			contact.setEnabled(false);
		}

		if (fixB.getUserData().equals("enemyTurret") && fixA.getUserData() instanceof Bullet) {
			contact.setEnabled(false);
		}

		// TODO Auto-generated method stub

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

}
