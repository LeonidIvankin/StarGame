package ru.geekbrains.stargame.ship;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.engine.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;
import ru.geekbrains.stargame.pool.ExplosionPool;

public class Enemy extends Ship{

	private enum State{DESCENT, FIGHT}//2 РЕЖИМА у корабли (боя и появление)

	private State state;

	private Vector2 descentV = new Vector2(0, -0.15f);

	private MainShip mainShip;

	private Vector2 v0 = new Vector2();

	public Enemy(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, MainShip mainShip) {
		super(bulletPool, explosionPool, worldBounds);
		this.mainShip = mainShip;
		this.v.set(v0);
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		pos.mulAdd(v, deltaTime);

		switch (state) {
			case DESCENT:
				if(getTop() <= worldBounds.getTop()){//если корабль появился на экране, выставляем FIGHT
					v.set(v0);
					state = State.FIGHT;
				}
				break;
			case FIGHT:
				reloadTimer += deltaTime;
				if(reloadTimer >= reloadInterval){//стрельба вражеских кораблей
					reloadTimer = 0f;
					shoot();
				}
				if(getBottom() < worldBounds.getBottom()){
					//как только вражеский корабль достигает гарниц экрана - урон
					mainShip.damage(bulletDamage);
					boom();
					destroy();
				}
				break;
		}
	}



	public void set(
			TextureRegion[] regions,//текстура корабля
	        Vector2 v0,//начальная скорость
	        TextureRegion bulletRegion,//текстура пули
	        float bulletHeight,//высота пули
	        float bulletVY,//скорость пули по Y
	        int bulletDamage,//урон
	        float reloadInterval,//скорость перезарядки
	        Sound shootSound,//звук выстрела
	        float height,//размер корабля
	        int hp//жизни
	){
		this.regions = regions;
		this.v0.set(v0);
		this.bulletRegion = bulletRegion;
		this.bulletHeight = bulletHeight;
		this.bulletV.set(0f, bulletVY);
		this.bulletDamage = bulletDamage;
		this.reloadInterval = reloadInterval;
		this.shootSound = shootSound;
		this.hp = hp;
		setHeightProportion(height);
		reloadTimer = reloadInterval;
		v.set(descentV);//когда создали корабль, он не в режиме боя, он выплывает
		state = State.DESCENT;
	}

	public boolean isBulletCollision(Rect bullet){//проверка куда именно долетела пуля
		return !(
					bullet.getRight() < getLeft()
					|| bullet.getLeft() > getRight()
					|| bullet.getBottom() > getTop()
					|| bullet.getTop() < pos.y//пуля находится в половине корабля задней
				);
	}
}
