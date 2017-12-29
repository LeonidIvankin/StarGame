package ru.geekbrains.engine.ui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.engine.sprite.Sprite;

/**
 * Created by ILM on 13.12.2017.
 */

public class ScaledTouchUpButton extends Sprite{//класс для уменьшающейся кнопки

	private int pointer;//сколько пальцев нажало. Нажималась 1 раз
	private boolean pressed;//нажата- не нажата кнопа
	private float pressScale;//масштаб уменьшения
	private ActionListener actionListener;

	public ScaledTouchUpButton(TextureRegion region, ActionListener actionListener, float pressScale) {//процент уменьшения
		super(region);
		this.pressScale = pressScale;
		this.actionListener = actionListener;
	}

	@Override
	public boolean touchDown(Vector2 touch, int pointer) {
		if(pressed || !isMe(touch)){//если кнопка нажати или мы промахнулись
			return false;
		}
		this.pointer = pointer;//присваиваем номер пальца
		scale = pressScale;
		pressed = true;
		return true;
	}

	@Override
	public boolean touchUp(Vector2 touch, int pointer) {
		//какой палец отпустили
		if(this.pointer != pointer || !pressed){//если отпустили палец, который не на кнопке или кнопка не нажата
			return false;
		}
		pressed = false;//отпустили кнопку
		scale = 1f;
		//передумал нажимать кнопку при передвижении
		if(isMe(touch)){
			actionListener.actionPerformed(this);
			return true;
		}
		return false;
	}
}
