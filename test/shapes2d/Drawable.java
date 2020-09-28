package shapes2d;

import engine.gfx.Renderer;

/**
 * This interface implements the <method>drawYourSelf</method> method. I know
 * must be implemented in all the classes that are going to be drawn in the
 * screen.
 *
 * @class: Drawable
 * @autor: Sergio Martí Torregrosa
 * @date: 2020-07-06
 */
public interface Drawable {

    /**
     * This method will have to be overwritten in all the classes to be drawn.
     * All drawing methods are in the <class> Renderer </class> class, therefore
     * this object is passed as a parameter.
     *
     * @param r It is the <class> Renderer </class> object that is used to use all the drawn methods.
     */
    void drawYourSelf(Renderer r);

}
