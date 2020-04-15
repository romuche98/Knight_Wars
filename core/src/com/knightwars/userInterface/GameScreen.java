package com.knightwars.userInterface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.knightwars.game.KnightWarsGame;
import com.knightwars.game.environment.Building;

import java.util.ArrayList;
import java.util.List;


public class GameScreen implements Screen {

    private final Display display;
    private final KnightWarsGame gameState;

    private final FitViewport viewport;
    private final SpriteBatch batch;
    private final Sprite spriteRedUnit;
    private final Sprite spriteRedBuilding;
    private final Sprite spriteBlueBuilding;
    private final Sprite spriteNeutralBuilding;
    private final Camera camera;
    private final Vector2 mapSize;

    public static final float SCALE = 320f;

    public GameScreen(final Display display, KnightWarsGame gameState) {
        this.display = display;
        this.gameState = gameState;
        batch = new SpriteBatch();
        spriteRedUnit = new Sprite(new Texture("red_unit.png"));
        spriteRedBuilding = new Sprite(new Texture("red_building.png"));
        spriteBlueBuilding = new Sprite(new Texture("blue_building.png"));
        spriteNeutralBuilding = new Sprite(new Texture("neutral_building.png"));

        // Fetch the map size from the game state
        mapSize = gameState.getMap().getSize(); //new Vector2(3f, 2f);

        // Constructs a new OrthographicCamera, using the given viewport width and height
        // Height is multiplied by aspect ratio.
        camera = new OrthographicCamera();
        camera.position.set(mapSize.x * SCALE / 2, mapSize.y * SCALE / 2, 0);

        viewport = new FitViewport(mapSize.x * SCALE, mapSize.y * SCALE, camera);
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
        // Fetch the coordinates of the buildings and units
        ArrayList<Building> buildings = gameState.getMap().getBuildings();
        List<Vector2> unitCoordinates = new ArrayList<>();
        unitCoordinates.add(new Vector2(1f, 1f));

        // Clear last screen
        Gdx.gl.glClearColor(0, 0.3f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update the camera and the inverse window projection
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        Vector3 pos = new Vector3();
        pos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.unproject(pos);

        // Draw the map boundaries
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0f, 0.5f, 0f, 1f);
        shapeRenderer.rect(0, 0, mapSize.x * SCALE, mapSize.y * SCALE);
        shapeRenderer.end();

        // Draw the map elements
        batch.begin();
        for (Building building : buildings) {
            Vector2 buildingCoordinates = building.getCoordinates();
            batch.draw(determineBuildingSprite(building), buildingCoordinates.x * SCALE - spriteRedBuilding.getWidth() / 2f,
                    buildingCoordinates.y * SCALE - spriteRedUnit.getHeight() / 2f);
        }
        for (Vector2 unitCoordinate : unitCoordinates) {
            batch.draw(spriteRedUnit, unitCoordinate.x * SCALE - spriteRedBuilding.getWidth() / 2f,
                    unitCoordinate.y * SCALE - spriteRedBuilding.getHeight() / 2f);
        }
        batch.end();
    }

    private Sprite determineBuildingSprite(Building building) {
        if (building.getOwner() == gameState.getPlayerRed()) {
            return spriteRedBuilding;
        }
        else if (building.getOwner() == gameState.getPlayerBlue()) {
            return spriteBlueBuilding;
        }
        else if (building.getOwner() == gameState.getPlayerNeutral()) {
            return spriteNeutralBuilding;
        }
        else {
            throw new UnknownPlayerException("There is no player associated with the building.");
        }
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
