package ru.geekbrains.stargame.ship;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.engine.math.Rect;
import ru.geekbrains.engine.sprite.Sprite;
import ru.geekbrains.stargame.bullet.Bullet;
import ru.geekbrains.stargame.explosion.Explosion;
import ru.geekbrains.stargame.pool.BulletPool;
import ru.geekbrains.stargame.pool.ExplosionPool;

public class Ship extends Sprite{

	private static final float DAMAGE_ANIMATE_INTERVAL = 0.1f;
	private float damageAnimateTimer = DAMAGE_ANIMATE_INTERVAL;

	protected final Vector2 v = new Vector2();
	protected Rect worldBounds;

	protected ExplosionPool explosionPool;
	protected BulletPool bulletPool;
	protected TextureRegion bulletRegion;


	//корабль будет доставать пули и эти параметры ей устанавливать
	protected final Vector2 bulletV = new Vector2();
	protected float bulletHeight;
	protected int bulletDamage;

	protected float reloadInterval;//вермя перезарядки, интервал пуль
	protected float reloadTimer;

	protected int hp;//здоровье корабля

	protected Sound shootSound;

	public Ship(TextureRegion region, int rows, int cols, int frames, BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds){
		super(region, rows, cols, frames);
		this.bulletPool = bulletPool;
		this.explosionPool = explosionPool;
		this.worldBounds = worldBounds;
	}

	public Ship(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds){
		this.bulletPool = bulletPool;
		this.explosionPool = explosionPool;
		this.worldBounds = worldBounds;
	}

	@Override
	public void update(float deltaTime) {//для анимации
		super.update(deltaTime);
		damageAnimateTimer += deltaTime;
		if(damageAnimateTimer >= DAMAGE_ANIMATE_INTERVAL){
			frame = 0;//меняем фрейм на обычный

		}
	}

	@Override
	public void resize(Rect worldBounds) {
		this.worldBounds = worldBounds;
	}

	protected void shoot(){//стрелять
		Bullet bullet = bulletPool.obtain();//или создаст, или возьмет из пула
		bullet.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, bulletDamage);
		if(shootSound.play() == -1) throw new RuntimeException("shootSound.play() == -1");
	}

	protected void shootLeft(){//стрелять
		Bullet bullet = bulletPool.obtain();//или создаст, или возьмет из пула
		TextureRegion bulletRegion = this.bulletRegion;
		bullet.set(this, bulletRegion, new Vector2(getLeft(), pos.y), bulletV, bulletHeight, worldBounds, bulletDamage);
		if(shootSound.play() == -1) throw new RuntimeException("shootSound.play() == -1");
	}


	protected void shootRight(){//стрелять
		Bullet bullet = bulletPool.obtain();//или создаст, или возьмет из пула
		bullet.set(this, bulletRegion, new Vector2(getRight(), pos.y), bulletV, bulletHeight, worldBounds, bulletDamage);
		if(shootSound.play() == -1) throw new RuntimeException("shootSound.play() == -1");
	}

	public void boom(){//когда вражеский корабль выходит за границы экрана, взрывается и наносит урон MainShip
		hp = 0;
		Explosion explosion = explosionPool.obtain();
		explosion.set(getHeight(), pos);//позиция взрыва равна позиции корабля
	}

	public void damage(int damage){//для урона
		frame = 1;//меняем фрейм
		damageAnimateTimer = 0f;
		hp -= damage;
		if(hp < 0){
			hp = 0;
		}
		if(hp == 0){
			boom();
			destroy();
		}
	}

	public int getBulletDamage() {
		return bulletDamage;
	}

	public int getHp() {
		return hp;
	}
}
