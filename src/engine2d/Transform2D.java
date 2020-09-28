package engine2d;

import engine.vectors.points2d.Vec2df;

/**
 * This class is a representation of an affine transformation,
 * used to rotate, scale offset and shear space
 *
 * It's copied from the class Transform2D from the class GFX2D
 * (olcPGEX_Graphics2D.h) made by Javidx9. The code can be seen
 * in this GitHub repository:
 * https://github.com/OneLoneCoder/olcPixelGameEngine/blob/master/Extensions/olcPGEX_Graphics2D.h
 *
 * In this approach, I have made the implementation in Java
 *
 * @class Transform2D
 * @author Sergio Martí Torregrosa. sMartiTo
 * @date 28/09/2020
 */
public class Transform2D {

    /**
     * The size of the matrix array
     * It's need four matrix in this approach
     */
    private final int NUM_MATRIX = 4;

    /**
     * Array made of matrix
     * The matrix 0 and 1 are used as swaps in Transform accumulation
     * The matrix 2 is a cache matrix to hold the immediate transform operation
     * The matrix 3 is a cache matrix to hold the inverted transform
     */
    private Mat3x3[] mat3x3s;

    /**
     * The index of target matrix
     */
    private int targetMatrix;

    /**
     * The index of source matrix
     */
    private int sourceMatrix;

    /**
     * dirty flag for reduce the processing cost
     */
    private boolean isDirty;

    /**
     * Constructor
     */
    public Transform2D() {
        mat3x3s = new Mat3x3[NUM_MATRIX];
        for ( int i = 0; i < NUM_MATRIX; i++ ) {
            mat3x3s[i] = new Mat3x3();
        }
        reset();
    }

    private Mat3x3 getIdentityMatrix() {
        float[][] mat = new float[3][3];

        mat[0][0] = 1.0f;
        mat[1][0] = 0.0f;
        mat[2][0] = 0.0f;

        mat[0][1] = 0.0f;
        mat[1][1] = 1.0f;
        mat[2][1] = 0.0f;

        mat[0][2] = 0.0f;
        mat[1][2] = 0.0f;
        mat[2][2] = 1.0f;

        return new Mat3x3(mat);
    }

    /**
     * This method resets the object transform2D
     *
     * Sets the target and source matrix identifiers
     * to 0 and 1 respectively, the dirty flag to true,
     * and sets the 0 and 1 matrix to the identity
     * matrix
     */
    public void reset() {
        targetMatrix = 0;
        sourceMatrix = 1;
        isDirty = true;

        mat3x3s[0] = getIdentityMatrix();
        mat3x3s[1] = getIdentityMatrix();
    }

    /**
     * This method multiplies the target matrix (0) and the
     * source matrix (1)
     * Any transform multiply dirties the inversion
     */
    private void multiply() {
        int cols = mat3x3s[2].getNUM_COLS();
        int rows = mat3x3s[2].getNUM_ROWS();
        for ( int c = 0; c < cols; c++ ) {
            for ( int r = 0; r < rows; r++ ) {
                mat3x3s[targetMatrix].getM()[c][r] =
                        mat3x3s[2].getM()[0][r] * mat3x3s[sourceMatrix].getM()[c][0] +
                                mat3x3s[2].getM()[1][r] * mat3x3s[sourceMatrix].getM()[c][1] +
                                mat3x3s[2].getM()[2][r] * mat3x3s[sourceMatrix].getM()[c][2];
            }
        }

        int temp = targetMatrix;
        targetMatrix = sourceMatrix;
        sourceMatrix = temp;
        isDirty = true;
    }

    /**
     * This method sets the matrix with index 2 to a
     * rotation matrix
     * @param theta the angle of rotation
     * @return this for accumulative operations
     */
    public Transform2D rotate(float theta) {
        mat3x3s[2].getM()[0][0] = (float) Math.cos(theta);
        mat3x3s[2].getM()[1][0] = (float) Math.sin(theta);
        mat3x3s[2].getM()[2][0] = 0.0f;

        mat3x3s[2].getM()[0][1] = (float) - Math.sin(theta);
        mat3x3s[2].getM()[1][1] = (float) Math.cos(theta);
        mat3x3s[2].getM()[2][1] = 0.0f;

        mat3x3s[2].getM()[0][2] = 0.0f;
        mat3x3s[2].getM()[1][2] = 0.0f;
        mat3x3s[2].getM()[2][2] = 1.0f;

        multiply();

        return this;
    }

