package com.denizdogan.survivorbird;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;

public class GameActor {

    private int pieceID;
    private Texture texture;
    private float startX;
    private float startY;
    public float width;
    public float height;
    private float speed;
    private Circle circle;
    private boolean isScored;
    public GameActor(){

    }

    public GameActor(int pieceID, Texture texture, float startX, float startY, float width, float height, float speed, Circle circle, boolean isScored) {
        this.pieceID = pieceID;
        this.texture = texture;
        this.startX = startX;
        this.startY = startY;
        this.speed = speed;
        this.circle = circle;
        this.isScored = isScored;
        this.width = width;
        this.height = height;
    }

    public int getPieceID() {
        return pieceID;
    }

    public void setPieceID(int pieceID) {
        this.pieceID = pieceID;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public boolean isScored() {
        return isScored;
    }

    public void setScored(boolean scored) {
        isScored = scored;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public Texture getTexture() {
        return texture;
    }

    public float getStartX() {
        return startX;
    }

    public float getStartY() {
        return startY;
    }

    public float getSpeed() {
        return speed;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setStartX(float startX) {
        this.startX = startX;
    }

    public void setStartY(float startY) {
        this.startY = startY;
    }
    public void setSpeed(float speed) {
        this.speed = speed;
    }


}
