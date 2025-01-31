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
    public static boolean isLeftMouseButtonPressed() {
        return Gdx.input.isButtonPressed(Input.Buttons.LEFT);
    }

    public static boolean isRightMouseButtonPressed() {
        return Gdx.input.isButtonPressed(Input.Buttons.RIGHT);
    }

    public static float getMouseX() {
        return Gdx.input.getX();
    }

    public static float getMouseY() {
        return Gdx.input.getY();
    }
}
