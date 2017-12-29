package ru.geekbrains.stargame;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.engine.math.Rect;
import ru.geekbrains.engine.sprite.Sprite;

/**
 * Created by ILM on 11.12.2017.
 */

public class Background extends Sprite{
	public Background(TextureRegion region) {
		super(region);
	}

	@Override
	public void resize(Rect worldBounds) {
		setHeightProportion(worldBounds.getHeight());//высота фона == высоте экрана
		//выравнить, выставить новую позицию
		pos.set(worldBounds.pos);//одному вектору уст другой вектор
	}
}