    /**
     * This methods sets the matrix with index 2 to a
     * scale matrix
     * @param scaleX the scale factor in x axis
     * @param scaleY the scale factor in y axis
     * @return this for accumulative operations
     */
    public Transform2D scale(float scaleX, float scaleY) {
        mat3x3s[2].getM()[0][0] = scaleX;
        mat3x3s[2].getM()[1][0] = 0.0f;
        mat3x3s[2].getM()[2][0] = 0.0f;

        mat3x3s[2].getM()[0][1] = 0.0f;
        mat3x3s[2].getM()[1][1] = scaleY;
        mat3x3s[2].getM()[2][1] = 0.0f;

        mat3x3s[2].getM()[0][2] = 0.0f;
        mat3x3s[2].getM()[1][2] = 0.0f;
        mat3x3s[2].getM()[2][2] = 1.0f;

        multiply();

        return this;
    }

    /**
     * This methods sets the matrix with index 2 to a
     * shear matrix
     * @param shearX the shear in x axis
     * @param shearY the shear in y axis
     * @return this for accumulative operations
     */
    public Transform2D shear(float shearX, float shearY) {
        mat3x3s[2].getM()[0][0] = 1.0f;
        mat3x3s[2].getM()[1][0] = shearX;
        mat3x3s[2].getM()[2][0] = 0.0f;

        mat3x3s[2].getM()[0][1] = shearY;
        mat3x3s[2].getM()[1][1] = 1.0f;
        mat3x3s[2].getM()[2][1] = 0.0f;

        mat3x3s[2].getM()[0][2] = 0.0f;
        mat3x3s[2].getM()[1][2] = 0.0f;
        mat3x3s[2].getM()[2][2] = 1.0f;

        multiply();

        return this;
    }

    /**
     * This method sets the matrix with index 2 to a
     * translation matrix
     * @param offsetX the offset in x axis
     * @param offsetY the offset in y axis
     * @return this for accumulative operations
     */
    public Transform2D translate(float offsetX, float offsetY) {
        mat3x3s[2].getM()[0][0] = 1.0f;
        mat3x3s[2].getM()[1][0] = 0.0f;
        mat3x3s[2].getM()[2][0] = offsetX;

        mat3x3s[2].getM()[0][1] = 0.0f;
        mat3x3s[2].getM()[1][1] = 1.0f;
        mat3x3s[2].getM()[2][1] = offsetY;

        mat3x3s[2].getM()[0][2] = 0.0f;
        mat3x3s[2].getM()[1][2] = 0.0f;
        mat3x3s[2].getM()[2][2] = 1.0f;

        multiply();

        return this;
    }

    /**
     * This method sets the matrix with index 2 to a
     * perspective matrix
     * @param offsetX the offset in x axis
     * @param offsetY the offset in y axis
     * @return this for accumulative operations
     */
    public Transform2D perspective(float offsetX, float offsetY) {
        mat3x3s[2].getM()[0][0] = 1.0f;
        mat3x3s[2].getM()[1][0] = 0.0f;
        mat3x3s[2].getM()[2][0] = 0.0f;

        mat3x3s[2].getM()[0][1] = 0.0f;
        mat3x3s[2].getM()[1][1] = 1.0f;
        mat3x3s[2].getM()[2][1] = 0.0f;

        mat3x3s[2].getM()[0][2] = offsetX;
        mat3x3s[2].getM()[1][2] = offsetY;
        mat3x3s[2].getM()[2][2] = 1.0f;

        multiply();

        return this;
    }

    /**
     * The forward operation
     * @param inputX input x value
     * @param inputY input y value
     * @return return a @Vec2df object with the values of outputX and outputY
     */
    Vec2df forward(float inputX, float inputY) {
        float outX = inputX * mat3x3s[sourceMatrix].getM()[0][0] + inputY * mat3x3s[sourceMatrix].getM()[1][0] +
                mat3x3s[sourceMatrix].getM()[2][0];
        float outY = inputX * mat3x3s[sourceMatrix].getM()[0][1] + inputY * mat3x3s[sourceMatrix].getM()[1][1] +
                mat3x3s[sourceMatrix].getM()[2][1];
        float outZ = inputX * mat3x3s[sourceMatrix].getM()[0][2] + inputY * mat3x3s[sourceMatrix].getM()[1][2] +
                mat3x3s[sourceMatrix].getM()[2][2];
        if ( outZ != 0 ) {
            outX /= outZ;
            outY /= outZ;
        }
        return new Vec2df(outX, outY);
    }

