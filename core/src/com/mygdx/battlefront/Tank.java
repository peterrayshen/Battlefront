package com.mygdx.battlefront;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
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

public class Tank extends InputAdapter {

	public Body chassis, cannon;
	private RevoluteJoint joint;
	private float acc = 5000, leftAcc, rightAcc;

	public Tank(World world, float x, float y, float width, float height) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x, y);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2, height / 2);

		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.restitution = .1f;
		fixDef.friction = .5f;

		chassis = world.createBody(bodyDef);
		chassis.createFixture(fixDef);
		chassis.setLinearDamping(15);

		shape.setAsBox(width / 2 / 5, height / 2 / 3);

		cannon = world.createBody(bodyDef);
		cannon.createFixture(fixDef);

		RevoluteJointDef jointDef = new RevoluteJointDef();
		jointDef.bodyA = chassis;
		jointDef.bodyB = cannon;
		jointDef.localAnchorB.y = -height / 3;

		joint = (RevoluteJoint) world.createJoint(jointDef);

	}
	
	Vector2 tmp = new Vector2(), tmp2 = new Vector2();
	
	public void update() {
		
		cannon.setTransform(cannon.getWorldCenter(), 3);
	
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
