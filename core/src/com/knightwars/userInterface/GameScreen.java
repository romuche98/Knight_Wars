/*
 * Draw the game
 */


package com.knightwars.userInterface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.knightwars.game.KnightWarsGame;


public class GameScreen implements Screen {

    private final Display display;
    private final Stage stage;
    private final FitViewport viewport;
    private final SpriteBatch batch;
    private final Camera camera;
    private final Vector2 mapSize;

    public static final float SCALE = 320f;

    public GameScreen(final Display display, KnightWarsGame gameState) {
        this.display = display;
        batch = new SpriteBatch();

        // Fetch the map size from the game state
        mapSize = gameState.getMap().getSize();

        // Create a new camera
        camera = new OrthographicCamera();

        // Create a viewport so the proportions of the displayed map are always preserved
        viewport = new FitViewport(mapSize.x * SCALE, mapSize.y * SCALE, camera);

        // Create a stage and add all the actors to display
        stage = new Stage(viewport, batch);
        stage.addActor(new GameScreenMapDisplay(gameState));
        stage.addActor(new GameScreenHUD(gameState));
    }


    /**
     * Called when this screen becomes the current screen.
     */
    @Override
    public void show() {

    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        // Clear last screen
        Gdx.gl.glClearColor(0, 0.3f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw the map boundaries
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0f, 0.5f, 0f, 1f);
        shapeRenderer.rect(0, 0, mapSize.x * SCALE, mapSize.y * SCALE);
        shapeRenderer.end();

        stage.act();
        stage.draw();
    }

    /**
     * Called when the screen is resized
     *
     * @param width  The width of the resized screen.
     * @param height The height of the resized screen.
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    /**
     * Called when the game is paused, usually when it's not active or visible on-screen. The game is also
     * paused before it is destroyed.
     */
    @Override
    public void pause() {

    }

    /**
     * Called when the game is resumed from a paused state, usually when it regains focus.
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen.
     */
    @Override
    public void hide() {

    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
        batch.dispose();
    }
}
