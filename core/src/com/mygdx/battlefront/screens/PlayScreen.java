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

public class PlayScreen implements Screen {

	private World world;
	private Box2DDebugRenderer debugRenderer;
	private ShapeRenderer sr;

	private OrthographicCamera camera;
	public Battlefront game;

	public Player player;

	public Vector3 mouse;
	
	public Enemy enemy;

	public ArrayList<Bullet> bullets;

	public PlayScreen(Battlefront game) {
		world = new World(new Vector2(), true);

		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth() / 25, Gdx.graphics.getHeight() / 25);
		camera.translate(-15, -15);

		debugRenderer = new Box2DDebugRenderer();
		sr = new ShapeRenderer();

		player = new Player(world, this, 0, 0);

		bullets = new ArrayList<Bullet>();
		
		enemy = new Enemy(world, 3, 3);
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

	public void update() {
		mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(mouse);

		player.update();
		enemy.update();
		
	}

	@Override
	public void render(float delta) {
		update();
		handleInput(delta);

		Gdx.gl.glClearColor(190 / 255f, 190 / 255f, 190 / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();

		sr.setProjectionMatrix(camera.combined);
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).drawBullet(sr);
		}

		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		player.draw(game.batch);
		enemy.draw(game.batch);
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
