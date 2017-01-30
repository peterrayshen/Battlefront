package com.mygdx.battlefront.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.mygdx.battlefront.Battlefront;
import com.mygdx.battlefront.Bullet;
import com.mygdx.battlefront.Enemy;
import com.mygdx.battlefront.Player;
import com.mygdx.battlefront.Chassis;
import com.mygdx.battlefront.Turret;
import com.mygdx.battlefront.tools.AssetLoader;
import com.mygdx.battlefront.tools.RoundController;
import com.mygdx.battlefront.tools.WorldContactListener;

public class PlayScreen implements Screen {

	private World world;
	private Box2DDebugRenderer debugRenderer;
	private ShapeRenderer sr;
	private RoundController roundController;
	

	private OrthographicCamera camera;
	public Battlefront game;

	public Player player;

	public Vector3 mouse;
	
	public Enemy enemy;

	public ArrayList<Bullet> bullets;
	public ArrayList<Enemy> enemies;

	public PlayScreen(Battlefront game) {
		world = new World(new Vector2(), true);
		world.setContactListener(new WorldContactListener(this));

		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth() / 25, Gdx.graphics.getHeight() / 25);
		camera.translate(-15, -15);

		debugRenderer = new Box2DDebugRenderer();
		sr = new ShapeRenderer();

		player = new Player(world, this, 0, 0);

		bullets = new ArrayList<Bullet>();
		enemies = new ArrayList<Enemy>();
		
		roundController = new RoundController(world, this);
		roundController.startRound();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	public void handleInput(float delta) {
		if (Gdx.input.isKeyPressed(Input.Keys.W))
			player.goForward(delta);
		if (Gdx.input.isKeyPressed(Input.Keys.S))
			player.goBackward(delta);
		if (Gdx.input.isKeyPressed(Input.Keys.D))
			player.rotClock(delta);
		if (Gdx.input.isKeyPressed(Input.Keys.A))
			player.rotCounterClock(delta);

		if (Gdx.input.justTouched()) {
			System.out.println(mouse);
			player.shoot();
			player.isShooting = true;
		} else {
			player.isShooting = false;
		}

	}

	public void update(float delta) {
		mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(mouse);
		player.update();
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).update();
		}
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).update(delta);
			
			if (bullets.get(i).shouldRemove()) {
				bullets.get(i).world.destroyBody(bullets.get(i).b2body);
				bullets.remove(i);		
			}
		}
		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i).health < 0) {
				enemies.get(i).world.destroyBody(enemies.get(i).chassis.b2body);
				enemies.get(i).world.destroyBody(enemies.get(i).turret.b2body);
				enemies.remove(i);
			}
		}
		
		roundController.update(delta);
		
	}

	@Override
	public void render(float delta) {
		update(delta);
		handleInput(delta);

		Gdx.gl.glClearColor(190 / 255f, 190 / 255f, 190 / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();

		sr.setProjectionMatrix(camera.combined);
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).drawBullet(sr);
		}
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).drawHealthBar(sr);
		}

		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		player.draw(game.batch);
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(game.batch);
		}
		if (player.isShooting) {
			player.drawFlash(game.batch);
		}
		game.batch.end();

		debugRenderer.render(world, camera.combined);

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

	@Override
	public void dispose() {
		world.dispose();
		debugRenderer.dispose();
		AssetLoader.dispose();

	}

}
