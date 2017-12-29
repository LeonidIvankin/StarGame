package ru.geekbrains.stargame.screen.menu.ui;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.engine.math.Rect;
import ru.geekbrains.engine.ui.ActionListener;
import ru.geekbrains.engine.ui.ScaledTouchUpButton;

/**
 * Created by ILM on 14.12.2017.
 */

public class ButtonExit extends ScaledTouchUpButton {

	public ButtonExit(TextureAtlas atlas, ActionListener actionListener, float pressScale) {
		super(atlas.findRegion("btExit"), actionListener, pressScale);//наименоваение из атласа
	}

	@Override
	public void resize(Rect worldBounds) {
		//когда происходит resize, передаются границы экрана - это нужно, чтобы правильно спозиционировать кнопку
		setBottom(worldBounds.getBottom());//берем worldBounds (границы мира).берем нижнюю границу
		setRight(worldBounds.getRight());//берем worldBounds (границы мира).берем правую границу
	}
}
