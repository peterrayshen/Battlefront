package com.mygdx.battlefront;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.battlefront.screens.PlayScreen;
import com.mygdx.battlefront.tools.AssetLoader;



public class Enemy {
	
	public World world;
	
	public Chassis chassis;
	public Turret turret;
	
	private PlayScreen screen;
	private float healthBarWidth;
	
	public boolean isShooting;
	
	public int health;
	public int totalHealth;
	
	public Enemy(World world, PlayScreen screen, float x, float y) {
		
		
		this.world = world;
		this.screen = screen;
		
		chassis = new Chassis(world, x, y, Battlefront.ENEMY_INDEX);
		turret = new Turret(world, chassis, Battlefront.ENEMY_INDEX);
		
		chassis.b2body.setTransform(chassis.b2body.getPosition(), (float) Math.PI / 2);
		chassis.b2body.getFixtureList().first().setUserData(this);
		chassis.b2body.getFixtureList().first().setDensity(1000f);
		
		turret.b2body.setTransform(turret.b2body.getPosition(), (float) Math.PI / 2);
		turret.b2body.getFixtureList().first().setUserData("enemyTurret");
		turret.setRotation(MathUtils.radiansToDegrees * turret.b2body.getTransform().getRotation());
		
	}
	
	public void setInitialHealth(int health) {
		this.health = health;
		this.totalHealth = health;
	}
	
	public void shoot() {
		AssetLoader.cannonFiring.play();
		turret.shoot(screen);
	}
	
	public void update() {
		
		healthBarWidth = (float) health / totalHealth * chassis.getWidth();
	
		turret.update();
		chassis.update();
		chassis.b2body.applyForceToCenter(new Vector2(-750, 0), true);
		
		
		
	}
	
	
	
	public void draw(SpriteBatch batch) {
		chassis.draw(batch);
		turret.draw(batch);
	}
	
	public void drawHealthBar(ShapeRenderer sr) {
		
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.WHITE);
		sr.rect(chassis.b2body.getPosition().x - chassis.getWidth() / 2 - 0.2f, chassis.b2body.getPosition().y + chassis.getHeight() / 2 + 0.3f , chassis.getWidth(),
				0.3f);
		sr.end();
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.RED);
		sr.rect(chassis.b2body.getPosition().x - chassis.getWidth() / 2 - 0.2f, chassis.b2body.getPosition().y + chassis.getHeight() / 2 + 0.3f , healthBarWidth,
				0.3f);
		sr.end();
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
