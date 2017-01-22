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

	private static final float TURRET_WIDTH = 0.4f;
	private static final float TURRET_HEIGHT = 1.4f;
	private static final float SPRITE_WIDTH = 1.5f;
	private static final float SPRITE_HEIGHT = 3.3f;

	public static float fpRadius = 0.6f;

	private PlayScreen playScreen;
	public RevoluteJoint joint;
	public Body turret;
	private World world;
	private Vector2 firePoint;

	public MuzzleFlash muzzleFlash;
	public boolean isShooting;

	public Turret(World world, PlayScreen playScreen) {
		this.playScreen = playScreen;
		this.world = world;

		defineBody();

		setRegion(AssetLoader.playerTurret);
		this.setBounds(joint.getAnchorA().x, joint.getAnchorA().y - 0.6f, SPRITE_WIDTH, SPRITE_HEIGHT);
		this.setOrigin(getWidth() / 2, 0.58f);

		this.muzzleFlash = new MuzzleFlash(AssetLoader.cannonFlare, 2, 1.5f);
	}

	public void defineBody() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(TURRET_WIDTH, TURRET_HEIGHT);

		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.density = 0.01f;
		fixDef.filter.groupIndex = Battlefront.PLAYER_INDEX;

		turret = world.createBody(bodyDef);
		turret.createFixture(fixDef);
		turret.setAngularDamping(90000);

		RevoluteJointDef jointDef = new RevoluteJointDef();
		jointDef.bodyA = playScreen.tank.chassis;
		jointDef.bodyB = turret;
		jointDef.localAnchorB.y = -1.4f;

		joint = (RevoluteJoint) world.createJoint(jointDef);

		// turret.setTransform(turret.getPosition(), (float) Math.PI);

	}

	public void update() {

		this.setPosition(joint.getAnchorA().x - getWidth() / 2, joint.getAnchorA().y - 0.6f);
		this.setRotation(turret.getTransform().getRotation() * MathUtils.radiansToDegrees);
	
	
	}

	public void shoot() {
		
		
		
		AssetLoader.cannonFiring.play();

		firePoint = new Vector2(
				turret.getPosition().x + 2 * fpRadius * MathUtils.cos((float) (turret.getAngle() + Math.PI / 2)),
				turret.getPosition().y + 2 * fpRadius * MathUtils.sin((float) (turret.getAngle() + Math.PI / 2)));

		Bullet bullet = new Bullet(world, playScreen);
		bullet.defineBody(0.4f, firePoint.x, firePoint.y, (float) (turret.getTransform().getRotation() + Math.PI / 2));
		bullet.setRadius(0.4f);
		bullet.setColor(Color.LIGHT_GRAY);
		bullet.setDamage(100);
		bullet.setHealth(100);
		bullet.setSpeed(300);
		bullet.setLifetime(10);
		playScreen.bullets.add(bullet);

		System.out.println(bullet.b2body.getTransform().getPosition());
	}

	public void drawFlash(SpriteBatch batch) {

		firePoint = new Vector2(
				turret.getPosition().x + fpRadius * MathUtils.cos((float) (turret.getAngle() + Math.PI / 2)),
				turret.getPosition().y + fpRadius * MathUtils.sin((float) (turret.getAngle() + Math.PI / 2)));
	
		
		muzzleFlash.setPosition(firePoint.x , firePoint.y - getWidth() / 2 );
		muzzleFlash.setRotation(MathUtils.radiansToDegrees * turret.getTransform().getRotation() + 90);
		muzzleFlash.setOrigin(0, getWidth() / 2);
		muzzleFlash.draw(batch);
		
	

	}
}
