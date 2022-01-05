import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    private static final String FILE_NAME = "data/aoc4.txt";
    private static final URL resource = Main.class.getClassLoader().getResource(FILE_NAME);
    private static Stack<Matrix> winners = new Stack<>();
    private static Stack<Matrix> winning_scores = new Stack<>();
    private static ArrayList<Matrix> boards = new ArrayList<>(100);
    private static ArrayList<Matrix> scores = new ArrayList<>(100);
    private static List<Integer> bingo_nums = null;
    private static int lastNum = 0;
    /**
     * Main entry point.
     * @param args ignored
     */
    public static void main(final String[] args) {

        try(FileInputStream fis = new FileInputStream(resource.getFile());
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader in = new BufferedReader(isr)) {
            String line;
            int i = 0;
            Matrix b = new Matrix(5,5);
            while((line = in.readLine()) != null) {
                if (line.matches("([0-9][,]*)+")) {
                    bingo_nums = (List<Integer>) Arrays.stream(
                            line.split(",")).map(x -> Integer.parseInt(x)).toList();
                } else if (line.matches("^(([ ]*)([0-9]+)){5}$")) {
                    String[] s = line.split("[ ]+");
                    int j = 0;
                    for (String n : s) {
                        if (!n.isEmpty()) {
                            b.set(i, j, Integer.parseInt(n));
                            j++;
                        }
                    }
                    i++;
                    if (i % 5 == 0) {
                        i = 0;
                        scores.add(new Matrix(5,5));
                        boards.add(b);
                        b.print();
                        b = new Matrix(5,5);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < bingo_nums.size(); i++) {
            int n = bingo_nums.get(i);
            while (callNumber(n)) {
            }
        }
        printWinner(lastNum, winning_scores.pop(), winners.pop());
    }

    private static boolean callNumber(int n) {
        System.out.println("Calling number: " + n);
        for (int i = 0; i < boards.size(); i++) {
            Matrix b = boards.get(i);
            Matrix s = scores.get(i);
            int[] idx = b.find(n);
            if (idx != null) {
                scores.get(i).set(idx[0],idx[1], 1);
            }
            if (scores.get(i).isWinner()) {
                lastNum = n;
                winning_scores.push(scores.remove(i));
                winners.push(boards.remove(i));
                return true;
            }
        }
        return false;
    }

    private static final void printWinner(int last, Matrix score, Matrix board) {
        System.out.println("Winner Winner! Chicken Dinner");
        score.print();
        board.print();
        score.binaryInvert();
        score.print();
        score.hadamardMultiply(board);
        score.print();
        int sum = score.sum();
        System.out.println("Sum: " + sum);
        System.out.println("Last Called: " + last);
        int value = sum * last;
        System.out.println("Winning Value: " + value);
    }
}
