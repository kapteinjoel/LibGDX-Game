package World;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class IsometricTileMap {
    private Tile[][] tiles;  // 2D array of tiles
    private int width, height;  // Width and height of the map in terms of tiles

    public IsometricTileMap(int width, int height) {
        this.width = width;
        this.height = height;
        tiles = new Tile[width][height];
    }

    public void setTile(int x, int y, Tile tile) {
        tiles[x][y] = tile;
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    // Render the tiles using isometric projection
    public void render(SpriteBatch batch) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tile tile = tiles[x][y];
                if (tile != null) {
                    // Convert isometric coordinates to screen coordinates
                    Vector2 screenPos = isoToScreen(x, y);
                    batch.draw(tile.getTexture(), screenPos.x, screenPos.y);
                }
            }
        }
    }

    // Convert isometric coordinates to screen coordinates
    private Vector2 isoToScreen(int x, int y) {
        float screenX = (x - y) * Tile.TILE_WIDTH / 2;
        float screenY = (x + y) * Tile.TILE_HEIGHT / 2;
        return new Vector2(screenX, screenY);
    }
}
