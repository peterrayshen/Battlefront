package com.mygdx.battlefront.gameobjects;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.battlefront.game.Battlefront;
import com.mygdx.battlefront.screens.PlayScreen;
import com.mygdx.battlefront.tools.AssetLoader;


public class Enemy {
	
	public World world;
	private PlayScreen screen;
	
	//each enemy has a chassis and a turret body since they're tanks
	public Chassis chassis;
	public Turret turret;
	
	//width of the health bar that is above them
	private float healthBarWidth;
	
	//other attributes for the tank
	public boolean isShooting;
	
	public int health;
	public int totalHealth;
	
	public float speed;
	
	//constructor
	public Enemy(World world, PlayScreen screen, float x, float y) {
		
		
		this.world = world;
		this.screen = screen;
		
		//creates the chassis physics body
		chassis = new Chassis(world, x, y, Battlefront.ENEMY_INDEX);
		//creates the turret physics body
		turret = new Turret(world, chassis, Battlefront.ENEMY_INDEX);
		
		//rotates the chassis to point leftwards, since the enemies move from the right side of the screen to the left
		chassis.b2body.setTransform(chassis.b2body.getPosition(), (float) Math.PI / 2);
		chassis.b2body.getFixtureList().first().setUserData(this);
	
		//rotates the turret leftwards for the same given reason
		turret.b2body.setTransform(turret.b2body.getPosition(), (float) Math.PI / 2);
		turret.b2body.getFixtureList().first().setUserData("enemyTurret");
		turret.setRotation(MathUtils.radiansToDegrees * turret.b2body.getTransform().getRotation());
		
	}
	
	//setter method, sets initial health of the enemy
	public void setInitialHealth(int health) {
		this.health = health;
		this.totalHealth = health;
	}
	
	//method that is called whenever the tank shoots
	public void shoot() {
		//plays sound effect
		AssetLoader.cannonFiring.play();
		//calls the shoot method in the turret class
		turret.shoot(screen);
	}
	
	//this is method is called many times a second
	public void update() {
		//updates the health bar width according to their current health percentage
		healthBarWidth = (float) health / totalHealth * chassis.getWidth();
	
		//updates both the turret and the chassis
		turret.update();
		chassis.update();
		
		//creates a leftwards force, since enemies move from right to left of the screen
		chassis.b2body.applyForceToCenter(new Vector2(-speed, 0), true);
	}
	
	
	//draws the enemy tank
	public void draw(SpriteBatch batch) {
		//draws both the chassis and the turret separately
		chassis.draw(batch);
		turret.draw(batch);
	}
	
	//draws the health bar using two rectangles
	public void drawHealthBar(ShapeRenderer sr) {
		//this is the background rectangle of the health bar, is always the same width
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.WHITE);
		sr.rect(chassis.b2body.getPosition().x - chassis.getWidth() / 2 - 0.2f, chassis.b2body.getPosition().y + chassis.getHeight() / 2 + 0.3f , chassis.getWidth(),
				0.3f);
		sr.end();
		
		//this is the percentage rectangle of the health bar, which changes according to the percentage of health left in the enemy
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.RED);
		sr.rect(chassis.b2body.getPosition().x - chassis.getWidth() / 2 - 0.2f, chassis.b2body.getPosition().y + chassis.getHeight() / 2 + 0.3f , healthBarWidth,
				0.3f);
		sr.end();
	}

}
