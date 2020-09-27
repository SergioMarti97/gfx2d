package shapes2d;

import engine.gfx.Renderer;
import engine.vectors.points2d.Vec2df;

public class Triangle2D extends Polygon2D {

    public Triangle2D(float posX, float posY, float size, int color) {
        super(posX, posY, size, 3, color);
    }

    public Triangle2D(float posX, float posY, float x0, float y0, float x1, float y1, float x2, float y2, int color) {
        super(posX, posY, 0, 3, color);
        pShape.get(0).set(new Vec2df(x0, y0));
        pShape.get(1).set(new Vec2df(x1, y1));
        pShape.get(2).set(new Vec2df(x2, y2));
        pFinal.get(0).set(new Vec2df(x0, y0));
        pFinal.get(1).set(new Vec2df(x1, y1));
        pFinal.get(2).set(new Vec2df(x2, y2));
        normalizePShape();
        calculateTheoreticalCenter();
        Vec2df max = new Vec2df(pShape.get(0).getX(), pShape.get(0).getY());
        for ( int i = 1; i < pShape.size(); i++ ) {
            max.setX(Math.max(max.getX(), pShape.get(i).getX()));
            max.setY(Math.max(max.getY(), pShape.get(i).getY()));
        }
        max.sub(theoreticalCenter);
        size = max.mag();
        calculateTheoreticalCenter();
        rotateScaleOffsetPoints();
    }

    public Triangle2D(float posX, float posY, float size, float x0, float y0, float x1, float y1, float x2, float y2, int color) {
        super(posX, posY, 0, 3, color);
        pShape.get(0).set(new Vec2df(x0, y0));
        pShape.get(1).set(new Vec2df(x1, y1));
        pShape.get(2).set(new Vec2df(x2, y2));
        pFinal.get(0).set(new Vec2df(x0, y0));
        pFinal.get(1).set(new Vec2df(x1, y1));
        pFinal.get(2).set(new Vec2df(x2, y2));
        this.size = size;
        normalizePShape();
        calculateTheoreticalCenter();
        rotateScaleOffsetPoints();
    }

    @Override
    public void drawYourSelf(Renderer r) {
        switch ( wayToRender ) {
            case WIRE: default:
                r.drawTriangle(
                        (int)(pFinal.get(0).getX()), (int)(pFinal.get(0).getY()),
                        (int)(pFinal.get(1).getX()), (int)(pFinal.get(1).getY()),
                        (int)(pFinal.get(2).getX()), (int)(pFinal.get(2).getY()),
                        color
                );
                break;
            case SOLID:
                r.drawFillTriangle(
                        (int)(pFinal.get(0).getX()), (int)(pFinal.get(0).getY()),
                        (int)(pFinal.get(1).getX()), (int)(pFinal.get(1).getY()),
                        (int)(pFinal.get(2).getX()), (int)(pFinal.get(2).getY()),
                        color
                );
                break;
            case BLACKBOARD:
                r.drawLine(
                        (int)(pFinal.get(0).getX()), (int)(pFinal.get(0).getY()),
                        (int)(pFinal.get(1).getX()), (int)(pFinal.get(1).getY()),
                        0xffff0000 // Rojo
                );
                r.drawLine(
                        (int)(pFinal.get(0).getX()), (int)(pFinal.get(0).getY()),
                        (int)(pFinal.get(2).getX()), (int)(pFinal.get(2).getY()),
                        0xff0000ff // Azul
                );
                r.drawLine(
                        (int)(pFinal.get(1).getX()), (int)(pFinal.get(1).getY()),
                        (int)(pFinal.get(2).getX()), (int)(pFinal.get(2).getY()),
                        0xffffff00 // Amarillo
                );
                r.drawCircle((int)(posX), (int)(posY), 2, 0xffffffff);
                break;
            case BLUEPRINT:
                r.drawTriangle(
                        (int)(pFinal.get(0).getX()), (int)(pFinal.get(0).getY()),
                        (int)(pFinal.get(1).getX()), (int)(pFinal.get(1).getY()),
                        (int)(pFinal.get(2).getX()), (int)(pFinal.get(2).getY()),
                        0xffffffff
                );
                r.drawCircle((int)(posX), (int)(posY), 2, 0xffffffff);
                break;
        }
        super.drawYourSelf(r);
    }

    /**
     * En este caso, para saber si un punto esta dentro de un triangulo, es un poco complicado.
     * He utilizado la formula de la siguiente fuente:
     * https://huse360.home.blog/2019/12/14/como-saber-si-un-punto-esta-dentro-de-un-triangulo/
     */
    @Override
    public boolean isPointInside(float x, float y) {
        //Sea d el segmento ab.
        Vec2df d = new Vec2df(pFinal.get(1).getX() - pFinal.get(0).getX(), pFinal.get(1).getY() - pFinal.get(0).getY());

        //Sea e el segmento ac.
        Vec2df e = new Vec2df(pFinal.get(2).getX() - pFinal.get(0).getX(), pFinal.get(2).getY() - pFinal.get(0).getY());

        //Variable de ponderación a~b
        float w1 = (e.getX() * (pFinal.get(0).getY() - y) + e.getY() * (x - pFinal.get(0).getX())) / (d.getX() * e.getY() - d.getY() * e.getX());

        //Variable de ponderación a~c
        float w2 = (y - pFinal.get(0).getY() - w1 * d.getY()) / e.getY();

        //El punto p se encuentra dentro del triángulo
        // si se cumplen las 3 condiciones:
        return (w1 >= 0.0) && (w2 >= 0.0) && ((w1 + w2) <= 1.0);
    }

}
