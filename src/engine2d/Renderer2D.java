package engine2d;

import engine.GameContainer;
import engine.gfx.Renderer;
import engine.gfx.images.Image;
import engine.vectors.points2d.Vec2df;

/**
 * This class works as an extension from the Renderer
 * class of the engine. It adds the methods to draw
 * an image affine transformations
 *
 * @class Renderer2D
 * @author Sergio Martí Torregrosa
 * @date 27/09/2020
 */
public class Renderer2D extends Renderer {

    /**
     * The constructor
     *
     * @param gc the game container object
     */
    public Renderer2D(GameContainer gc) {
        super(gc);
    }

    /**
     * This method allows draw an image with an affine
     * transformation
     *
     * @param image the image
     * @param transformation the transformation for the image
     */
    public void drawImage(Image image, Mat3x3 transformation) {
        Mat3x3 transformationInv;
        transformationInv = Transform2D.invert(transformation);

        Vec2df p = Transform2D.forward(transformation, 0.0f, 0.0f);
        Vec2df end = new Vec2df();
        Vec2df start = new Vec2df();

        start.set(p);
        end.set(p);

        p = Transform2D.forward(transformation, (float)(image.getW()), (float)(image.getH()));
        start.setX(Math.min(start.getX(), p.getX()));
        start.setY(Math.min(start.getY(), p.getY()));
        end.setX(Math.max(end.getX(), p.getX()));
        end.setY(Math.max(end.getY(), p.getY()));

        p = Transform2D.forward(transformation, 0.0f, (float)(image.getH()));
        start.setX(Math.min(start.getX(), p.getX()));
        start.setY(Math.min(start.getY(), p.getY()));
        end.setX(Math.max(end.getX(), p.getX()));
        end.setY(Math.max(end.getY(), p.getY()));

        p = Transform2D.forward(transformation, (float)(image.getW()), 0.0f);
        start.setX(Math.min(start.getX(), p.getX()));
        start.setY(Math.min(start.getY(), p.getY()));
        end.setX(Math.max(end.getX(), p.getX()));
        end.setY(Math.max(end.getY(), p.getY()));

        int pixel;
        Vec2df newPos;
        for ( int x = (int)start.getX(); x < (int)end.getX(); x++ ) {
            for ( int y = (int)start.getY(); y < (int)end.getY(); y++ ) {
                newPos = Transform2D.forward(transformationInv, (float)(x), (float)(y));
                if ( newPos.getX() >= 0 && newPos.getX() < image.getW() && newPos.getY() >= 0 && newPos.getY() < image.getH() ) {
                    try {
                        pixel = image.getPixel((int) (newPos.getX() + 0.5f), (int) (newPos.getY() + 0.5f));
                        setPixel(x, y, pixel);
                    } catch ( ArrayIndexOutOfBoundsException e ) {
                        setPixel(x, y, 0x00000000);
                    }
                }
            }
        }
    }

}
