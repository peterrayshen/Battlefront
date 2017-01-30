package com.mygdx.battlefront;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.battlefront.tools.AssetLoader;



public class Chassis extends Sprite{

	public Body b2body;
	
	private static final float TANK_WIDTH = 3f;
	private static final float TANK_HEIGHT = 3.3f;
	private static final float SPRITE_WIDTH = 3.1f;
	private static final float SPRITE_HEIGHT = 3.5f;

	public Chassis(World world, float x, float y) {
		
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x, y);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(TANK_WIDTH / 2, TANK_HEIGHT / 2);

		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.restitution = .1f;
		fixDef.friction = .5f;
		fixDef.filter.groupIndex = Battlefront.PLAYER_INDEX;

		b2body = world.createBody(bodyDef);
		b2body.createFixture(fixDef);
		b2body.setLinearDamping(20);
		
		setRegion(AssetLoader.playerChassis);
		setBounds(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() /2 , SPRITE_WIDTH, SPRITE_HEIGHT);
		this.setOriginCenter();

		

		

	}
	
	
	
	public void update() {
		
		this.setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() /2);
		this.setRotation(b2body.getTransform().getRotation() * MathUtils.radiansToDegrees);
		

	
	}
	
	


}
