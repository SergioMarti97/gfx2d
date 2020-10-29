package engine2d;

import engine.GameContainer;
import engine.gfx.Renderer;
import engine.gfx.font.Font;
import engine.gfx.images.Image;
import engine.vectors.points2d.Vec2df;
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
     * @param gc the game container object
     */
    public Renderer2D(GameContainer gc) {
        super(gc);
    }

    public Image transformImage(Image image, Transform2D transform) {
        assert image != null;

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

        int width = (int)(end.getX() - start.getX());
        int height = (int)(end.getY() - start.getY());
        int[] newImage = new int[width * height];

        for ( float i = start.getX(); i < end.getX(); i++ ) {
            for ( float j = start.getY(); j < end.getY(); j++ ) {
                Vec2df output = transform.backward(i, j);
                int value = 0xffff0000;
                if ( output.getX() >= 0 && output.getX() < image.getW() && output.getY() >= 0 && output.getY() < image.getH() ) {
                    int finalX = (int) (output.getX() + 0.5f);
                    int finalY = (int) (output.getY() + 0.5f);
                    if ( finalX < 0 ) {
                        finalX = 0;
                    }
                    if ( finalY < 0 ) {
                        finalY = 0;
                    }
                    if ( finalX >= 64 ) {
                        finalX = 63;
                    }
                    if ( finalY >= 64 ) {
                        finalY = 63;
                    }
                    value = image.getPixel(finalX, finalY);
                    /*try {
                        value = image.getPixel((int) (output.getX() + 0.5f), (int) (output.getY() + 0.5f));
                    } catch ( ArrayIndexOutOfBoundsException e ) {
                        System.out.println(
                                String.format("position: %dx : %dy out of %d x %d",
                                        (int) (output.getX() + 0.5f),
                                        (int) (output.getY() + 0.5f),
                                        image.getW(),
                                        image.getH()
                                )
                        );
                    }*/
                }
                int index = ((int) (i) + (int) (j) * width);
                if ( index < 0 ){
                    index = 0;
                }
                if ( index >= (width * height) ){
                    index = (width * height) - 1;
                }
                newImage[index] = value;
            }
        }

        return new Image(newImage, width, height);
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
                    /*int value;
                    try {
                        value = image.getPixel((int) (output.getX() + 0.5f), (int) (output.getY() + 0.5f));
                    } catch ( ArrayIndexOutOfBoundsException e ) {
                        value = 0x00000000;
                    }*/
                    int finalX = (int) (output.getX() + 0.5f);
                    int finalY = (int) (output.getY() + 0.5f);
                    if ( finalX < 0 ) {
                        finalX = 0;
                    }
                    if ( finalY < 0 ) {
                        finalY = 0;
                    }
                    if ( finalX >= 64 ) {
                        finalX = 63;
                    }
                    if ( finalY >= 64 ) {
                        finalY = 63;
                    }
                    int value = image.getPixel(finalX, finalY);
                    setPixel((int) i, (int) j, value);
                }
            }
        }
        
    }

    private void drawCharacter(Image characterImage, Transform2D transform2D, int color) {
        drawImage(characterImage, transform2D);
    }

    public void drawText(String text, Transform2D transform2D, int offX, int offY, int color, Font font) {
        for ( int i = 0; i < text.length(); i++ ) {
            int unicode = text.codePointAt(i);
            transform2D.translate(i == 0 ? offX : font.getWidths()[unicode], i == 0 ? offY : 0);
            drawCharacter(font.getCharacterImage(unicode), transform2D, color);
        }
    }

    public void drawText(String text, Transform2D transform2D, int offX, int offY, int color) {
        drawText(text, transform2D, offX, offY, color, font);
    }

    /**
     * This method draws a really primitive way to draw a
     * shape as a wire
     * @param shape the shape to draw
     * @param transform the transformation
     * @param color the color to draw the shape
     */
    public void drawShape(Shape shape, Transform2D transform, int color) {
        ArrayList<Vec2df> transformedPoints = new ArrayList<>();
        for ( Vec2df point : shape.getPoints() ) {
            transformedPoints.add(transform.forward(point.getX(), point.getY()));
        }
        drawPolygon(transformedPoints, color);
    }

}
