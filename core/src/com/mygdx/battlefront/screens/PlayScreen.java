package com.mygdx.battlefront.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.battlefront.Battlefront;
import com.mygdx.battlefront.Tank;

public class PlayScreen implements Screen {
	
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	
	private Tank tank;
	
	public PlayScreen(Battlefront game) {
		world = new World(new Vector2(), true);
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth() / 25, Gdx.graphics.getHeight() / 25);
		camera.translate(-15, -15);
		
		debugRenderer = new Box2DDebugRenderer();
		
		tank = new Tank(world, 0, 0, 3, 5);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	
	public void handleInput(float delta) {
		if (Gdx.input.isKeyPressed(Input.Keys.W))
			tank.goForward(delta);
		if (Gdx.input.isKeyPressed(Input.Keys.S))
			tank.goBackward(delta);
		if (Gdx.input.isKeyPressed(Input.Keys.D))
			tank.rotClock(delta);
		if (Gdx.input.isKeyPressed(Input.Keys.A))
			tank.rotCounterClock(delta);
		
	}

	@Override
	public void render(float delta) {
		handleInput(delta);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		
		world.step(1 / 120f, 6, 2);
		debugRenderer.render(world, camera.combined);
		
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
		batch.dispose();
		debugRenderer.dispose();
		
	}

}
