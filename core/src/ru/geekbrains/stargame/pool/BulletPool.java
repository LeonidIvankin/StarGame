package ru.geekbrains.stargame.pool;

import ru.geekbrains.engine.pool.SpritesPool;
import ru.geekbrains.stargame.bullet.Bullet;

public class BulletPool extends SpritesPool<Bullet>{
	@Override
	protected Bullet newObject() {
		return new Bullet();
	}

	@Override
	protected void debugLog() {
		System.out.println("Bullet pool change active/free: " + activeObjects.size() + " / " + freeObjects.size());
	}
}
