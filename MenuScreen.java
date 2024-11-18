package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuScreen implements Screen {
    private final MyGame game;
    private Texture menuBackgroundTexture;
    private Texture startButtonTexture;
    private Texture exitButtonTexture;
    private float startButtonX, startButtonY;
    private float exitButtonX, exitButtonY;

    public MenuScreen(MyGame game) {
        this.game = game;
        menuBackgroundTexture = new Texture(Gdx.files.internal("menuBackground.png"));
        startButtonTexture = new Texture(Gdx.files.internal("startButton.png"));
        exitButtonTexture = new Texture(Gdx.files.internal("exitButton.png"));

        startButtonX = (Gdx.graphics.getWidth() - startButtonTexture.getWidth()) / 2;
        startButtonY = (Gdx.graphics.getHeight() - startButtonTexture.getHeight()) / 2 + 100;
        exitButtonX = (Gdx.graphics.getWidth() - exitButtonTexture.getWidth()) / 2;
        exitButtonY = (Gdx.graphics.getHeight() - exitButtonTexture.getHeight()) / 2 - 100;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(menuBackgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.draw(startButtonTexture, startButtonX, startButtonY);
        game.batch.draw(exitButtonTexture, exitButtonX, exitButtonY);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (touchX > startButtonX && touchX < startButtonX + startButtonTexture.getWidth() &&
                touchY > startButtonY && touchY < startButtonY + startButtonTexture.getHeight()) {
                game.setScreen(new GameScreen(game));
            } else if (touchX > exitButtonX && touchX < exitButtonX + exitButtonTexture.getWidth() &&
                touchY > exitButtonY && touchY < exitButtonY + exitButtonTexture.getHeight()) {
                Gdx.app.exit();
            }
        }
    }

    @Override
    public void dispose() {
        menuBackgroundTexture.dispose();
        startButtonTexture.dispose();
        exitButtonTexture.dispose();
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
