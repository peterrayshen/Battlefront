package com.mygdx.battlefront.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
import com.mygdx.battlefront.screens.PlayScreen;

public class Hud {
	
	private Integer round;
	private PlayScreen screen;
	public Stage stage;
	private Viewport viewport;
	
	Label roundLabel;
	
	private FreeTypeFontGenerator fontGenerator;
	
	public Hud(SpriteBatch sb, PlayScreen screen) {
		
		this.screen = screen;
		
		fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font.TTF"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 40;
		BitmapFont font = fontGenerator.generateFont(parameter);
		
		viewport = new FitViewport(1280, 720, new OrthographicCamera());
		stage = new Stage(viewport, sb);
		
		roundLabel = new Label("round " + Integer.toString(screen.roundController.round),
				new Label.LabelStyle(font, Color.WHITE));
		roundLabel.setBounds(640, 680, 10, 10);
		roundLabel.setAlignment(Align.center);
		
		stage.addActor(roundLabel);
		
		
		
	}
	
	public void update() {
		roundLabel.setText("Round " + Integer.toString(screen.roundController.round));
	}
	
	public void dispose() {
		
		stage.dispose();
		fontGenerator.dispose();

	}

}
