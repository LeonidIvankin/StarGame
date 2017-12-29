package ru.geekbrains.stargame.explosion;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.engine.sprite.Sprite;

public class Explosion extends Sprite {//класс для взрыва

	private Sound sound;
	private float animateInterval = 0.017f;
	private float animateTimer;

	public Explosion(TextureRegion region, int rows, int cols, int frame, Sound sound) {
		super(region, rows, cols, frame);
		this.sound = sound;
	}

	public void set(float height, Vector2 pos){
		this.pos.set(pos);
		setHeightProportion(height);
		if(sound.play() == -1) throw new RuntimeException("sound.play() == -1");
	}

	@Override
	public void update(float deltaTime) {
		animateTimer += deltaTime;
		if(animateTimer >= animateInterval){
			animateTimer = 0f;
			if(++frame == regions.length){
				destroy();
			}
		}
	}

	@Override
	public void destroy() {
		super.destroy();
		frame = 0;
	}
}
