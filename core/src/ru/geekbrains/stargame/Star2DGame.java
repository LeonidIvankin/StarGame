package ru.geekbrains.stargame;


import com.badlogic.gdx.Game;

import ru.geekbrains.stargame.screen.menu.MenuScreen;


public class Star2DGame extends Game {//стартовый класс; создается 1ый экран; с чего игра начинается
	@Override
	public void create() {
		//запускаем игру, устанавливаем текущиий экран
		setScreen(new MenuScreen(this));
	}

}