    /**
     * The backward operation, opposite to the forward operation
     * @param inputX input x value
     * @param inputY input y value
     * @return return a @Vec2df object with the values of outputX and outputY
     */
    Vec2df backward(float inputX, float inputY) {
        float outX = inputX * mat3x3s[3].getM()[0][0] + inputY * mat3x3s[3].getM()[1][0] + mat3x3s[3].getM()[2][0];
        float outY = inputX * mat3x3s[3].getM()[0][1] + inputY * mat3x3s[3].getM()[1][1] + mat3x3s[3].getM()[2][1];
        float outZ = inputX * mat3x3s[3].getM()[0][2] + inputY * mat3x3s[3].getM()[1][2] + mat3x3s[3].getM()[2][2];
        if ( outZ != 0 ) {
            outX /= outZ;
            outY /= outZ;
        }
        return new Vec2df(outX, outY);
    }

    /**
     * The invert method, is high processing costly
     */
    void invert() {
        if ( isDirty ) {
            float det = mat3x3s[sourceMatrix].getM()[0][0] * (mat3x3s[sourceMatrix].getM()[1][1] * mat3x3s[sourceMatrix].getM()[2][2] - mat3x3s[sourceMatrix].getM()[1][2] * mat3x3s[sourceMatrix].getM()[2][1]) -
                    mat3x3s[sourceMatrix].getM()[1][0] * (mat3x3s[sourceMatrix].getM()[0][1] * mat3x3s[sourceMatrix].getM()[2][2] - mat3x3s[sourceMatrix].getM()[2][1] * mat3x3s[sourceMatrix].getM()[0][2]) +
                    mat3x3s[sourceMatrix].getM()[2][0] * (mat3x3s[sourceMatrix].getM()[0][1] * mat3x3s[sourceMatrix].getM()[1][2] - mat3x3s[sourceMatrix].getM()[1][1] * mat3x3s[sourceMatrix].getM()[0][2]);

            float idet = 1.0f / det;
            mat3x3s[3].getM()[0][0] = (mat3x3s[sourceMatrix].getM()[1][1] * mat3x3s[sourceMatrix].getM()[2][2] - mat3x3s[sourceMatrix].getM()[1][2] * mat3x3s[sourceMatrix].getM()[2][1]) * idet;
            mat3x3s[3].getM()[1][0] = (mat3x3s[sourceMatrix].getM()[2][0] * mat3x3s[sourceMatrix].getM()[1][2] - mat3x3s[sourceMatrix].getM()[1][0] * mat3x3s[sourceMatrix].getM()[2][2]) * idet;
            mat3x3s[3].getM()[2][0] = (mat3x3s[sourceMatrix].getM()[1][0] * mat3x3s[sourceMatrix].getM()[2][1] - mat3x3s[sourceMatrix].getM()[2][0] * mat3x3s[sourceMatrix].getM()[1][1]) * idet;
            mat3x3s[3].getM()[0][1] = (mat3x3s[sourceMatrix].getM()[2][1] * mat3x3s[sourceMatrix].getM()[0][2] - mat3x3s[sourceMatrix].getM()[0][1] * mat3x3s[sourceMatrix].getM()[2][2]) * idet;
            mat3x3s[3].getM()[1][1] = (mat3x3s[sourceMatrix].getM()[0][0] * mat3x3s[sourceMatrix].getM()[2][2] - mat3x3s[sourceMatrix].getM()[2][0] * mat3x3s[sourceMatrix].getM()[0][2]) * idet;
            mat3x3s[3].getM()[2][1] = (mat3x3s[sourceMatrix].getM()[0][1] * mat3x3s[sourceMatrix].getM()[2][0] - mat3x3s[sourceMatrix].getM()[0][0] * mat3x3s[sourceMatrix].getM()[2][1]) * idet;
            mat3x3s[3].getM()[0][2] = (mat3x3s[sourceMatrix].getM()[0][1] * mat3x3s[sourceMatrix].getM()[1][2] - mat3x3s[sourceMatrix].getM()[0][2] * mat3x3s[sourceMatrix].getM()[1][1]) * idet;
            mat3x3s[3].getM()[1][2] = (mat3x3s[sourceMatrix].getM()[0][2] * mat3x3s[sourceMatrix].getM()[1][0] - mat3x3s[sourceMatrix].getM()[0][0] * mat3x3s[sourceMatrix].getM()[1][2]) * idet;
            mat3x3s[3].getM()[2][2] = (mat3x3s[sourceMatrix].getM()[0][0] * mat3x3s[sourceMatrix].getM()[1][1] - mat3x3s[sourceMatrix].getM()[0][1] * mat3x3s[sourceMatrix].getM()[1][0]) * idet;
            isDirty = false;
        }
    }

    public int getNUM_MATRIX() {
        return NUM_MATRIX;
    }

    public Mat3x3[] getMat3x3s() {
        return mat3x3s;
    }

}
