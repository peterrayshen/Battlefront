package com.mygdx.battlefront;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.battlefront.screens.PlayScreen;
import com.mygdx.battlefront.tools.AssetLoader;

public class Bullet {

	private float lifeTime;
	public float lifeTimer;
	private float dx, dy;

	public World world;

	public Body b2body;

	public float damage = 8;

	private float speed;
	public float health;
	private float radius, height, width;
	private float b2radius, b2height, b2width;
	private Color color;
	private boolean isShotgun;

	private PlayScreen screen;

	public boolean remove, isCircle;

	public Bullet(World world, PlayScreen screen) {
		this.screen = screen;
		this.world = world;

		remove = false;
		lifeTimer = 0;
		isCircle = true;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
		dx = MathUtils.cos(b2body.getTransform().getRotation()) * speed;
		dy = MathUtils.sin(b2body.getTransform().getRotation()) * speed;

		b2body.setLinearVelocity(dx, dy);
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public void defineBody(float radius, float x, float y, float radians) {
		this.b2radius = radius;

		defineCircleBullet(x, y, radians);

	}

	public void setDamage(float damage) {
		this.damage = damage;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public void setLifetime(float lifetime) {
		this.lifeTime = lifetime;
	}

	private void defineCircleBullet(float x, float y, float radians) {
		BodyDef bdef = new BodyDef();
		bdef.position.set(x, y);
		bdef.type = BodyDef.BodyType.KinematicBody;
		b2body = world.createBody(bdef);
		b2body.setTransform(b2body.getWorldCenter(), radians);

		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(b2radius);
		fdef.shape = shape;
		fdef.filter.groupIndex = Battlefront.PROJECTILE_INDEX;
		b2body.createFixture(fdef).setUserData(this);

		MassData mdata = new MassData();
		mdata.mass = 1;
		b2body.setMassData(mdata);
		b2body.setBullet(true);

	}

	public void kill() {
		this.b2body.destroyFixture(b2body.getFixtureList().first());
		

	}

	public void update(float delta) {


		lifeTimer = lifeTimer + delta;
		if (lifeTimer > lifeTime) {
			remove = true;
		}

	}

	public boolean shouldRemove() {
		return remove;
	}

	public void drawBullet(ShapeRenderer sr) {

		sr.begin(ShapeType.Filled);
		sr.setColor(color);
		sr.circle(b2body.getPosition().x, b2body.getPosition().y, radius, 100);
		sr.end();

	}
}
