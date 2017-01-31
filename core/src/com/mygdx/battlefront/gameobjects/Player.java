package com.mygdx.battlefront.gameobjects;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.battlefront.game.Battlefront;
import com.mygdx.battlefront.screens.PlayScreen;
import com.mygdx.battlefront.tools.AssetLoader;

public class Player {
	
	//the player tank has a chassis and a turret
	public Chassis chassis;
	private Turret turret;
	
	private PlayScreen screen;
	public boolean isShooting;
	
	public Player(World world, PlayScreen screen, float x, float y) {
		this.screen = screen;
		
		//creates the chassis of the tank
		chassis = new Chassis(world, x, y, Battlefront.PLAYER_INDEX);
		chassis.b2body.getFixtureList().first().setUserData(this);
		
		//creates the turret of the tank
		turret = new Turret(world, chassis, Battlefront.PLAYER_INDEX);
	}
	
	//method is called every time the player shoots
	public void shoot() {
		//shooting sound effect
		AssetLoader.cannonFiring.play();
		turret.shoot(screen);
	}
	
	//this method is called many times in a second, updates the turret and chassis modules
	public void update() {
		//gets angle from center of the tank to the mouse
		float rotation = MathUtils.atan2(screen.mouse.y - turret.joint.getAnchorA().y,screen.mouse.x - turret.joint.getAnchorA().x);
		//rotates turret to the mouse
		turret.rotateTurret(rotation);
		
		//updates the modules of the tank
		turret.update();
		chassis.update();
	}
	
	//draws the chassis and the turret on screen
	public void draw(SpriteBatch batch) {
		chassis.draw(batch);
		turret.draw(batch);
	}
	
	//draws the muzzle flash
	public void drawFlash(SpriteBatch batch) {
		turret.drawFlash(batch);
	}
	
	//applies a force to wherever the tank is pointing, making it go forwards, uses some basic trigonometry to get direction vector
	public void goForward(float delta) {
		float x = (float) Math.cos(chassis.b2body.getTransform().getRotation() + Math.PI / 2);
		float y = (float) Math.sin(chassis.b2body.getTransform().getRotation() + Math.PI / 2);
		
		Vector2 dir = new Vector2(x, y);
		dir.scl(600);
		chassis.b2body.applyForceToCenter(dir, true);
	
	}
	
	//applies a force opposite to wherever the tank is pointing, making it go backwards, uses some basic trigonometry to get direction vector
	public void goBackward (float delta) {
		float x = (float) Math.cos(chassis.b2body.getTransform().getRotation() + Math.PI / 2);
		float y = (float) Math.sin(chassis.b2body.getTransform().getRotation() + Math.PI / 2);
		
		Vector2 dir = new Vector2(x, y);
	
		dir.scl(600);
		dir.rotate(180);
		
		chassis.b2body.applyForceToCenter(dir, true);
	}
	
	//rotates the tank clockwise
	public void rotClock(float delta) {
		chassis.b2body.setTransform(chassis.b2body.getWorldCenter(), (float)(chassis.b2body.getTransform().getRotation() - 150 * Math.PI / 180 * delta));
		
	}
	
	//rotates the tank counter clock wise
	public void rotCounterClock(float delta) {
		chassis.b2body.setTransform(chassis.b2body.getWorldCenter(), (float)(chassis.b2body.getTransform().getRotation() + 150 * Math.PI / 180 * delta));
	}

}
