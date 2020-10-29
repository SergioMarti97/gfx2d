package affinetransformations;

import engine.AbstractGame;
import engine.GameContainer;
import engine.gfx.Renderer;
import engine.gfx.images.ImageTile;
import engine2d.Renderer2D;

import java.util.ArrayList;

public class TestAffineTransformations extends AbstractGame {

    private ArrayList<BouncingImage> images;

    private ImageTile imageTile;

    private Renderer2D renderer2D;

    private TestAffineTransformations(String title) {
        super(title);
    }

    @Override
    public void initialize(GameContainer gameContainer) {
        renderer2D = new Renderer2D(gameContainer);
        images = new ArrayList<>();
        imageTile = new ImageTile("/images.png", 64, 64);
    }

    private int getRandomIntBetweenRange(int max, int min) {
        return (int) ((Math.random() * ((max - min) + 1)) + min);
    }

    private void updateBouncingPositions(GameContainer gc, float dt) {
        for ( BouncingImage image : images ) {
            image.updatePosition(dt);

            float posX = image.getPosX();
            float posY = image.getPosY();

            if (posX < 0) {
                posX += (float) (gc.getWidth());
                image.setPosX(posX);
            }

            if (posX >= gc.getWidth()) {
                posX -= (float) (gc.getWidth());
                image.setPosX(posX);
            }

            if (posY < 0) {
                posY += (float) (gc.getHeight());
                image.setPosY(posY);
            }

            if (posY >= gc.getHeight()) {
                posY -= (float) (gc.getHeight());
                image.setPosY(posY);
            }
        }
    }

    @Override
    public void update(GameContainer gameContainer, float v) {
        if ( gameContainer.getInput().isButtonHeld(1) ) {
            images.add(
                    new BouncingImage(
                            imageTile.getTileImage(
                                    getRandomIntBetweenRange(5, 0),
                                    getRandomIntBetweenRange(9, 0)
                            ),
                            gameContainer.getInput().getMouseX(),
                            gameContainer.getInput().getMouseY()
                    )
            );
        }
        updateBouncingPositions(gameContainer, v);
    }

    @Override
    public void render(GameContainer gameContainer, Renderer renderer) {
        renderer.clear(0xffffffff);
        for ( BouncingImage image : images ) {
            image.drawYourself(renderer2D);
        }
        renderer.drawText(String.format("Images: %d", images.size()), 10, 10, 0xff000000);
    }

    public static void main(String[] args) {
        GameContainer gc = new GameContainer(new TestAffineTransformations("Test affine transformations"));
        gc.start();
    }

}
