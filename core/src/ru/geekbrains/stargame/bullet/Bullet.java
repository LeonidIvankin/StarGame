package ru.geekbrains.stargame.bullet;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.engine.math.Rect;
import ru.geekbrains.engine.sprite.Sprite;

/**
 * Пуля
 */

public class Bullet extends Sprite {
	private Rect worldBounds;
	private final Vector2 v = new Vector2();
	private int damage;
	private Object owner;//владелец пули

	public Bullet() {
		regions = new TextureRegion[1];//массив из одной пули
	}

	public void set(//метод для повторного изпользования пули разными кораблями
	        Object owner,//владелец пули
	        TextureRegion region,//текстуру для пули
	        Vector2 pos0,
	        Vector2 v0,
			float height,//размер пули
	        Rect worldBounds,
	        int damage//урон от пули
	){
		this.owner = owner;
		this.regions[0] = region;
		this.pos.set(pos0);
		this.v.set(v0);
		setHeightProportion(height);
		this.worldBounds = worldBounds;
		this.damage = damage;
	}

	//метод для движения пули
	@Override
	public void update(float deltaTime) {
		this.pos.mulAdd(v, deltaTime);
		if(isOutside(worldBounds)){//проверяем вылетела ли пуля за границы экрана
			destroy();
		}
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public Object getOwner() {
		return owner;
	}

	public void setOwner(Object owner) {
		this.owner = owner;
	}
}
