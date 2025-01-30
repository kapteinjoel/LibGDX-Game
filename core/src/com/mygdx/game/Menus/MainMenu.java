package com.mygdx.game.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.Core.Game;
import com.mygdx.game.Gui.Button;
import com.mygdx.game.Core.AssetManagerSingleton;

public class MainMenu extends Menu {

    private Button singlePlayerButton;
    private Button exitButton;

    // Updated constructor to accept viewport and camera
    public MainMenu(Viewport viewport, OrthographicCamera camera) {
        super(viewport, camera);

        // Load button textures from the asset manager
        Texture upTexture = AssetManagerSingleton.getTexture("button_up.png");
        Texture downTexture = AssetManagerSingleton.getTexture("button_down.png");

        // Set button dimensions and position (centered)
        float buttonWidth = 500;  // Example width
        float buttonHeight = 50;  // Example height
        float buttonX = (viewport.getWorldWidth() - buttonWidth) / 2;  // Center horizontally
        float buttonY = (viewport.getWorldHeight() - buttonHeight) / 2; // Center vertically

        // Create the Single Player button
        singlePlayerButton = new Button("Single Player", buttonX, buttonY, buttonWidth, buttonHeight, upTexture, downTexture);
        exitButton = new Button("Exit", buttonX, buttonY - 100, buttonWidth, buttonHeight, upTexture, downTexture);
        // Add the button to the menu
        addButton(singlePlayerButton);
        addButton(exitButton);
    }

    @Override
    public void handleInput(Vector2 mousePos) {
        super.handleInput(mousePos);
        // Additional handling for when the single player button is clicked
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && singlePlayerButton.isClicked(mousePos)) {
            // Handle the single player button action (e.g., navigate to the game screen)
            System.out.println("Single Player Button Clicked!");
            Game.changeState(Game.GameState.GAME_STATE);

        }
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && exitButton.isClicked(mousePos)) {

            System.exit(0);

        }
    }

    @Override
    public void render(SpriteBatch batch) {
        // Call the parent class render to draw the menu and buttons
        super.render(batch);
        //System.out.println("Button Bounds: " + singlePlayerButton.getBounds());
    }

    @Override
    public void dispose() {
        // Dispose of the button resources
        singlePlayerButton.dispose();
        super.dispose();
    }
}