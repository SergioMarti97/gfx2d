package text;

import engine.AbstractGame;
import engine.GameContainer;
import engine.gfx.Renderer;
import engine.gfx.images.Image;
import engine.gfx.images.ImageTile;
import engine2d.Renderer2D;
import engine2d.Transform2D;

/**
 * This is a test of drawing text with transformations
 */
public class TestDrawingText extends AbstractGame {

    private Renderer2D renderer2D;

    private ImageTile imageTile;

    private Image image;

    private Image imageTransformed;

    private float rotation = 0;

    private TestDrawingText(String title) {
        super(title);
    }

    @Override
    public void initialize(GameContainer gameContainer) {
        renderer2D = new Renderer2D(gameContainer);
        imageTile = new ImageTile("/images.png", 64, 64);
        image = imageTile.getTileImage(0, 4);
        //Transform2D transform2D = new Transform2D();
        //transform2D.rotate((float)Math.PI / 2.0f);
        //imageTransformed = renderer2D.transformImage(image, transform2D);
    }

    @Override
    public void update(GameContainer gameContainer, float v) {
        rotation += 0.5 * v;
    }

    @Override
    public void render(GameContainer gameContainer, Renderer renderer) {
        Transform2D transform2D = new Transform2D();
        transform2D.translate(-image.getW() / 2.0f, -image.getH() / 2.0f).rotate(rotation).translate(200, 200);
        renderer2D.drawImage(image, transform2D);
        transform2D.reset();
        transform2D.translate(-image.getW() / 2.0f, -image.getH() / 2.0f).rotate(rotation);
        imageTransformed = renderer2D.transformImage(image, transform2D);
        renderer.drawImage(imageTransformed, 500, 200);
    }

    public static void main(String[] args) {
        GameContainer gc = new GameContainer(new TestDrawingText("Test drawing text"));
        gc.start();
    }

}
