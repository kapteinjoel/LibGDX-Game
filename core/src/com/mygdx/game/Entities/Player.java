package com.mygdx.game.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Core.InputHandler;

public class Player {
    private Vector2 position;
    private Texture texture;
    private float speed = 200f; // Movement speed (adjust as needed)

    public Player(float x, float y) {
        position = new Vector2(x, y);
        texture = new Texture("branden.png"); // Ensure you have a player sprite in assets!
    }

    public void update(float deltaTime) {
        handleInput(deltaTime);
    }

    private void handleInput(float deltaTime) {
        float moveAmount = speed * deltaTime;

        if (InputHandler.isMoveUpPressed()) {
            position.y += moveAmount;
        }
        if (InputHandler.isMoveDownPressed()) {
            position.y -= moveAmount;
        }
        if (InputHandler.isMoveLeftPressed()) {
            position.x -= moveAmount;
        }
        if (InputHandler.isMoveRightPressed()) {
            position.x += moveAmount;
        }
    }
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }

    public void dispose() {
        texture.dispose();
    }

    public Vector2 getPosition() {
        return position;
    }
}