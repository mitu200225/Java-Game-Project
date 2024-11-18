package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Player {
    private Texture[] runFrames;
    private Texture[] jumpFrames;
    private Animation<TextureRegion> runAnimation;
    private Animation<TextureRegion> jumpAnimation;
    private float stateTime;
    private float playerX, playerY;
    private float playerScale;
    private boolean isJumping;
    private float jumpSpeed;
    private float gravity;
    private Rectangle boundingBox;

    public void initialize() {
        // Load run frames
        runFrames = new Texture[9];
        for (int i = 0; i < 9; i++) {
            runFrames[i] = new Texture(Gdx.files.internal("Run__00" + i + ".png"));
        }

        // Load jump frames
        jumpFrames = new Texture[10];
        for (int i = 0; i < 10; i++) {
            jumpFrames[i] = new Texture(Gdx.files.internal("Jump__00" + i + ".png"));
        }

        // Create run animation
        TextureRegion[] runRegions = new TextureRegion[runFrames.length];
        for (int i = 0; i < runFrames.length; i++) {
            runRegions[i] = new TextureRegion(runFrames[i]);
        }
        runAnimation = new Animation<>(0.1f, runRegions);

        // Create jump animation
        TextureRegion[] jumpRegions = new TextureRegion[jumpFrames.length];
        for (int i = 0; i < jumpFrames.length; i++) {
            jumpRegions[i] = new TextureRegion(jumpFrames[i]);
        }
        jumpAnimation = new Animation<>(0.1f, jumpRegions);

        stateTime = 0f;
        playerX = 50;
        playerY = 50;
        playerScale = 0.4f;

        isJumping = false;
        jumpSpeed = 0;
        gravity = -0.6f;

        boundingBox = new Rectangle(playerX, playerY, runFrames[0].getWidth() * playerScale, runFrames[0].getHeight() * playerScale);
    }

    public void update(float delta) {
        stateTime += delta;

        if (Gdx.input.isTouched() && !isJumping) {
            jumpSpeed = 15;
            isJumping = true;
        }

        if (isJumping) {
            playerY += jumpSpeed;
            jumpSpeed += gravity;

            if (playerY < 50) {
                playerY = 50;
                isJumping = false;
                jumpSpeed = 0;
                stateTime = 0; // Reset stateTime when landing
            }
        }

        boundingBox.setPosition(playerX, playerY);
    }

    public void render(SpriteBatch batch) {
        TextureRegion currentFrame;
        if (isJumping) {
            currentFrame = jumpAnimation.getKeyFrame(stateTime, true);
        } else {
            currentFrame = runAnimation.getKeyFrame(stateTime, true);
        }
        batch.draw(currentFrame, playerX, playerY, currentFrame.getRegionWidth() * playerScale, currentFrame.getRegionHeight() * playerScale);
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public void dispose() {
        for (Texture frame : runFrames) {
            frame.dispose();
        }
        for (Texture frame : jumpFrames) {
            frame.dispose();
        }
    }
}
