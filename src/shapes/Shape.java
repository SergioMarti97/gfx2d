package shapes;

import engine.vectors.points2d.Vec2df;

import java.util.ArrayList;

/**
 * This is a representation of a shape
 *
 * @class Shape
 * @autor Sergio Martí Torregrosa
 * @date 29/09/2020
 */
public class Shape {

    private ArrayList<Vec2df> points;

    private int color = 0xffffffff;

    public Shape() {
        points = new ArrayList<>();
        points.add(new Vec2df(0.0f, 0.0f));
        points.add(new Vec2df(1.0f, 0.0f));
        points.add(new Vec2df(1.0f, 1.0f));
        points.add(new Vec2df(0.0f, 1.0f));
    }

    public ArrayList<Vec2df> getPoints() {
        return points;
    }

    public int getColor() {
        return color;
    }

}
