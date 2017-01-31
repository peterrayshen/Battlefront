package com.mygdx.battlefront.gameobjects;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.battlefront.tools.AssetLoader;



public class Chassis extends Sprite{

	//physics body
	public Body b2body;
	
	//dimensions of the sprite and the physics body
	private static final float TANK_WIDTH = 3f;
	private static final float TANK_HEIGHT = 3.3f;
	private static final float SPRITE_WIDTH = 3.1f;
	private static final float SPRITE_HEIGHT = 3.5f;

	//constructor
	public Chassis(World world, float x, float y, short filterIndex) {
		
		//defines the physics body, and places it in the physics world
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x, y);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(TANK_WIDTH / 2, TANK_HEIGHT / 2);

		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.restitution = .1f;
		fixDef.friction = .5f;
		fixDef.filter.groupIndex = filterIndex;

		b2body = world.createBody(bodyDef);
		b2body.createFixture(fixDef);
		b2body.setLinearDamping(20);
		
		//sets the sprite to the playerChassis image
		setRegion(AssetLoader.chassis);
		
		//sets dimensions / position for the sprite
		setBounds(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() /2 , SPRITE_WIDTH, SPRITE_HEIGHT);
		//sets the origin of the sprite to its center
		this.setOriginCenter();
	}
	
	//this method is called many times each second, updates the image to match the corresponding position/rotation of the physics body
	//as the physics body moves and interacts with the world,the sprite needs to be updated with it
	public void update() {
		this.setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() /2);
		this.setRotation(b2body.getTransform().getRotation() * MathUtils.radiansToDegrees);
	}
}
