package com.mcristoni.autoinstrucional;

import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;

public class Sprite {
    public RectF rect = new RectF();
    public float dx = 0;
    public float dy = 0;
    public float radius = 0;
    public Paint paint = new Paint();
    public Paint paintName = new Paint();
    public float textSize = 30f;

    public Sprite() {
    }

    /* Constructs a sprite of the given location and size. */
    public Sprite(float x, float y, float width, float height, int[] colors) {
        setLocation(x, y);
        radius = width;
        setSize(width, height);

        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setARGB(colors[0], colors[1], colors[2], colors[3]);

        paintName.setARGB(255, 255, 255, 255);
        paintName.setTextSize(textSize);
        paintName.setTextAlign(Paint.Align.CENTER);
        paintName.setTypeface(Typeface.DEFAULT_BOLD);
    }

    /* Tells the sprite to move itself by its current velocity dx,dy. */
    public void move() {
        rect.offset(dx, dy);
    }

    /* Stops the sprite from moving by setting its velocity to 0,0. */
    public void stopMoving() {
        setVelocity(0, 0);
    }

    /* Sets the sprite's x,y location on screen to be the given values. */
    public void setLocation(float x, float y) {
        rect.offsetTo(x, y);
    }

    /* Sets the sprite's size to be the given values. */
    public void setSize(float width, float height) {
        rect.right = rect.left + width;
        rect.bottom = rect.top + height;
    }

    /* Sets the sprites dx,dy velocity to be the given values. */
    public void setVelocity(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public float getRadius() {
        return radius;
    }
}