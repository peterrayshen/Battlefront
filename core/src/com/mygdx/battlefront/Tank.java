package com.mygdx.battlefront;


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



public class Tank extends Sprite{

	public Body chassis;
	
	private static final float TANK_WIDTH = 3f;
	private static final float TANK_HEIGHT = 3.3f;
	private static final float SPRITE_WIDTH = 3.1f;
	private static final float SPRITE_HEIGHT = 3.5f;

	public Tank(World world, float x, float y) {
		
		
		
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

		chassis = world.createBody(bodyDef);
		chassis.createFixture(fixDef);
		chassis.setLinearDamping(15);
		
		setRegion(AssetLoader.playerChassis);
		setBounds(chassis.getPosition().x - getWidth() / 2, chassis.getPosition().y - getHeight() /2 , SPRITE_WIDTH, SPRITE_HEIGHT);
		this.setOriginCenter();

		

		

	}
	
	
	
	public void update() {
		
		this.setPosition(chassis.getPosition().x - getWidth() / 2, chassis.getPosition().y - getHeight() /2);
		this.setRotation(chassis.getTransform().getRotation() * MathUtils.radiansToDegrees);
		

	
	}
	
	public void goForward(float delta) {
		float x = (float) Math.cos(this.chassis.getTransform().getRotation() + Math.PI / 2);
		float y = (float) Math.sin(this.chassis.getTransform().getRotation() + Math.PI / 2);
		
		Vector2 dir = new Vector2(x, y);
		dir.scl(300);
		this.chassis.applyForceToCenter(dir, true);
	
	}
	
	public void goBackward (float delta) {
		float x = (float) Math.cos(this.chassis.getTransform().getRotation() + Math.PI / 2);
		float y = (float) Math.sin(this.chassis.getTransform().getRotation() + Math.PI / 2);
		
		Vector2 dir = new Vector2(x, y);
	
		dir.scl(300);
		dir.rotate(180);
		
		this.chassis.applyForceToCenter(dir, true);
	}
	
	public void rotClock(float delta) {
		this.chassis.setTransform(chassis.getWorldCenter(), (float)(chassis.getTransform().getRotation() - 50 * Math.PI / 180 * delta));
		
	}
	
	public void rotCounterClock(float delta) {
		this.chassis.setTransform(chassis.getWorldCenter(), (float)(chassis.getTransform().getRotation() + 50 * Math.PI / 180 * delta));
	}


}
