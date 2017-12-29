package ru.geekbrains.stargame.star;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.engine.math.Rect;
import ru.geekbrains.engine.math.Rnd;
import ru.geekbrains.engine.sprite.Sprite;

/**
 * Created by ILM on 14.12.2017.
 */

public class Star extends Sprite{

	protected final Vector2 v = new Vector2();//скорость движения звезды по экрану
	private Rect worldBounds;//границы мира

	public Star(TextureAtlas atlas, float vx, float vy, float width) {
		super(atlas.findRegion("star"));
		v.set(vx, vy);
		setWidthProportion(width);
	}

	@Override
	public void update(float deltaTime) {
		pos.mulAdd(v, deltaTime);//сложение векторов, умножение на скаляр
		checkAndHandleBounds();

	}

	@Override
	public void resize(Rect worldBounds) {
		this.worldBounds = worldBounds;//обновить границы мира
		float posX = Rnd.nextFloat(this.worldBounds.getLeft(), this.worldBounds.getRight());// при изменении границ экрана звезда генерируется случайно
		float posY = Rnd.nextFloat(this.worldBounds.getBottom(), this.worldBounds.getTop());
		pos.set(posX, posY);//позиция экрана

	}

	protected void checkAndHandleBounds(){//находится ли звезда в границах экрана
		if(getRight() < worldBounds.getLeft()){
			setLeft((worldBounds.getRight()));
		}
		if(getLeft() > worldBounds.getRight()){
			setRight(worldBounds.getLeft());
		}
		if(getTop() < worldBounds.getBottom()){
			setBottom(worldBounds.getTop());
		}
		if(getBottom() > worldBounds.getTop()){
			setTop(worldBounds.getBottom());
		}
	}
}
