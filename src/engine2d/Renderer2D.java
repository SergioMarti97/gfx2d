package engine2d;

import engine.GameContainer;
import engine.gfx.Renderer;
import engine.gfx.images.Image;
import engine.vectors.points2d.Vec2df;
import engine.vectors.points3d.Vec3df;
import shapes.Shape;

import java.util.ArrayList;

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
     * @param transform the transformation for the image
     */
    public void drawImage(Image image, Transform2D transform) {
        if ( image == null ) {
            return;
        }

        Vec2df end = new Vec2df();
        Vec2df start;
        Vec2df pos = new Vec2df();

        start = transform.forward(0.0f, 0.0f);
        pos.setX(start.getX());
        pos.setY(start.getY());

        start.setX(Math.min(start.getX(), pos.getX()));
        start.setY(Math.min(start.getY(), pos.getY()));
        end.setX(Math.max(end.getX(), pos.getX()));
        end.setY(Math.max(end.getY(), pos.getY()));

        pos = transform.forward(image.getW(), image.getH());
        start.setX(Math.min(start.getX(), pos.getX()));
        start.setY(Math.min(start.getY(), pos.getY()));
        end.setX(Math.max(end.getX(), pos.getX()));
        end.setY(Math.max(end.getY(), pos.getY()));

        pos = transform.forward(0.0f, image.getH());
        start.setX(Math.min(start.getX(), pos.getX()));
        start.setY(Math.min(start.getY(), pos.getY()));
        end.setX(Math.max(end.getX(), pos.getX()));
        end.setY(Math.max(end.getY(), pos.getY()));

        pos = transform.forward(image.getW(), 0.0f);
        start.setX(Math.min(start.getX(), pos.getX()));
        start.setY(Math.min(start.getY(), pos.getY()));
        end.setX(Math.max(end.getX(), pos.getX()));
        end.setY(Math.max(end.getY(), pos.getY()));

        transform.invert();

        if ( end.getX() < start.getX() ) {
            float temp = end.getX();
            end.setX(start.getX());
            start.setX(temp);
        }
        if ( end.getY() < start.getY() ) {
            float temp = end.getY();
            end.setY(start.getY());
            start.setY(temp);
        }

        for ( float i = start.getX(); i < end.getX(); i++ ) {
            for ( float j = start.getY(); j < end.getY(); j++ ) {
                Vec2df output = transform.backward(i, j);
                if ( output.getX() >= 0 && output.getX() < image.getW() && output.getY() >= 0 && output.getY() < image.getH() ) {
                    int value;
                    try {
                        value = image.getPixel((int) (output.getX() + 0.5f), (int) (output.getY() + 0.5f));
                    } catch ( ArrayIndexOutOfBoundsException e ) {
                        value = 0x00000000;
                    }
                    setPixel((int) i, (int) j, value);
                }
            }
        }
        
    }

    private Vec2df matrixMultiplyVector(Mat3x3 m, Vec2df i) {
        float x = i.getX() * m.getM()[0][0] + i.getY() * m.getM()[1][0] + m.getM()[2][0];
        float y = i.getX() * m.getM()[0][1] + i.getY() * m.getM()[1][1] + m.getM()[2][1];
        float z = i.getX() * m.getM()[0][2] + i.getY() * m.getM()[1][2] + m.getM()[2][2];

        if ( z != 0 ) {
            x /= z;
            y /= z;
        }

        return new Vec2df(x, y);
    }

    public void drawShape(Shape shape, Transform2D transform) {
        ArrayList<Vec2df> transformedPoints = new ArrayList<>();
        for ( Vec2df point : shape.getPoints() ) {
            transformedPoints.add(transform.forward(point.getX(), point.getY()));
        }

        int i;
        for ( i = 0; i < transformedPoints.size() - 1; i++ ) {
            drawLine(
                    (int)transformedPoints.get(i).getX(),
                    (int)transformedPoints.get(i).getY(),
                    (int)transformedPoints.get(i + 1).getX(),
                    (int)transformedPoints.get(i + 1).getY(),
                    0xffffffff);
        }
        drawLine(
                (int)transformedPoints.get(i).getX(),
                (int)transformedPoints.get(i).getY(),
                (int)transformedPoints.get(0).getX(),
                (int)transformedPoints.get(0).getY(),
                0xffffffff);
    }

}
