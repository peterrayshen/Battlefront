package com.mygdx.battlefront;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.battlefront.screens.PlayScreen;
import com.mygdx.battlefront.tools.AssetLoader;

public class Enemy {
	
	private World world;
	
	private Chassis chassis;
	private Turret turret;
	
	private PlayScreen screen;
	
	public boolean isShooting;
	
	public int health;
	
	public Enemy(World world, PlayScreen screen, float x, float y) {
		
		
		this.world = world;
		this.screen = screen;
		
		chassis = new Chassis(world, x, y);
		turret = new Turret(world, chassis);
		
		chassis.b2body.setTransform(chassis.b2body.getPosition(), (float) Math.PI / 2);
		turret.b2body.setTransform(turret.b2body.getPosition(), (float) Math.PI / 2);
		turret.setRotation(MathUtils.radiansToDegrees * turret.b2body.getTransform().getRotation());
		
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public void shoot() {
		AssetLoader.cannonFiring.play();
		turret.shoot(screen);
	}
	
	public void update() {
	
		turret.update();
		chassis.update();
		
		
	}
	
	public void draw(SpriteBatch batch) {
		
		
		chassis.draw(batch);
		turret.draw(batch);
	}
	
	public void drawFlash(SpriteBatch batch) {
		turret.drawFlash(batch);
	}
	
	public void goForward(float delta) {
		float x = (float) Math.cos(chassis.b2body.getTransform().getRotation() + Math.PI / 2);
		float y = (float) Math.sin(chassis.b2body.getTransform().getRotation() + Math.PI / 2);
		
		Vector2 dir = new Vector2(x, y);
		dir.scl(600);
		chassis.b2body.applyForceToCenter(dir, true);
	
	}
	
	public void goBackward (float delta) {
		float x = (float) Math.cos(chassis.b2body.getTransform().getRotation() + Math.PI / 2);
		float y = (float) Math.sin(chassis.b2body.getTransform().getRotation() + Math.PI / 2);
		
		Vector2 dir = new Vector2(x, y);
	
		dir.scl(600);
		dir.rotate(180);
		
		chassis.b2body.applyForceToCenter(dir, true);
	}
	
	public void rotClock(float delta) {
		chassis.b2body.setTransform(chassis.b2body.getWorldCenter(), (float)(chassis.b2body.getTransform().getRotation() - 150 * Math.PI / 180 * delta));
		
	}
	
	public void rotCounterClock(float delta) {
		chassis.b2body.setTransform(chassis.b2body.getWorldCenter(), (float)(chassis.b2body.getTransform().getRotation() + 150 * Math.PI / 180 * delta));
	}

}
