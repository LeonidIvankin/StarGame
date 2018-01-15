package ru.geekbrains.stargame.ship;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.engine.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;
import ru.geekbrains.stargame.pool.ExplosionPool;

/**
 * Created by ILM on 16.12.2017.
 */

public class MainShip extends Ship{

	//размер корабля
	private static final float SHIP_HEIGHT = 0.15f;
	private static final float BOTTOM_MARGIN = 0.05f;//отступ от низа экрна

	private static final int INVALID_POINTER = -1;//палец не соответствует ни одному числу

	private final Vector2 v0 = new Vector2(0.5f, 0);//вектор скорости корабля

	private boolean pressedLeft;//исправление бага с быстрой сменой кнопок
	private boolean pressedRight;

	private int leftPointer = INVALID_POINTER;
	private int rightPointer = INVALID_POINTER;


	public MainShip(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, Sound shootSound) {
		super(atlas.findRegion("main_ship"), 1, 2, 2, bulletPool, explosionPool, worldBounds);//вызываем конструктор для нарезки текстур
		setHeightProportion(SHIP_HEIGHT);
		this.bulletRegion = atlas.findRegion("bulletMainShip");
		this.shootSound = shootSound;
	}

	public void setToNewGame(){//установка новой игры
		pos.x = worldBounds.pos.x;
        this.bulletHeight = 0.01f;
        this.bulletV.set(0, 0.5f);
        this.bulletDamage = 1;
        this.reloadInterval = 0.2f;
		hp = 20;
		v.set(Vector2.Zero);
		flushDestroy();
	}

	@Override
	public void resize(Rect worldBounds) {
		super.resize(worldBounds);
		setBottom(worldBounds.getBottom() + BOTTOM_MARGIN);//нижняя граница корабля чуть выше нижней границы экрана
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);

		pos.mulAdd(v, deltaTime);//сложение векторов скорости и умножение на скаляр
		reloadTimer += deltaTime;
		if(reloadTimer >= reloadInterval){
			reloadTimer = 0f;
			shoot();

		}
		//ограничение движения корабля
		if(getRight() > worldBounds.getRight()){
			setRight(worldBounds.getRight());
			stop();
		}
		if(getLeft() < worldBounds.getLeft()){
			setLeft(worldBounds.getLeft());
			stop();
		}
	}

	//движение корабля влево-вправо по щелчку мыши и сенсору
	//если нажали оба пальца, то корабль движется к тому пальцу, который нажали последним
	//если палец убрали, то движется к тому пальцу, который остается
	//если нажимаем 3-4 пальца, то работа происходит с двумя
	@Override
	public boolean touchDown(Vector2 touch, int pointer) {
		if(touch.x < worldBounds.pos.x){
			if(leftPointer != INVALID_POINTER) return false;
			leftPointer = pointer;//зафиксировали в левой части экрана и зафикс палец
			//палец нажат в левой части экрана; если опять нажмем в левой части экрана ничего не должно происходить
			moveLeft();
		}else{
			if(rightPointer != INVALID_POINTER) return false;
			rightPointer = pointer;
			moveRight();
		}
		return false;
	}

	@Override
	public boolean touchUp(Vector2 touch, int pointer) {
		if(pointer == leftPointer){//отпустили левый палец
			leftPointer = INVALID_POINTER;
			if(rightPointer != INVALID_POINTER){//не отпущен
				moveRight();//начать движение вправо
			}else{
				stop();//происходит только тогда, когда убрали два пальца с тачскрина
			}
		}else if(pointer == rightPointer){
			rightPointer = INVALID_POINTER;
			if(leftPointer != INVALID_POINTER){
				moveLeft();
			}else{
				stop();
			}
		}
		return false;
	}

	public void keyDown(int keyCode){
		switch (keyCode){
			case Input.Keys.A:
			case Input.Keys.LEFT:
				pressedLeft = true;
				moveLeft();
				break;
			case Input.Keys.D:
			case Input.Keys.RIGHT:
				pressedRight = true;
				moveRight();
				break;
			case Input.Keys.Q:
				shootLeft();
				break;
			case Input.Keys.W:
				shoot();
				break;
			case Input.Keys.E:
				shootRight();
				break;
		}
	}

	public void keyUp(int keyCode){
		switch (keyCode){
			case Input.Keys.A:
			case Input.Keys.LEFT:
				pressedLeft = false;
				if(pressedRight) moveRight();
				else stop();
				break;
			case Input.Keys.D:
			case Input.Keys.RIGHT:
				pressedRight = false;
				if(pressedLeft) moveLeft();
				else stop();
				break;
			case Input.Keys.UP:
				break;
		}
	}

	private void moveRight(){//движение вправо
		v.set(v0);
	}

	private void moveLeft(){
		v.set(v0).rotate(180);//движение влево - разворачиваем вектор
	}

	private void stop(){
		v.setZero();//обнуление вектора
	}

	public Vector2 getV() {
		return v;
	}

	public boolean isBulletCollision(Rect bullet){//проверка куда именно долетела пуля
		return !(
				bullet.getRight() < getLeft()
						|| bullet.getLeft() > getRight()
						|| bullet.getBottom() > pos.y
						|| bullet.getTop() < getBottom()//пуля находится в половине корабля задней
		);
	}

}
