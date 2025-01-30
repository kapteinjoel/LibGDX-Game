package World;

import com.badlogic.gdx.graphics.Texture;

public class Tile {

    public static final int TILE_WIDTH = 64;  // Width of the tile
    public static final int TILE_HEIGHT = 32; // Height of the tile
    private int x, y; // Tile coordinates in isometric space
    private Texture texture; // Texture for the tile

    public Tile(int x, int y, Texture texture) {
        this.x = x;
        this.y = y;
        this.texture = texture;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Texture getTexture() {
        return texture;
    }
}