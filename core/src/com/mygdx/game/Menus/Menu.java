package com.mygdx.game.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Gui.Button;

public abstract class Menu {
    protected Viewport viewport;
    protected OrthographicCamera camera;
    protected Array<Button> buttons;

    public Menu(Viewport viewport, OrthographicCamera camera) {
        this.viewport = viewport;
        this.camera = camera;
        buttons = new Array<>();
    }
    public void addButton(Button button) {
        buttons.add(button);
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
        scaleButtons(width, height);
    }
    protected void scaleButtons(int screenWidth, int screenHeight) {
        float scaleX = (float) screenWidth / viewport.getWorldWidth();
        float scaleY = (float) screenHeight / viewport.getWorldHeight();
        float scale = Math.min(scaleX, scaleY); // Uniform scaling to maintain aspect ratio

        for (Button button : buttons) {
            float buttonWidth = button.getBaseWidth() * scale;
            float buttonHeight = button.getBaseHeight() * scale;
            float buttonX = (screenWidth - buttonWidth) / 2; // Centered horizontally
            float buttonY = button.getBaseY() * scale;       // Adjust vertical position
            button.setSize(buttonWidth, buttonHeight);
            button.setPosition(buttonX, buttonY);
        }
    }

    // Handle input (e.g., mouse clicks)
    public void handleInput(Vector2 mousePos) {
        for (Button button : buttons) {
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && button.isClicked(mousePos)) {
                button.onClick();
            }
        }
        for (Button button : buttons) {
            if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                button.onLift();
            }
        }

    }

    // Render the menu and all buttons
    public void render(SpriteBatch batch) {
        for (Button button : buttons) {
            button.render(batch);
        }
    }

    public void dispose() {
        for (Button button : buttons) {
            button.dispose();
        }
    }
}