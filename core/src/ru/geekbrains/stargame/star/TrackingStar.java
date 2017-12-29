package ru.geekbrains.stargame.star;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;


//звезда, которая следит за движением корабля
public class TrackingStar extends Star {

	protected final Vector2 trackingV;
	private final Vector2 sumV = new Vector2();//вектор хранит сумму скоростей

	public TrackingStar(TextureAtlas atlas, float vx, float vy, float width, Vector2 trackingV) {
		super(atlas, vx, vy, width);
		this.trackingV = trackingV;//скорость корабля
	}

	@Override
	public void update(float deltaTime) {
		sumV.setZero().mulAdd(trackingV, 0.2f).rotate(180).add(v);//обнуляем; 0,2 от скорости корабля
		pos.mulAdd(sumV, deltaTime);//изменяем позицию звезды
		checkAndHandleBounds();//не вылетит ли звезда за пределы экрана
	}
}
