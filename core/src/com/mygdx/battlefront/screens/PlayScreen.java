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
import com.mygdx.battlefront.Tank;
import com.mygdx.battlefront.Turret;
import com.mygdx.battlefront.tools.AssetLoader;

public class PlayScreen implements Screen {
	
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private ShapeRenderer sr;
	
	private OrthographicCamera camera;
	public Battlefront game;
	
	public Tank tank;
	public Turret turret;
	
	public Vector3 mouse;
	
	public ArrayList<Bullet> bullets;
	
	public PlayScreen(Battlefront game) {
		world = new World(new Vector2(), true);
	
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth() / 25, Gdx.graphics.getHeight() / 25);
		camera.translate(-15, -15);
		
		debugRenderer = new Box2DDebugRenderer();
		sr = new ShapeRenderer();
		
		tank = new Tank(world, 0, 0);
		turret = new Turret(world, this);
		
		bullets = new ArrayList<Bullet>();
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
		if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)){
			turret.joint.enableMotor(true);
			turret.joint.setMotorSpeed(3);
			turret.joint.setMaxMotorTorque(500);
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
			turret.joint.enableMotor(true);
			turret.joint.setMotorSpeed(-3);
			turret.joint.setMaxMotorTorque(500);
		}
		else {
			turret.joint.setMotorSpeed(0);
			turret.joint.enableMotor(false);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			turret.shoot();
		}
		if (Gdx.input.isButtonPressed(Buttons.LEFT))
			System.out.println(mouse);
		
	}
	
	public void update() {
		mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(mouse);
		
		tank.update();
		turret.update();
	}

	@Override
	public void render(float delta) {
		update();
		handleInput(delta);
	
		Gdx.gl.glClearColor(190 / 255f, 190 /255f, 190 /255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		
		
	
		
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		tank.draw(game.batch);
		turret.draw(game.batch);
		//turret.drawFlash(game.batch);
		game.batch.end();
		
		sr.setProjectionMatrix(camera.combined);
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).drawBullet(sr);
		}
		
		//debugRenderer.render(world, camera.combined);
		
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
