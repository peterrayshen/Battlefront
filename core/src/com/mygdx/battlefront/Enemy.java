package com.mygdx.battlefront;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.battlefront.tools.AssetLoader;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Enemy extends Sprite {
	
	public static final float SPRITE_WIDTH = 2.5f;
	public static final float SPRITE_HEIGHT = 4.5f;
	
	public static final float BODY_WIDTH = 2.5f;
	public static final float BODY_HEIGHT = 4.5f;
	
	public Body b2body;
	
	private World world;
	
	private Chassis tank;
	private Turret turret;
	
	public Enemy(World world, float x, float y) {
		
		this.world = world;
		
		defineBody(x, y);
		
		this.setOriginCenter();
		this.setBounds(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2, SPRITE_WIDTH, SPRITE_HEIGHT);
		this.setRegion(AssetLoader.car);
	}
	
	public void defineBody(float x, float y) {
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x, y);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(BODY_WIDTH / 2, BODY_HEIGHT / 2);

		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.restitution = .1f;
		fixDef.friction = .5f;
		fixDef.filter.groupIndex = Battlefront.ENEMY_INDEX;
		
		b2body = world.createBody(bodyDef);
		b2body.createFixture(fixDef);
		b2body.setLinearDamping(15);
		
	}
	
	public void update() {
		this.setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
		this.setRotation(MathUtils.radiansToDegrees * b2body.getTransform().getRotation());
	}

}
