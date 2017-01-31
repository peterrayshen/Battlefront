package com.mygdx.battlefront;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.World;

public class Bullet {

	//lifetime of the bullet
	private float lifeTime;
	public float lifeTimer;
	
	//direction X, direction Y
	private float dx, dy;

	public World world;
	public Body b2body;

	//bullet attributes
	public float damage = 8;
	public float speed;
	public float health;
	
	//the shape radius
	private float radius;
	
	//the physics body radius
	private float b2radius;
	private Color color;
	
	public boolean remove, isCircle;

	//constructor that takes in the physics world and the game screen as parameters
	public Bullet(World world) {
		this.world = world;

		//initializing variables
		remove = false;
		lifeTimer = 0;
		isCircle = true;
	}

	//setter method to set the bullet speed
	public void setSpeed(float speed) {
		this.speed = speed;
		dx = MathUtils.cos(b2body.getTransform().getRotation()) * speed;
		dy = MathUtils.sin(b2body.getTransform().getRotation()) * speed;

		b2body.setLinearVelocity(dx, dy);
	}

	//setter method, sets radius
	public void setRadius(float radius) {
		this.radius = radius;
	}

	//method that defines the physics body of the bullet
	public void defineBody(float radius, float x, float y, float radians) {
		this.b2radius = radius;
		defineCircleBullet(x, y, radians);

	}

	//setter method, sets damage of the bullet
	public void setDamage(float damage) {
		this.damage = damage;
	}

	//setter method, sets color of the bullet
	public void setColor(Color color) {
		this.color = color;
	}

	//setter method, sets health of the bullet
	public void setHealth(float health) {
		this.health = health;
	}

	//setter method, sets the lifetime of the bullet
	public void setLifetime(float lifetime) {
		this.lifeTime = lifetime;
	}

	//method that creates the circular physics body and spawns it in the physics world
	private void defineCircleBullet(float x, float y, float radians) {
		BodyDef bdef = new BodyDef();
		bdef.position.set(x, y);
		bdef.type = BodyDef.BodyType.KinematicBody;
		b2body = world.createBody(bdef);
		b2body.setTransform(b2body.getWorldCenter(), radians);

		CircleShape shape = new CircleShape();
		shape.setRadius(b2radius);
		
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.filter.groupIndex = Battlefront.PROJECTILE_INDEX;
		b2body.createFixture(fdef).setUserData(this);

		MassData mdata = new MassData();
		mdata.mass = 1;
		b2body.setMassData(mdata);
		b2body.setBullet(true);

	}


	//this method is called many times a second while the game is rendering
	public void update(float delta) {

		//if the time is past the bullet's lifetime, remove the bullet
		lifeTimer = lifeTimer + delta;
		if (lifeTimer > lifeTime) {
			remove = true;
		}

		//checks if bullet is within boundaries of the screen, if not, removes the bullet from play
		if (b2body.getPosition().x < -18f)
			remove = true;
		else if (b2body.getPosition().x > 34)
			remove = true;
		else if (b2body.getPosition().y > 15)
			remove = true;
		else if (b2body.getPosition().y < -17.5f)
			remove = true;
	}

	//getter method, returns whether bullet should be removed from play
	public boolean shouldRemove() {
		return remove;
	}

	//method that draws the bullet on the screen
	public void drawBullet(ShapeRenderer sr) {

		//sets the shape type, color, diameters and then draws it
		sr.begin(ShapeType.Filled);
		sr.setColor(color);
		sr.circle(b2body.getPosition().x, b2body.getPosition().y, radius, 100);
		sr.end();

	}
}
