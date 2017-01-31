package com.mygdx.battlefront;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.mygdx.battlefront.screens.PlayScreen;
import com.mygdx.battlefront.tools.AssetLoader;

public class Turret extends Sprite {

	//static variables for the physics body and sprite dimensions
	private static final float TURRET_WIDTH = 0.4f;
	private static final float TURRET_HEIGHT = 1.4f;
	private static final float SPRITE_WIDTH = 1.5f;
	private static final float SPRITE_HEIGHT = 3.3f;

	//radius from the center of the turret to the outer tip of the cannon
	public static float fpRadius = 2f;

	//physics bodies
	public RevoluteJoint joint;
	public Body b2body;
	
	private World world;
	
	//x and y coordinates of the tip of the cannon
	private Vector2 firePoint;

	public MuzzleFlash muzzleFlash;
	

	public Turret(World world, Chassis chassis, short filterIndex) {
		
		this.world = world;

		//defines the physics body
		defineBody(chassis, filterIndex);

		//sets the sprite image of the turret
		setRegion(AssetLoader.turret);
		
		//sets sprite dimensions/position/origin
		this.setBounds(joint.getAnchorA().x, joint.getAnchorA().y - 0.6f, SPRITE_WIDTH, SPRITE_HEIGHT);
		this.setOrigin(getWidth() / 2, 0.58f);

		//creates a muzzle flash for this turret that will be displayed whenever it shoots.
		this.muzzleFlash = new MuzzleFlash(AssetLoader.cannonFlare, 2, 1.5f);
	}

	//method that defines the physics body and places it in the physics world
	public void defineBody(Chassis chassis, short filterIndex) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(TURRET_WIDTH, TURRET_HEIGHT);

		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.density = 0.01f;
		fixDef.filter.groupIndex = filterIndex;

		b2body = world.createBody(bodyDef);
		b2body.createFixture(fixDef);
		b2body.setAngularDamping(90000);
		

		RevoluteJointDef jointDef = new RevoluteJointDef();
		jointDef.bodyA = chassis.b2body;
		jointDef.bodyB = b2body;

		joint = (RevoluteJoint) world.createJoint(jointDef);


	}

	//this method is called many times a second, updates the sprite position to that of the physics body it is attached to
	public void update() {
	
		this.setPosition(joint.getAnchorA().x - getWidth() / 2, joint.getAnchorA().y - 0.6f);
	}
	
	//rotates turret to given rotation
	public void rotateTurret(float rotation) {
		b2body.setTransform(b2body.getPosition(), (float) (rotation - Math.PI / 2) );
		this.setRotation(rotation * MathUtils.radiansToDegrees - 90);
	}

	public void shoot(PlayScreen screen) {

		//calculates the tip of the turret using some basic trigonometry
		firePoint = new Vector2(
				b2body.getPosition().x + 2 * fpRadius * MathUtils.cos((float) (b2body.getAngle() + Math.PI / 2)),
				b2body.getPosition().y + 2 * fpRadius * MathUtils.sin((float) (b2body.getAngle() + Math.PI / 2)));

		//spawns a new bullet and sets all of its attributes
		Bullet bullet = new Bullet(world);
		bullet.defineBody(0.4f, firePoint.x, firePoint.y, (float) (b2body.getTransform().getRotation() + Math.PI / 2));
		bullet.setRadius(0.4f);
		bullet.setColor(Color.BLACK);
		bullet.setDamage(100);
		bullet.setHealth(100);
		bullet.setSpeed(250);
		bullet.setLifetime(3);
		
		//adds the bullet to the game screen
		screen.bullets.add(bullet);

	}

	//draws the muzzle flash
	public void drawFlash(SpriteBatch batch) {

		//calculates the tip of the cannon
		firePoint = new Vector2(
				b2body.getPosition().x + fpRadius * MathUtils.cos((float) (b2body.getAngle() + Math.PI / 2)),
				b2body.getPosition().y + fpRadius * MathUtils.sin((float) (b2body.getAngle() + Math.PI / 2)));
	
		//setting dimensions, position and origin of the muzzle flare image
		muzzleFlash.setPosition(firePoint.x , firePoint.y - getWidth() / 2 );
		muzzleFlash.setRotation(MathUtils.radiansToDegrees * b2body.getTransform().getRotation() + 90);
		muzzleFlash.setOrigin(0, getWidth() / 2);
		
		//draws the sprite on screen
		muzzleFlash.draw(batch);

	}
}
