package engine2d;

/**
 * A 3x3 matrix of type float. It is mainly used for
 * make transformations to images.
 *
 * @class: Mat3x3
 * @autor: Sergio Martí Torregrosa
 * @date: 2020-07-06
 */
public class Mat3x3 {

    private final int NUM_COLS = 3;

    private final int NUM_ROWS = 3;

    protected float[][] m;

    /**
     * Constructor
     *
     * @param m the matrix.
     */
    public Mat3x3(float[][] m) {
        this.m = m;
    }

    /**
     * Null constructor
     */
    public Mat3x3() {
        m = new float[NUM_COLS][NUM_ROWS];
        for ( int c = 0; c < NUM_COLS; c++ ) {
            for ( int r = 0; r < NUM_ROWS; r++ ) {
                m[c][r] = 0.0f;
            }
        }
    }

    public float[][] getM() {
        return m;
    }

    public int getNUM_COLS() {
        return NUM_COLS;
    }

    public int getNUM_ROWS() {
        return NUM_ROWS;
    }

    public void setAsIdentity() {
        m[0][0] = 1.0f;  m[1][0] = 0.0f;  m[2][0] = 0.0f;
        m[0][1] = 0.0f;  m[1][1] = 1.0f;  m[2][1] = 0.0f;
        m[0][2] = 0.0f;  m[1][0] = 0.0f;  m[2][2] = 1.0f;
    }

    public void setAsTranslate(float ox, float oy)
    {
        m[0][0] = 1.0f; m[1][0] = 0.0f; m[2][0] = ox;
        m[0][1] = 0.0f; m[1][1] = 1.0f; m[2][1] = oy;
        m[0][2] = 0.0f;	m[1][2] = 0.0f;	m[2][2] = 1.0f;
    }

    public void setAsRotate(float fTheta)
    {
        m[0][0] = (float) Math.cos(fTheta);  m[1][0] = (float) Math.sin(fTheta); m[2][0] = 0.0f;
        m[0][1] = (float) - Math.sin(fTheta); m[1][1] = (float) Math.cos(fTheta); m[2][1] = 0.0f;
        m[0][2] = 0.0f;			 m[1][2] = 0.0f;		 m[2][2] = 1.0f;
    }

    public void setAsScale(float sx, float sy)
    {
        m[0][0] = sx;   m[1][0] = 0.0f; m[2][0] = 0.0f;
        m[0][1] = 0.0f; m[1][1] = sy;   m[2][1] = 0.0f;
        m[0][2] = 0.0f;	m[1][2] = 0.0f;	m[2][2] = 1.0f;
    }

    public void setAsShear(float sx, float sy)
    {
        m[0][0] = 1.0f; m[1][0] = sx;   m[2][0] = 0.0f;
        m[0][1] = sy;   m[1][1] = 1.0f; m[2][1] = 0.0f;
        m[0][2] = 0.0f;	m[1][2] = 0.0f;	m[2][2] = 1.0f;
    }

    public void invert() {
        float[][] matOut = new float[NUM_COLS][NUM_ROWS];

        float det = m[0][0] * (m[1][1] * m[2][2] - m[1][2] * m[2][1]) -
                m[1][0] * (m[0][1] * m[2][2] - m[2][1] * m[0][2]) +
                m[2][0] * (m[0][1] * m[1][2] - m[1][1] * m[0][2]);

        float ident = 1.0f / det;
        matOut[0][0] = (m[1][1] * m[2][2] - m[1][2] * m[2][1]) * ident;
        matOut[1][0] = (m[2][0] * m[1][2] - m[1][0] * m[2][2]) * ident;
        matOut[2][0] = (m[1][0] * m[2][1] - m[2][0] * m[1][1]) * ident;
        matOut[0][1] = (m[2][1] * m[0][2] - m[0][1] * m[2][2]) * ident;
        matOut[1][1] = (m[0][0] * m[2][2] - m[2][0] * m[0][2]) * ident;
        matOut[2][1] = (m[0][1] * m[2][0] - m[0][0] * m[2][1]) * ident;
        matOut[0][2] = (m[0][1] * m[1][2] - m[0][2] * m[1][1]) * ident;
        matOut[1][2] = (m[0][2] * m[1][0] - m[0][0] * m[1][2]) * ident;
        matOut[2][2] = (m[0][0] * m[1][1] - m[0][1] * m[1][0]) * ident;
        
        m = matOut;
    }

    public void multiply(Mat3x3 matrix) {
        float[][] result = new float[NUM_COLS][NUM_ROWS];
        for ( int c = 0; c < NUM_COLS; c++ ) {
            for ( int r = 0; r < NUM_ROWS; r++ ) {
                result[c][r] = m[0][r] * matrix.getM()[c][0] + m[1][r] * matrix.getM()[c][1] + m[2][r] * matrix.getM()[c][2];
            }
        }
        m = result;
    }

    public void setM(float[][] m) {
        for ( int c = 0; c < NUM_COLS; c++ ) {
            System.arraycopy(m[c], 0, this.m[c], 0, NUM_ROWS);
        }
    }

}
