package ru.geekbrains.engine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.engine.math.MatrixUtils;
import ru.geekbrains.engine.math.Rect;

/**
 * Базовый класс для экранов
 * Отлавливает все события, происходящие на экране touchUp, touchDown, ...
 */

public class Base2DScreen implements Screen, InputProcessor{

	protected Game game;
	private Rect screenBounds; //реальные границы экрана в пикселях
	protected Rect worldBounds; //границы проекций мировых координат
	private Rect glBounds; //от 0 до 1f; дефолтные проекцие мир - gl

	protected Matrix4 worldToGl;//матрица для преобразования из мировых в gl
	protected Matrix3 screenToWorld;

	Vector2 touch = new Vector2();

	protected SpriteBatch batch;//получает текстуру и координаты каждого прямоугольника

	public Base2DScreen(Game game) {
		this.game = game;
		this.screenBounds = new Rect();
		this.worldBounds = new Rect();
		this.glBounds = new Rect(0, 0, 1f, 1f);
		this.worldToGl = new Matrix4();//единичная матрица 4х4
		this.screenToWorld = new Matrix3();
		//проверка, чтобы в памяти не висел batch
		if(this.batch != null) throw new RuntimeException("Повторная установка screen без dispose");
		this.batch = new SpriteBatch();
	}

	@Override
	public void show() {//инициализация всех граф объектов
		System.out.println("show");
		Gdx.input.setInputProcessor(this);//установили inputProcessor, отлавливаем события; без строчки не будут отлавливаться события мыши
	}

	@Override
	public void render(float delta) {//60 раз в секунду; отрисовка

	}

	@Override
	public void resize(int width, int height) {//изм размера экрана
		System.out.println("resize" + width + " " + height);
		screenBounds.setSize(width, height);
		screenBounds.setLeft(0);
		screenBounds.setBottom(0);

		float aspect = width / (float) height;
		worldBounds.setHeight(1f);
		worldBounds.setWidth(1f * aspect);
		MatrixUtils.calcTransitionMatrix(worldToGl, worldBounds, glBounds);//пересчет матрицы worldBounds в glBounds
		batch.setProjectionMatrix(worldToGl);
		MatrixUtils.calcTransitionMatrix(screenToWorld, screenBounds, worldBounds);//из screenBounds в worldBounds
		resize(worldBounds);
	}

	protected void resize(Rect worldBounds){

	}

	@Override
	public void pause() {//при остановке; из поля видимости
		System.out.println("pause");
	}

	@Override
	public void resume() {//появляется в поле видимости
		System.out.println("resume");
	}

	@Override
	public void hide() {//скрывает полностью
		System.out.println("hide");
		dispose();//как только скрываем уходит из памяти
	}

	@Override
	public void dispose() {//когда уничтожается
		System.out.println("dispose");
		batch.dispose();

	}

	@Override
	public boolean keyDown(int keycode) {//нажали клавишу
		System.out.println("keyDown" + keycode);
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {//отпустили клавишу
		System.out.println("keyUp" + keycode);
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		System.out.println("keyTyped" + character);
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {//нажали кнопку мыши, экран; pointer - палец
//отлавливается в screen координатах
		touch.set(screenX, screenBounds.getHeight() - 1f - screenY).mul(screenToWorld);//переворачиваем координату Y и переводим в мировые координаты
		touchDown(touch, pointer);
		return false;
	}

	public void touchDown(Vector2 touch, int pointer) {
//в мировой системе координат и ввиде вектора
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {//отпустили кнопку мыши, экран
		touch.set(screenX, screenBounds.getHeight() - 1f - screenY).mul(screenToWorld);//переворачиваем координату Y и переводим в мировые координаты
		touchUp(touch, pointer);
		return false;
	}

	public void touchUp(Vector2 touch, int pointer) {

	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {//нажали и провели
		return false;
	}

	public void touchDragged(Vector2 touch, int pointer) {

	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
