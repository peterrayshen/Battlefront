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
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.mygdx.battlefront.screens.PlayScreen;
import com.mygdx.battlefront.tools.AssetLoader;

public class Turret extends Sprite {

	private PlayScreen playScreen;
	public RevoluteJoint joint;
	public Body turret;
	private World world;

	public Turret(World world, PlayScreen playScreen) {
		this.playScreen = playScreen;
		this.world = world;

		defineBody();

		setRegion(AssetLoader.playerTurret);
		this.setBounds(joint.getAnchorA().x, joint.getAnchorA().y - getHeight() / 2, 5.5f, 2.5f);
		this.setOrigin(4.4f, getHeight() / 2);
	}

	public void defineBody() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(0.4f, 1.4f);

		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = shape;
		fixDef.density = 0.01f;

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
		
		this.setPosition(joint.getAnchorA().x - 4.4f, joint.getAnchorA().y - getHeight() / 2);
		this.setRotation(turret.getTransform().getRotation() * MathUtils.radiansToDegrees - 90);

		
	}
}
