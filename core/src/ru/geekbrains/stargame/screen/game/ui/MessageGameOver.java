package ru.geekbrains.stargame.screen.game.ui;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.engine.sprite.Sprite;

/**
 * Created by admin on 28.12.2017.
 */

public class MessageGameOver extends Sprite {//сообщение GameOver

    private static final float HEIGHT = 0.07f;
    private static final float BOTTOM_MARGIN = 0.009f;//отступ снизу

    public MessageGameOver(TextureAtlas atlas) {
         super(atlas.findRegion("message_game_over"));
         setHeightProportion(HEIGHT);
         setBottom(BOTTOM_MARGIN);
    }
}
