package shapes2d;

/**
 * This interface implements the <method> isPointInside </method> method.
 * This method is used to see when a point is within
 * of an area. That way, you can tell if the mouse is inside
 * of a figure, and thus be able to be selected.
 *
 * As it is an interface, you cannot add the fields of
 * <field> isSelected </field>, which would be even more correct and would work
 * to recycle the code used to select objects
 * that are printed by screen. To do this, you should create
 * an abstract class.
 *
 * Examples:
 * To know if the point (x, y) is inside the circle
 * with origin at the point (ox, oy) and radius "r" the
 * following formula:
 *
 * return (ox - x) * (ox - x) + (oy - y) * (oy - y) <(r * r)
 *
 * This formula comes from applying Pythagoras. No need to use
 * the square root since this operation consumes many resources of the
 * computer. It is better to compare the areas, it is the same in the end.
 *
 * However, to check that a point is within
 * a triangle, the algorithm is much more complicated.
 *
 * @see "https://huse360.home.blog/2019/12/14/como-saber-si-un-punto-esta-dentro-de-un-triangulo/"
 *
 * @class: SelecteableByMouse
 * @autor: Sergio Martí Torregrosa
 * @date: 2020-07-06
 */
public interface SelectableByMouse {

    /**
     * This function is used to find out if a point (x, y) is within
     * of a figure. Normally, this point is the "x" and "y" coordinates of the
     * mouse.
     *
     * @param x The x position of the point to be tested if it is within the area.
     * @param y The position y of the point to be tested if it is within the area.
     * @return Returns true or false, depending on whether it is outside or
     * within the area the point.
     */
    boolean isPointInside(float x, float y);

}
