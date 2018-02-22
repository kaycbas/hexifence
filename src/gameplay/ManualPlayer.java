package gameplay;

import board.Board;
import board.Edge;
import board.Utils;

import java.io.PrintStream;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Kristian on 28/04/16.
 */
public class ManualPlayer extends Hexifence {
    @Override
    public Move makeMove() {
        // Return row and col of move and color of self
        Move move = new Move();
        System.out.println("Enter a coord: ");
        Scanner scanner = new Scanner(System.in);
        int coord = scanner.nextInt();
        // int x = scanner.nextInt();
        // int y = scanner.nextInt();
        int x = (int) Math.floor(coord / 10);
        int y = coord % 10;

        move.Row = x;
        move.Col = y;
        move.P = super.player;

        board.updateBoard(move, Utils.getColor(super.player));

        return move;
    }
}
