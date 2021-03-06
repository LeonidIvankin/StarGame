package ru.geekbrains.engine.utils;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Regions {

	/**
	 * Разбивает‚ TextureRegion на фреймы
	 * @param region регион
	 * @param rows количество строк
	 * @param cols количество столбцов
	 * @param frames количество фреймов
	 * @return массив регионов
	 */

	//на вход принимает TextureRegion - будем доставать из атласа
	//принимает количество строк и количество столбцов, количество фреймов
	//корабль: 1 строка, 2 столбца, 2 фрейма

	public static TextureRegion[] split(TextureRegion region, int rows, int cols, int frames) {
		if(region == null) throw new RuntimeException("Split null region");
		TextureRegion[] regions = new TextureRegion[frames];//создаётся одномерный массив по количеству фреймов
		int tileWidth = region.getRegionWidth() / cols;//вычисляется ширина фрейма
		int tileHeight = region.getRegionHeight() / rows;

		int frame = 0;
		for (int i = 0; i < rows; i++) {//происходит нарезка
			for (int j = 0; j < cols; j++) {
				regions[frame] = new TextureRegion(region, tileWidth * j, tileHeight * i, tileWidth, tileHeight);
				if(frame == frames - 1) return regions;
				frame++;
			}
		}
		return regions;
	}
}