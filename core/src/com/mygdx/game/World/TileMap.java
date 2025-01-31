package com.mygdx.game.World;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TileMap {
    private static final int TILE_SIZE = 32; // Tile size in pixels
    private static final int CHUNK_SIZE = 10; // Size of a chunk in tiles (10x10 tiles per chunk)
    private static final int CHUNK_RENDER_RADIUS = 5; // Number of chunks to render around the player
    private static final long CHUNK_UNLOAD_DELAY = 5000; // Time in milliseconds before a chunk is unloaded

    private Map<String, Tile[]> chunkCache = new HashMap<>(); // Cache of loaded chunks
    private Map<String, Long> chunkLastAccessed = new HashMap<>(); // Track the last time a chunk was accessed
    private int width, height;
    private Texture[] textures; // Textures for tile types

    public TileMap(int width, int height) {
        this.width = width;
        this.height = height;

        textures = new Texture[2]; // Store the two textures
        textures[0] = new Texture("Untitled.png");
        textures[1] = new Texture("Untitled2.png");
    }

    // Create a unique key for each chunk based on its coordinates
    private String getChunkKey(int chunkX, int chunkY) {
        return chunkX + "_" + chunkY;
    }

    // Generate or retrieve a chunk of tiles
    private Tile[] getChunk(int chunkX, int chunkY) {
        String chunkKey = getChunkKey(chunkX, chunkY);

        // Check if the chunk is already loaded
        if (chunkCache.containsKey(chunkKey)) {
            // Update last access time
            chunkLastAccessed.put(chunkKey, System.currentTimeMillis());
            return chunkCache.get(chunkKey);
        }

        // Create and cache a new chunk
        Tile[] chunk = new Tile[CHUNK_SIZE * CHUNK_SIZE];
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int y = 0; y < CHUNK_SIZE; y++) {
                int tileID = MathUtils.randomBoolean() ? 0 : 1; // Randomly choose tile ID
                chunk[x + y * CHUNK_SIZE] = new Tile(x * TILE_SIZE + chunkX * CHUNK_SIZE * TILE_SIZE,
                        y * TILE_SIZE + chunkY * CHUNK_SIZE * TILE_SIZE,
                        textures[tileID]);
            }
        }

        // Cache the chunk and update the access time
        chunkCache.put(chunkKey, chunk);
        chunkLastAccessed.put(chunkKey, System.currentTimeMillis());
        return chunk;
    }

    // Load chunks dynamically based on the player's position
    public void loadChunks(OrthographicCamera camera) {
        unloadChunks(camera); // Unload chunks that are out of range

        // Now load the new chunks within the render radius
        int playerChunkX = (int) (camera.position.x / (CHUNK_SIZE * TILE_SIZE));
        int playerChunkY = (int) (camera.position.y / (CHUNK_SIZE * TILE_SIZE));

        for (int chunkX = playerChunkX - CHUNK_RENDER_RADIUS; chunkX <= playerChunkX + CHUNK_RENDER_RADIUS; chunkX++) {
            for (int chunkY = playerChunkY - CHUNK_RENDER_RADIUS; chunkY <= playerChunkY + CHUNK_RENDER_RADIUS; chunkY++) {
                getChunk(chunkX, chunkY); // Ensure chunk is loaded and accessed
            }
        }
    }

    // Unload chunks that are too far from the player
    private void unloadChunks(OrthographicCamera camera) {
        int playerChunkX = (int) (camera.position.x / (CHUNK_SIZE * TILE_SIZE));
        int playerChunkY = (int) (camera.position.y / (CHUNK_SIZE * TILE_SIZE));

        // Iterate through the cache and unload chunks that are out of range or haven't been accessed in a while
        Iterator<Map.Entry<String, Tile[]>> iterator = chunkCache.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Tile[]> entry = iterator.next();
            String chunkKey = entry.getKey();
            String[] coordinates = chunkKey.split("_");
            int chunkX = Integer.parseInt(coordinates[0]);
            int chunkY = Integer.parseInt(coordinates[1]);

            // Check if the chunk is outside the render radius
            if (Math.abs(chunkX - playerChunkX) > CHUNK_RENDER_RADIUS || Math.abs(chunkY - playerChunkY) > CHUNK_RENDER_RADIUS) {
                long lastAccessed = chunkLastAccessed.getOrDefault(chunkKey, 0L);
                if (System.currentTimeMillis() - lastAccessed > CHUNK_UNLOAD_DELAY) {
                    iterator.remove(); // Unload chunk if it hasn't been accessed recently
                    chunkLastAccessed.remove(chunkKey); // Remove from access tracking
                }
            }
        }
    }

    // Render the visible tiles (only those within the camera view)
    public void renderVisibleTiles(OrthographicCamera camera, SpriteBatch batch) {
        int playerChunkX = (int) (camera.position.x / (CHUNK_SIZE * TILE_SIZE));
        int playerChunkY = (int) (camera.position.y / (CHUNK_SIZE * TILE_SIZE));

        // Render all chunks in the visible area around the player
        for (int chunkX = playerChunkX - CHUNK_RENDER_RADIUS; chunkX <= playerChunkX + CHUNK_RENDER_RADIUS; chunkX++) {
            for (int chunkY = playerChunkY - CHUNK_RENDER_RADIUS; chunkY <= playerChunkY + CHUNK_RENDER_RADIUS; chunkY++) {
                Tile[] chunk = getChunk(chunkX, chunkY); // Get the chunk (lazy loading)

                // Render each tile in the chunk
                for (Tile tile : chunk) {
                    tile.render(batch);
                }
            }
        }
    }

    // Dispose of textures when the game is done
    public void dispose() {
        for (Texture texture : textures) {
            texture.dispose();
        }
    }
}