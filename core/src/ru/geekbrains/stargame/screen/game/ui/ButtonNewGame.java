package ru.geekbrains.stargame.screen.game.ui;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.engine.ui.ActionListener;
import ru.geekbrains.engine.ui.ScaledTouchUpButton;

/**
 * Created by admin on 28.12.2017.
 */

public class ButtonNewGame extends ScaledTouchUpButton {//содание кнопки на экране GameOver

	private static final float HEIGHT = 0.05f;
	private static final float TOP = -0.12f;
	private static final float PRESS_SCALE = 0.9f;

	public ButtonNewGame(TextureAtlas atlas, ActionListener actionListener) {
		super(atlas.findRegion("button_new_game"), actionListener, PRESS_SCALE);
		setHeightProportion(HEIGHT);
		setTop(TOP);
	}
}
