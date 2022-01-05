import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Matrix {

    /**
     * Number of rows
     */
    private int rows;

    /**
     * Number of columns
     */
    private int cols;

    /**
     * Matrix data
     */
    private int[][] data;

    /**
     * Create a new matrix n by m
     */
    public Matrix(final int n, final int m) {
        if (n < 0 || m < 0 || n >= Integer.MAX_VALUE || m >= Integer.MAX_VALUE) {
            throw new IllegalArgumentException("n or m, out of acceptable range.");
        }
        this.rows = n;
        this.cols = m;
        data = new int[n][m];
    }

    /**
     * Get a row vector
     * @param row the index of the row
     * @return the vector
     */
    public int[] row(int row) {
        return data[row];
    }

    /**
     * Get a column vector
     * @param column the index of the column
     * @return the vector
     */
    public Integer[] col(final int column) {
        return (Integer[]) Arrays.stream(data).map(x -> x[column]).toArray(Integer[]::new);
    }

    /**
     * Get the value at [n, m] in this matrix.
     * @param n the row
     * @param m the col
     * @return the value at [row,col]
     */
    public int at(final int n, final int m) {
        if (n < 0 || m < 0 || n > rows || m > cols) {
            throw new IllegalArgumentException("n or m, out of acceptable range.");
        }
        return data[n][m];
    }

    public void set(final int n, final int m, final int v) {
        if (n < 0 || m < 0 || n > rows || m > cols) {
            throw new IllegalArgumentException("n or m, out of acceptable range.");
        }
        data[n][m] = v;
    }

    /**
     * Print the matrix
     */
    public void print() {
        System.out.println();
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                System.out.print(data[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean isWinner() {
        int v = 0;
        for (int i = 0; i < rows; i++) {
            if (Arrays.stream(col(i)).reduce(1, (a, b) -> a * b) == 1) {
                return true;
            }
        }
        for (int i = 0; i < cols; i++) {
            if (Arrays.stream(row(i)).reduce(1, (a, b) -> a * b) == 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns an array of length 2 containing the indicies of the found element
     * return null if not found.
     *     - Nothing special O(n*m) lookup time.
     *
     * @param n the number to search for in this matrix
     * @return
     */
    public int[] find(int n) {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                if (data[i][j] == n) {
                    return new int[] {i,j};
                }
            }
        }
        return null;
    }

    /**
     * Get an int stream of this matrix
     * @return
     */
    public IntStream toIntStream() {
        return Arrays.stream(data).flatMapToInt(Arrays::stream);
    }

    /**
     * Return this matrix as a flat list.
     * @return
     */
    public ArrayList<Integer> toList() {
        return toIntStream().boxed()
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Get the sum of all elements.
     * @return
     */
    public int sum() {
        return toIntStream().sum();
    }

    /**
     * Given that this matrix is a binary matrix,
     * invert each 1 to a 0 and each 0 to a 1.
     *
     * (If this matrix is not a binary matrix however the result
     * would be a binary matrix that any 0 is 1 and anything else
     * is a 0.)
     */
    public void binaryInvert() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = (data[i][j] == 0) ? 1 : 0;
            }
        }
    }

    /**
     * Hadamard product of this matrix with b
     * @param b matrix b
     * @return the product
     */
    public final void hadamardMultiply(Matrix b) {
        if (rows != b.rows || cols != b.cols) {
            throw new IllegalArgumentException("Cannot hadamard multiply a and b, Invalid dimensions!");
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] *= b.data[i][j];
            }
        }
    }
}
