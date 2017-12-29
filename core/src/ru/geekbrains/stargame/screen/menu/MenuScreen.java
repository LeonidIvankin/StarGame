package ru.geekbrains.stargame.screen.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.engine.Base2DScreen;
import ru.geekbrains.engine.Sprite2DTexture;
import ru.geekbrains.engine.math.Rect;
import ru.geekbrains.engine.math.Rnd;
import ru.geekbrains.engine.ui.ActionListener;
import ru.geekbrains.stargame.Background;
import ru.geekbrains.stargame.screen.game.GameScreen;
import ru.geekbrains.stargame.screen.menu.ui.ButtonExit;
import ru.geekbrains.stargame.screen.menu.ui.ButtonNewGame;
import ru.geekbrains.stargame.star.Star;

/**
 * Меню - для кнопок
 * Наследуется от Base2DScreen, следовательно
 * отлавливает все события, происходящие на экране touchUp, touchDown, ...
 * передает их в Sprite -> ScaledTouchUpButton
 */

public class MenuScreen extends Base2DScreen implements ActionListener{

	private static final float BUTTON_PRESS_SCALE = 0.9f;//кнопка уменьшается на 10%
	private static final float BUTTON_HEIGHT = 0.15f;//высота кнопки
	private static final int STAR_COUNT = 256;
	private static final float STAR_WIDTH = 0.01f;

	Sprite2DTexture textureBackground;
	Background background;
	private TextureAtlas textureAtlas;
	private ButtonExit buttonExit;
	private ButtonNewGame buttonNewGame;

	private Star star[];

	public MenuScreen(Game game) {
		super(game);
	}

	@Override
	public void show() {
		super.show();
		textureBackground = new Sprite2DTexture("textures/bg.png");
		background = new Background(new TextureRegion(textureBackground));
		textureAtlas = new TextureAtlas("textures/menuAtlas.tpack");//создали атлас
		buttonExit = new ButtonExit(textureAtlas, this, BUTTON_PRESS_SCALE);//создали кнопку, передали ссылку на ActionListener
		buttonExit.setHeightProportion(BUTTON_HEIGHT);//устанавливаем кнопке высоту
		buttonNewGame = new ButtonNewGame(textureAtlas, this, BUTTON_PRESS_SCALE);
		buttonNewGame.setHeightProportion(BUTTON_HEIGHT);

		star = new Star[STAR_COUNT];
		for (int i = 0; i < star.length; i++) {
			star[i] = new Star(textureAtlas, Rnd.nextFloat(-0.005f, 0.005f), Rnd.nextFloat(-0.5f, -0.1f), STAR_WIDTH);
		}
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		update(delta);
		draw();

	}

	public void update(float delta){//метод обновления информации об объектах
		for (int i = 0; i < star.length; i++) {
			star[i].update(delta);
		}
	}

	public void draw(){//метод отрисовки
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		background.draw(batch);
		for (int i = 0; i < star.length; i++) {
			star[i].draw(batch);
		}
		buttonExit.draw(batch);
		buttonNewGame.draw(batch);
		batch.end();
	}

	@Override
	public void dispose() {
		super.dispose();
		textureAtlas.dispose();
		textureBackground.dispose();
		batch = null;//потерялась ссылка на батчер
	}

	@Override
	protected void resize(Rect worldBounds) {
		background.resize(worldBounds);
		buttonExit.resize(worldBounds);//передаем кнопке resize
		buttonNewGame.resize(worldBounds);
		for (int i = 0; i < star.length; i++) {
			star[i].resize(worldBounds);//передаем звезде
		}
	}

	@Override
	public void touchDown(Vector2 touch, int pointer) {
		buttonExit.touchDown(touch, pointer);//событие на кнопку
		buttonNewGame.touchDown(touch, pointer);
		System.out.println("touchDown " + touch.x + " " + touch.y);
	}

	@Override
	public void touchUp(Vector2 touch, int pointer) {
		buttonExit.touchUp(touch, pointer);//событие на кнопку
		buttonNewGame.touchUp(touch, pointer);
		System.out.println("touchUp " + touch.x + " " + touch.y);
	}

	@Override
	public void actionPerformed(Object src) {
		if(src == buttonExit){//какой объект вызвал кнопку
			Gdx.app.exit();//завершаем приложение
		}else if(src == buttonNewGame){
			//переключение экранов
			game.setScreen(new GameScreen(game));//новый экран
		}else{
			throw new RuntimeException("Unknow src = " + src);
		}
	}
}
