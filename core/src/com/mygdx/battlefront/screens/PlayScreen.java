package com.mygdx.battlefront.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.battlefront.Battlefront;
import com.mygdx.battlefront.Bullet;
import com.mygdx.battlefront.Enemy;
import com.mygdx.battlefront.Player;
import com.mygdx.battlefront.scenes.Hud;
import com.mygdx.battlefront.tools.AssetLoader;
import com.mygdx.battlefront.tools.RoundController;
import com.mygdx.battlefront.tools.WorldContactListener;

public class PlayScreen implements Screen {

	private World world;
	private Box2DDebugRenderer debugRenderer;
	private ShapeRenderer sr;

	public RoundController roundController;

	private OrthographicCamera camera;
	public Battlefront game;

	// head's up display
	private Hud hud;

	public Player player;
	public Vector3 mouse;
	public Enemy enemy;

	// arraylist that stores every bullet and every enemy in play
	public ArrayList<Bullet> bullets;
	public ArrayList<Enemy> enemies;

	// constructor
	public PlayScreen(Battlefront game) {
		// creates the physics world and sets up the contact listener
		world = new World(new Vector2(), true);
		world.setContactListener(new WorldContactListener(this));

		// gives a reference to the Battlefront class
		this.game = game;

		// creates a camera and our view into the game world
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth() / 25, Gdx.graphics.getHeight() / 25);
		camera.translate(-15, -15);

		// physics world debug renderer
		debugRenderer = new Box2DDebugRenderer();
		sr = new ShapeRenderer();

		// creates the player tank and spawns it in the world
		player = new Player(world, this, 0, 0);

		// instantiates the arraylist of enemies and bullets for future use
		bullets = new ArrayList<Bullet>();
		enemies = new ArrayList<Enemy>();

		// round controller to control the spawning of the enemies
		roundController = new RoundController(world, this);
		roundController.startRound();

		// creates the head's up display for the user
		hud = new Hud(game.batch, this);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	// handles the user input for the game
	public void handleInput(float delta) {

		// keyboard controls
		if (Gdx.input.isKeyPressed(Input.Keys.W))
			player.goForward(delta);
		if (Gdx.input.isKeyPressed(Input.Keys.S))
			player.goBackward(delta);
		if (Gdx.input.isKeyPressed(Input.Keys.D))
			player.rotClock(delta);
		if (Gdx.input.isKeyPressed(Input.Keys.A))
			player.rotCounterClock(delta);

		// mouse clicked -> shoot
		if (Gdx.input.justTouched()) {
			player.shoot();
			player.isShooting = true;
		} else {
			player.isShooting = false;
		}

	}

	// this method is called many times a second and updates the various objects
	// within our game
	public void update(float delta) {
		// gets position of the mouse and unprojects it so that it matches our
		// physics world coordinate system
		mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(mouse);

		// updates the HUD and the player, the camera and also the round controller
		hud.update();
		player.update();
		roundController.update(delta);
		camera.update();

		// updates each enemy
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).update();
		}

		// updates each bullet
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).update(delta);
			// checks if the bullet needs to be removed from play
			if (bullets.get(i).shouldRemove()) {
				bullets.get(i).world.destroyBody(bullets.get(i).b2body);
				bullets.remove(i);
			}
		}
		// checks if an enemy is below zero health, and therefore needs to be
		// removed from play
		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i).health <= 0) {
				enemies.get(i).world.destroyBody(enemies.get(i).chassis.b2body);
				enemies.get(i).world.destroyBody(enemies.get(i).turret.b2body);
				enemies.remove(i);
			}
		}

	}

	// checks if the player has lost
	public void checkIfLost() {

		// if an enemy tank crossed the whole screen, player has lost
		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i).chassis.b2body.getPosition().x < -18f)
				game.setScreen(game.loseScreen);
		}

		// if the player has crossed any of the boundaries of the screen, the
		// player has lost
		if (player.chassis.b2body.getPosition().x < -18f)
			game.setScreen(game.loseScreen);
		else if (player.chassis.b2body.getPosition().x > 38)
			game.setScreen(game.loseScreen);
		else if (player.chassis.b2body.getPosition().y > 15)
			game.setScreen(game.loseScreen);
		else if (player.chassis.b2body.getPosition().y < -17.5f)
			game.setScreen(game.loseScreen);

	}

	// this method is called many times a second
	@Override
	public void render(float delta) {
		// clears the screen and sets the background colour to grey
		Gdx.gl.glClearColor(190 / 255f, 190 / 255f, 190 / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// updates all modules and objects in the game world
		update(delta);
		// continuously checks for user input
		handleInput(delta);
		// continuously checks if user has met any conditions that make he/she
		// lose the game
		checkIfLost();

		//sets the shape renderer to the view of the game camera
		sr.setProjectionMatrix(camera.combined);
		//draws all the bullets
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).drawBullet(sr);
		}
		//draws all the enemy's health bars
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).drawHealthBar(sr);
		}

		//sets the sprite batch to the camera view
		game.batch.setProjectionMatrix(camera.combined);
		
		game.batch.begin();
		//draws the player
		player.draw(game.batch);
		//draws every enemy
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(game.batch);
		}
		//draws muzzle flash if player is shooting
		if (player.isShooting) {
			player.drawFlash(game.batch);
		}
		game.batch.end();

		//draws the head's up display
		hud.stage.draw();

		//updates the physics world
		world.step(1 / 120f, 6, 2);

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	//disposes of ressources
	@Override
	public void dispose() {
		world.dispose();
		debugRenderer.dispose();
		AssetLoader.dispose();

	}

}
