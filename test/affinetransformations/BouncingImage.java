package affinetransformations;

import engine.gfx.images.Image;
import engine2d.Transform2D;
import engine2d.Renderer2D;

public class BouncingImage {

    private Image image;

    private float posX;

    private float posY;

    private float velX;

    private float velY;

    private float rotationAngle;

    private float rotationVel;

    private float scale;

    public BouncingImage(Image image, int posX, int posY) {
        this.image = image;
        this.posX = posX;
        this.posY = posY;
        velX = getRandomFloatBetweenRange(50, -50);
        velY = getRandomFloatBetweenRange(50, -50);
        rotationAngle = getRandomFloatBetweenRange((float)(Math.PI * 2.0f), 0);
        rotationVel = getRandomFloatBetweenRange(2, -2);
        scale = getRandomFloatBetweenRange(1.0f, 0.15f);
    }

    private float getRandomFloatBetweenRange(float max, float min) {
        return (float) ((Math.random() * ((max - min) + 1)) + min);
    }

    public void updatePosition(float dt) {
        posX += velX * dt;
        posY += velY * dt;
        rotationAngle += (rotationVel * dt) / 2.0f;
    }

    public void drawYourself(Renderer2D r) {
        Transform2D transform = new Transform2D();
        transform.scale(scale, scale).translate(- image.getW() / 2.0f, - image.getH() / 2.0f).rotate(rotationAngle).translate(posX, posY);
        r.drawImage(image, transform);
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

}
