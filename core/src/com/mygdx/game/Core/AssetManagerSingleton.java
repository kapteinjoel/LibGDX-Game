package com.mygdx.game.Core;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class AssetManagerSingleton {
    private static AssetManager instance;

    // Private constructor to prevent instantiation
    private AssetManagerSingleton() {}

    // Method to get the instance of the AssetManager
    public static AssetManager getInstance() {
        if (instance == null) {
            instance = new AssetManager();
        }
        return instance;
    }

    // Method to get a texture from the asset manager
    public static Texture getTexture(String textureName) {
        // Make sure the texture is loaded first before fetching it
        if (!getInstance().isLoaded(textureName, Texture.class)) {
            getInstance().load(textureName, Texture.class);
            getInstance().finishLoadingAsset(textureName);  // Blocks until the asset is loaded
        }
        return getInstance().get(textureName, Texture.class);
    }
}