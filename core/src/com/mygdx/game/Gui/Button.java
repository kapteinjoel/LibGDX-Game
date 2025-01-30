package com.mygdx.game.Gui;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Button {
    private Texture upTexture;
    private Texture downTexture;
    private Texture buttonTexture;
    private Rectangle bounds;
    private BitmapFont font;
    private String text;

    public boolean buttonState;

    private float baseWidth, baseHeight, baseX, baseY;

    // Constructor for button
    public Button(String text, float x, float y, float width, float height, Texture upTexture, Texture downTexture) {
        this.text = text;
        this.upTexture = upTexture;
        this.downTexture = downTexture;
        this.buttonTexture = upTexture; // Default texture is the "up" state

        this.bounds = new Rectangle(x, y, width, height);
        this.buttonState = false;
        this.baseX = x;
        this.baseY = y;
        this.baseWidth = width;
        this.baseHeight = height;

        // Create a basic font for rendering the text
        this.font = new BitmapFont(); // You can replace this with your own custom font
    }

    // Render the button
    public void render(SpriteBatch batch) {
        // Draw the button texture
        batch.draw(buttonTexture, bounds.x, bounds.y, bounds.width, bounds.height);

        // Draw the button text centered on the button
        GlyphLayout layout = new GlyphLayout(font, text);
        float textX = bounds.x + (bounds.width - layout.width) / 2;
        float textY = bounds.y + (bounds.height + layout.height) / 2; // Adjust text vertically
        font.draw(batch, text, textX, textY);
    }

    // Check if the button is clicked
    public boolean isClicked(Vector2 mousePos) {
        return bounds.contains(mousePos.x, mousePos.y);
    }

    // Set button state when clicked
    public void onClick() {
        buttonTexture = downTexture; // Change texture to "down" state
        this.buttonState = true;
        // Add your click handling logic here
    }

    // Set button state when not clicked
    public void onLift() {
        buttonTexture = upTexture; // Revert to "up" state
        this.buttonState = false;
    }

    // Set the button size
    public void setSize(float width, float height) {
        bounds.setSize(width, height);
    }

    // Set the button position
    public void setPosition(float x, float y) {
        bounds.setPosition(x, y);
    }

    // Get the button's base width (for scaling purposes)
    public float getBaseWidth() {
        return baseWidth;
    }

    // Get the button's base height (for scaling purposes)
    public float getBaseHeight() {
        return baseHeight;
    }

    // Get the button's base X position
    public float getBaseX() {
        return baseX;
    }

    // Get the button's base Y position
    public float getBaseY() {
        return baseY;
    }

    public void dispose() {
        font.dispose();
    }

    public String getBounds() {
        return String.valueOf(this.bounds.x) + " " + String.valueOf(this.bounds.y);
    }
}