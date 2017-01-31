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
	
	//class variables
	private PlayScreen screen;
	public Stage stage;
	private Viewport viewport;
	
	Label roundLabel;
	FreeTypeFontGenerator fontGenerator;
	
	//constructor
	public Hud(SpriteBatch sb, PlayScreen screen) {
		
		this.screen = screen;
		
		//imports a font from the assets
		fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("font.TTF"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 40;
		BitmapFont font = fontGenerator.generateFont(parameter);
		
		//sets the viewport of the screen
		viewport = new FitViewport(1280, 720, new OrthographicCamera());
		stage = new Stage(viewport, sb);
		
		//adds a round label to the top of the screen
		roundLabel = new Label("round " + Integer.toString(screen.roundController.round),
				new Label.LabelStyle(font, Color.WHITE));
		roundLabel.setBounds(640, 680, 10, 10);
		roundLabel.setAlignment(Align.center);
		
		//adds the label to the stage
		stage.addActor(roundLabel);

	}
	
	//updates the round label given what round it is
	public void update() {
		roundLabel.setText("Round " + Integer.toString(screen.roundController.round));
	}
	
	//disposes of resources
	public void dispose() {
		stage.dispose();
		fontGenerator.dispose();
	}

}
