package com.mygdx.game.Core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Menus.MainMenu;
import com.mygdx.game.Entities.Player;
import com.mygdx.game.World.TileMap;
//import com.mygdx.game.Entities.Player;

import java.io.File;

public class Game extends ApplicationAdapter {
	private AssetManager assetManager;
	private SpriteBatch batch;
	private Vector2 mousePos = new Vector2();
	private MainMenu mainMenu;
	private OrthographicCamera camera;
	private Viewport viewport;
	private Player player;
	private TileMap tileMap; // Tile map object

	public enum GameState {
		MAIN_MENU,
		GAME_STATE,
		CHARACTER_CREATE,
		WORLD_CREATE,
		GAME_OVER
	}

	static GameState currentState;

	@Override
	public void create() {
		assetManager = AssetManagerSingleton.getInstance();
		batch = new SpriteBatch();

		// Initialize camera and viewport
		camera = new OrthographicCamera();
		viewport = new FitViewport(1920, 1080, camera);
		viewport.apply(true);
		viewport.update(1920, 1080);

		// Load assets
		loadAllTexturesFromFolder("assets/");
		assetManager.finishLoading();

		// Set the initial game state
		currentState = GameState.MAIN_MENU;

		// Pass the camera and viewport to MainMenu
		mainMenu = new MainMenu(viewport, camera);

		// Initialize game objects
		player = new Player(100, 100);
		tileMap = new TileMap(10000, 10000); // Create a large tile map (e.g., 100x100 tiles)
	}

	@Override
	public void render() {
		ScreenUtils.clear(0, 0, 1, 1);  // Clear the screen with a blue color
		batch.begin();

		mousePos = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

		// Update the camera
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		switch (currentState) {
			case MAIN_MENU:
				mainMenu.render(batch);
				mainMenu.handleInput(mousePos);
				break;
			case GAME_STATE:
				float deltaTime = Gdx.graphics.getDeltaTime();

				// Update the player and camera position
				player.update(deltaTime);
				camera.position.set(player.getPosition().x, player.getPosition().y, 0);
				camera.update();

				// Load chunks dynamically based on the player's position
				tileMap.loadChunks(camera);

				// Render the visible tiles
				tileMap.renderVisibleTiles(camera, batch);

				// Render the player
				player.render(batch);
				break;
			case WORLD_CREATE:
				break;
			case GAME_OVER:
				break;
		}
		batch.end();
	}

	public static void changeState(GameState newState) {
		currentState = newState;
	}

	private void loadAllTexturesFromFolder(String folderPath) {
		File folder = new File(folderPath);

		// Filter to only load .png files
		File[] textureFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".png"));

		if (textureFiles != null) {
			for (File file : textureFiles) {
				String relativePath = file.getName(); // Get file name (relative to assets folder)
				assetManager.load(relativePath, Texture.class); // Load each texture file
				System.out.println("Loading texture: " + relativePath); // Optional: Log the loaded files
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0); // Center the camera
	}

	@Override
	public void dispose() {
		batch.dispose();
		player.dispose();
		assetManager.dispose();
	}
}