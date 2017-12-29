package ru.geekbrains.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

/**
 * Фильтр сглаживания для текстур
 */

public class Sprite2DTexture extends Texture{
	public Sprite2DTexture(String internalPath) {
		this(Gdx.files.internal(internalPath));
		//путь к нашей тектуре в папе assets
	}

	public Sprite2DTexture(FileHandle file) {
		super(file, true);//дескриптор файла
		setFilter(TextureFilter.MipMapLinearNearest, TextureFilter.Linear);
		//скалирование в большую и меньшую сторону
	}
}
