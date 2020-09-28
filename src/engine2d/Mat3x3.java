package engine2d;

/**
 * Thi class is a representation of a three by three
 * matrix.
 *
 * @class Mat3x3
 * @author Sergio Martí Torregrosa. sMartiTo
 * @date 28/09/2020
 */
public class Mat3x3 {

    private final int NUM_COLS = 3;

    private final int NUM_ROWS = 3;

    private float[][] m;

    /**
     * Constructor
     *
     * @param m the matrix.
     */
    public Mat3x3(float[][] m) {
        this.m = m;
    }

    /**
     * Void constructor
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

    public void setM(float[][] m) {
        for ( int c = 0; c < NUM_COLS; c++ ) {
            System.arraycopy(m[c], 0, this.m[c], 0, NUM_ROWS);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for ( int c = 0; c < NUM_COLS; c++ ) {
            stringBuilder.append("col ").append(c).append(": |");
            for ( int r = 0; r < NUM_ROWS; r++ ) {
                stringBuilder.append(m[c][r]).append(" | ");
            }
        }
        return stringBuilder.toString();
    }
}
