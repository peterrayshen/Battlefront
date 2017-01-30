package com.mygdx.battlefront.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.battlefront.screens.PlayScreen;

public class LoseScreen implements Screen{
	

	public Stage stage;
	private Viewport viewport;
	
	
	private Label exitLabel;
	
	
	private FreeTypeFontGenerator fontGenerator;
	
	public LoseScreen(SpriteBatch sb) {
		
		
		
		fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font.TTF"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 120;
		BitmapFont font = fontGenerator.generateFont(parameter);
		
		viewport = new FitViewport(1280, 720, new OrthographicCamera());
		stage = new Stage(viewport, sb);
		
		exitLabel = new Label("YOU LOSE",
				new Label.LabelStyle(font, Color.WHITE));
		exitLabel.setBounds(640, 380, 10, 10);
		exitLabel.setAlignment(Align.center);
		
		
		
		
		stage.addActor(exitLabel);
		
		
		
		
	}
	
	
	public void dispose() {
		
		stage.dispose();
		fontGenerator.dispose();

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
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
