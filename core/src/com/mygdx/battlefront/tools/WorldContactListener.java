package com.mygdx.battlefront.tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.battlefront.Bullet;
import com.mygdx.battlefront.Chassis;
import com.mygdx.battlefront.Enemy;
import com.mygdx.battlefront.screens.PlayScreen;

public class WorldContactListener implements ContactListener {
	
	private PlayScreen screen;
	
	public WorldContactListener(PlayScreen screen) {
		this.screen = screen;
	}

	@Override
	public void beginContact(Contact contact) {
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endContact(Contact contact) {
	Fixture fixA = contact.getFixtureA();
	Fixture fixB = contact.getFixtureB();
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
		
		if (fixB.getUserData() instanceof Bullet && fixA.getUserData() instanceof Enemy) {
			contact.setEnabled(false);
			System.out.println("contact disabled");

		}

		if (fixB.getUserData() instanceof Enemy && fixA.getUserData() instanceof Bullet) {
			contact.setEnabled(false);
			System.out.println("contact disabled");
		}
		
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
