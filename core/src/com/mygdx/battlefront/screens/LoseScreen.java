package com.mygdx.battlefront.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LoseScreen implements Screen{
	
	//this screen is displayed whenever the player loses
	
	public Stage stage;
	private Viewport viewport;
	
	private Label messageLabel;
	
	private FreeTypeFontGenerator fontGenerator;
	
	public LoseScreen(SpriteBatch sb) {
		
		
		//imports a font and brings it into the game
		fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font.TTF"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 120;
		BitmapFont font = fontGenerator.generateFont(parameter);
		
		//sets the viewport
		viewport = new FitViewport(1280, 720, new OrthographicCamera());
		stage = new Stage(viewport, sb);
		
		//creates the text that says YOU LOSE
		messageLabel = new Label("YOU LOSE",
				new Label.LabelStyle(font, Color.WHITE));
		messageLabel.setBounds(640, 380, 10, 10);
		messageLabel.setAlignment(Align.center);
		
		//adds the text to the screen
		stage.addActor(messageLabel);
		
	}
	
	//disposes of ressources
	public void dispose() {
		stage.dispose();
		fontGenerator.dispose();

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
	}

	//method that's called many times every second
	@Override
	public void render(float delta) {
		//clears the screen and sets the background colour to grey
		Gdx.gl.glClearColor(190 / 255f, 190 / 255f, 190 / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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

}
