package com.denizdogan.survivorbird;

import com.badlogic.gdx.Gdx;

public class GameConstants {

    public static final float WIDTH = Gdx.graphics.getWidth();
    public static final float HEIGHT = Gdx.graphics.getHeight();
    public static final float GAME_ACTOR_WIDTH = WIDTH / 15;
    public static final float GAME_ACTOR_HEIGHT = HEIGHT / 10;
    public static final float CIRCLE_OFFSET_X = GAME_ACTOR_WIDTH / 2;
    public static final float CIRCLE_OFFSET_Y = GAME_ACTOR_HEIGHT / 2;
    public static final float SCORE_X = GAME_ACTOR_WIDTH / 2;
    public static final float SCORE_Y = HEIGHT -  GAME_ACTOR_HEIGHT / 2;
}
