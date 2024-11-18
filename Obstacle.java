package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import java.util.Random;

public class Obstacle {
    private Texture texture;
    private float x, y;
    private float speed;
    private static final float SCALE = 0.2f; // Smaller scale for the obstacle
    private Rectangle boundingBox;
    private float leftReduction; // Reduce left side of bounding box
    private float rightReduction; // Reduce right side of bounding box
    private float topReduction;   // Reduce top side of bounding box

    public Obstacle(String textureFile) {
        texture = new Texture(Gdx.files.internal(textureFile));
        Random random = new Random();
        x = Gdx.graphics.getWidth();
        y = 50; // Ground level

        speed = 350 + random.nextInt(100); // Random speed variation
        
        //reduced by 30%
        leftReduction = texture.getWidth() * SCALE * 0.30f;  
        rightReduction = texture.getWidth() * SCALE * 0.30f; 
        topReduction = texture.getHeight() * SCALE * 0.30f;  

        // Create bounding box with left, right, and top side reductions
        boundingBox = new Rectangle(
            x + leftReduction, // Move bounding box right by the left reduction
            y,                 // The bottom of the obstacle is at the ground level (y = 50)
            texture.getWidth() * SCALE - leftReduction - rightReduction, // Reduce width on both sides
            texture.getHeight() * SCALE - topReduction // Reduce the height from the top
        );
    }

    public void update(float delta) {
        x -= speed * delta;
        // Update bounding box position with the left side reduction
        boundingBox.setPosition(x + leftReduction, y);
    }

    public Rectangle getHitBox() {
        return boundingBox;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, texture.getWidth() * SCALE, texture.getHeight() * SCALE);
    }

    public void dispose() {
        texture.dispose();
    }
}
