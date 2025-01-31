package com.mygdx.game.World;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class TileMap {
    private int[][] tiles; // Store tile types as numbers
    private Texture[] textures; // Store textures in an array
    private int tileSize = 32; // Tile size in pixels
    private int width, height;

    public TileMap(int width, int height) {
        this.width = width;
        this.height = height;
        tiles = new int[width][height]; // Stores tile IDs
        textures = new Texture[2]; // Store the two textures

        // Load textures once
        textures[0] = new Texture("Untitled.png");
        textures[1] = new Texture("Untitled2.png");

        // Fill the tile map with random IDs (0 or 1)
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = MathUtils.randomBoolean() ? 0 : 1;
            }
        }
    }

    public void renderVisibleTiles(OrthographicCamera camera, SpriteBatch batch) {
        // Get the camera's bounds
        float left = camera.position.x - camera.viewportWidth / 2;
        float right = camera.position.x + camera.viewportWidth / 2;
        float bottom = camera.position.y - camera.viewportHeight / 2;
        float top = camera.position.y + camera.viewportHeight / 2;

        // Convert to tile indices
        int startX = Math.max(0, (int) (left / tileSize));
        int endX = Math.min(width - 1, (int) (right / tileSize));
        int startY = Math.max(0, (int) (bottom / tileSize));
        int endY = Math.min(height - 1, (int) (top / tileSize));

        // Render only visible tiles
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                int tileID = tiles[x][y];
                batch.draw(textures[tileID], x * tileSize, y * tileSize, tileSize, tileSize);
            }
        }
    }

    public void dispose() {
        // Dispose of textures
        for (Texture texture : textures) {
            texture.dispose();
        }
    }
}
