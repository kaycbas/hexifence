package test;

import board.Board;
import board.Utils;
import gameplay.Agent;
import gameplay.Hexifence;
import gameplay.Move;

import java.util.List;

/**
 * Created by Kristian on 11/05/16.
 */
public class Test {


    String initialBoard = "2\n" +
            "B R B R - - -\n" +
            "R b R r B - -\n" +
            "R R B R R B -\n" +
            "+ - B b B - R\n" +
            "- B R R B + R\n" +
            "- - R - B - B\n" +
            "- - - B + B R\n";

    public static void main(String[] args) {
        Test test = new Test();
        List<List<String>> inputAsStringList = Utils.generateTestBoard(test.initialBoard);
        Board board = new Board(2, inputAsStringList);
        System.out.println(board);

        Agent agent = new Agent();
        agent.init(2, 1);
        agent.setBoard(new Board(board));
        Hexifence stupid = new Hexifence();
        stupid.init(2, 2);
        stupid.setBoard(new Board(board));
        Move move = agent.minimaxDecision(board, 1);
        board.updateBoard(move, "B");
        System.out.println(board);



    }
}
