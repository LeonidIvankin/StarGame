package ru.geekbrains.engine.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.engine.math.Rect;
import ru.geekbrains.engine.utils.Regions;

/**
 * Суперкласс для графических объектов
 */

public class Sprite extends Rect{

	protected float angle;
	protected float scale = 1f;
	protected TextureRegion[] regions;//массив текстур
	protected int frame;//какая текстура текущая

	private boolean isDestroyed;//пометка на удаление пули

	public Sprite(){

	}

	public Sprite(TextureRegion region) {
		if(region == null) throw new NullPointerException();
		regions = new TextureRegion[1];//передаем 1 текстуру
		this.regions[0] = region;//устанавливаем в 0ую позицию нашего массива

	}

	//конструктор для массива текстур
	public Sprite(TextureRegion region, int rows, int cols, int frame) {
		this.regions = Regions.split(region, rows, cols, frame);//вызываем нарезку регионов
	}

	//графический объект мог сам себя рисовать
	//можем заворачивать
	//рисуется по центру
	public void draw(SpriteBatch batch){
		batch.draw(
				regions[frame], //текущий регион
				getLeft(), getBottom(), //точка отрисовки
				halfWidth, halfHeight, //точка вращения
				getWidth(), getHeight(), //ширина и высота
				scale, scale, //масштаб по X и Y
				angle //угол вращения
		);
	}

	//смена разрешения экрана
	public void setWidthProportion(float width){
		setWidth(width);
		float aspect = regions[frame].getRegionWidth() / (float) regions[frame].getRegionHeight();//соотношение сторон
		setHeight(width * aspect);
	}

	public void setHeightProportion(float height){
		setHeight(height);
		float aspect = regions[frame].getRegionWidth() / (float) regions[frame].getRegionHeight();//соотношение сторон
		setWidth(height * aspect);
	}

	//методы при изменении
	public void resize(Rect worldBounds){

	}

	//объект реагирует на нажатие
	public boolean touchDown(Vector2 touch, int pointer){
		return false;
	}

	public boolean touchUp(Vector2 touch, int pointer){
		return false;
	}

	public boolean touchDragged(Vector2 touch, int pointer){
		return false;
	}

	//движение, смена позиции
	public void update(float deltaTime){

	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public void destroy(){//метод для помечения пули на удаление
		this.isDestroyed = true;
	}

	public void flushDestroy(){//снять пометку на удаление
		this.isDestroyed = false;
	}

	public boolean isDestroyed() {
		return isDestroyed;
	}
}
