package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import java.util.Iterator;
import java.util.Random;

public class GameScreen implements Screen {
    private final MyGame game;
    private Texture backgroundTexture;
    private float backgroundX;
    private float scale;
    private boolean isPaused;
    private Player player;
    private Array<Obstacle> obstacles;
    private Random random;
    private float obstacleSpawnTimer;

    public GameScreen(MyGame game) {
        this.game = game;
        backgroundTexture = new Texture(Gdx.files.internal("back2.png"));

        scale = (float) Gdx.graphics.getWidth() / backgroundTexture.getWidth();

        player = new Player();
        player.initialize();

        obstacles = new Array<>();
        random = new Random();
        obstacleSpawnTimer = random.nextFloat() * 2 + 1.5f; // Start spawn timer between 1.5 and 3.5 seconds

        backgroundX = 0;
        isPaused = false;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!isPaused) {
            player.update(delta);
            backgroundX -= 300 * delta;
            if (backgroundX <= -backgroundTexture.getWidth() * scale) {
                backgroundX = 0;
            }

            // Update and remove obstacles
            Iterator<Obstacle> obstacleIterator = obstacles.iterator();
            while (obstacleIterator.hasNext()) {
                Obstacle obstacle = obstacleIterator.next();
                obstacle.update(delta);

                // Check for collision with player
                if (obstacle.getHitBox().overlaps(player.getBoundingBox())) {
                    System.out.println("Collision detected!");
                    Gdx.app.exit(); // Placeholder for handling collision
                }

                // Remove obstacle if it goes off screen
                if (obstacle.getHitBox().x + obstacle.getHitBox().width < 0) {
                    obstacleIterator.remove();
                    obstacle.dispose();
                }
            }

            // Spawn a new obstacle
            obstacleSpawnTimer -= delta;
            if (obstacleSpawnTimer <= 0) {
                String[] obstacleTextures = {"rock.png", "wood.png"};
                String selectedTexture = obstacleTextures[random.nextInt(obstacleTextures.length)];

                Obstacle newObstacle = new Obstacle(selectedTexture);
                obstacles.add(newObstacle);
                obstacleSpawnTimer = random.nextFloat() * 2 + 1.5f; // Reset spawn timer between 1.5 and 3.5 seconds
            }
        }

        game.batch.begin();

        // Draw background
        game.batch.draw(backgroundTexture, backgroundX, 0, backgroundTexture.getWidth() * scale, Gdx.graphics.getHeight());
        game.batch.draw(backgroundTexture, backgroundX + backgroundTexture.getWidth() * scale, 0, backgroundTexture.getWidth() * scale, Gdx.graphics.getHeight());

        // Render player and obstacles
        player.render(game.batch);
        for (Obstacle obstacle : obstacles) {
            obstacle.render(game.batch);
        }

        game.batch.end();
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        player.dispose();
        for (Obstacle obstacle : obstacles) {
            obstacle.dispose();
        }
    }

    @Override
    public void show() {}

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
}
