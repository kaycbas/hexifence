package gameplay;

import board.Board;
import board.Edge;
import board.Utils;

import java.io.PrintStream;
import java.util.List;
import java.util.Random;


/**
 * Created by Kristian Elset Boe 767956
 * and Kevin Bastoul 802847
 * on 17/03/16.
 */
public class Hexifence implements Player, Piece {

    Board board;

    public void setBoard(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    int size;

    int player;
    int opponent;

    //public static void main(String[] args) {
     //   Hexifence hex = new Hexifence();
     //   hex.init(3, 1);
        /*
        List<List<String>> inputAsStringList = new ArrayList<>();
        int size = 0;

        InputAndValidation io = new InputAndValidation();    //object to read and validate input


        if (args.length == 0) {
            inputAsStringList = io.getInputFromSysIn();    //Reads and validates input directly from console
        } else if (args.length == 1) {
            String arg = args[0];
            if (arg.equals("sample1")) {
//                inputAsStringList = io.getBoardFromSampleString1();    //Hard coded sample input for testing
            } else if (arg.equals("sample2")) {
//                inputAsStringList = io.getBoardFromSampleString2();    //Hard coded sample input for testing
            } else {
                String fileName = args[0];
                inputAsStringList = io.getInputAsStringList(fileName);    //Reads and validates input from provided file
            }
        } else {
            throw new IllegalArgumentException("Too many arguments, expects at most one");
        }

        size = io.getSize();    //Stores board dimension

        Board board = new Board(size, inputAsStringList);    //Initiates construction of board object

        HexGame game = new HexGame(board);    //Initiates playing of game (this is where part A queries are solved)
        */
    //}

    @Override
    public int init(int n, int p) {
        try {
            // asign color to self
            this.player = p;
            this.opponent = Utils.getOponent(p);
            this.size = n;
            this.board = new Board(n, null);
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }


    @Override
    public Move makeMove() {
        // Return row and col of move and color of self

        // Makes a random move
        Move move = new Move();
        move.P = this.player;

        List<Edge> possibleMoves = board.generatePossibleMoves();
        Random random = new Random();
        int index = random.nextInt(possibleMoves.size());

        Edge edge = possibleMoves.get(index);

        //Edge nextEdge = game.determineNextMove(board);
        //move.Row = nextEdge.getX();
        //move.Col = nextEdge.getY();

        //Move nextMove = game.minimaxDecision(board);

        move.Row = edge.getX();
        move.Col = edge.getY();


        board.updateBoard(move, Utils.getColor(player));

        return move;
    }

    @Override
    public int opponentMove(Move m) {
        try {
            validateMove(m);
            return board.updateBoard(m, Utils.getColor(opponent));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return -1;
        }

    }

    private void validateMove(Move m) {
        int x = m.Row;
        int y = m.Col;

        if (x < 2 * size - 1 && y >= 2 * size + x
                || x >= 2 * size && y <= x - 2 * size) {
            String error = String.format("The move is outside the map, move is %d, %d", m.Row, m.Col);
            throw new IllegalArgumentException(error);
        }
        if (x % 2 == 1 && y % 2 == 1) {
            String error = String.format("The move is inside a hexagon, aka it's not an edge, move is %d, %d", m.Row, m.Col);
            throw new IllegalArgumentException(error);
        }
        if (m.P != 1 && m.P != 2) {
            throw new IllegalArgumentException("The player value is not 1 or 2");
        }
        if (board.getEdgeMap().get(x).get(y).getStatus().equals("-")) {
            String error = String.format("Opponent tried to place on an invalid edge, move is %d, %d", m.Row, m.Col);
            throw new IllegalArgumentException(error);
        }
        if (board.getEdgeMap().get(x).get(y).getStatus().equals(Utils.getColor(player))) {
            String error = String.format("Opponent tried to place on one of my edges, move is %d, %d", m.Row, m.Col);
            throw new IllegalArgumentException(error);
        }
        if (board.getEdgeMap().get(x).get(y).getStatus().equals("b") || board.getEdgeMap().get(x).get(y).getStatus().equals("r")) {
            String error = String.format("Opponent tried to place on a hex coord, move is %d, %d", m.Row, m.Col);
            throw new IllegalArgumentException(error);
        }
    }

    @Override
    public int getWinner() {
        return board.getWinner();
        // They said in the tute that the game continiues until all pieces are played, but the winner is checked every move.
        // By just counting the score we could just terminate the game whenever one player gets over half the points
    }

    @Override
    public void printBoard(PrintStream output) {
        output.print(board.toString());
        // Write board to printstream
    }
}
