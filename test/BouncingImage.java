import engine.gfx.images.Image;
import engine2d.Mat3x3;
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
        Mat3x3 matRot = new Mat3x3();
        Mat3x3 matScale = new Mat3x3();
        Mat3x3 matRotScale;
        Mat3x3 offsetImage = new Mat3x3();
        Mat3x3 offsetScreen = new Mat3x3();
        Mat3x3 scaledOffsetImage;
        Mat3x3 transform;

        matScale.setAsScale(scale, scale);
        matRot.setAsRotate(rotationAngle);
        matRotScale = Transform2D.multiply(matRot, matScale);
        offsetImage.setAsTranslate(- image.getW() / 2.0f, - image.getH() / 2.0f);
        scaledOffsetImage = Transform2D.multiply(offsetImage, matRotScale);
        offsetScreen.setAsTranslate(posX, posY);
        transform = Transform2D.multiply(scaledOffsetImage, offsetScreen);
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
