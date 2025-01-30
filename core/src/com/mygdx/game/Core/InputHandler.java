package com.mygdx.game.Core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class InputHandler {
    public static boolean isMoveUpPressed() {
        return Gdx.input.isKeyPressed(Input.Keys.W);
    }

    public static boolean isMoveDownPressed() {
        return Gdx.input.isKeyPressed(Input.Keys.S);
    }

    public static boolean isMoveLeftPressed() {
        return Gdx.input.isKeyPressed(Input.Keys.A);
    }

    public static boolean isMoveRightPressed() {
        return Gdx.input.isKeyPressed(Input.Keys.D);
    }
}
