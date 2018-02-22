package gameplay;

import board.Board;
import board.Edge;
import board.Hex;
import board.Utils;

import java.util.List;

/**
 * Created by Kristian on 06/05/16.
 */
public class State {
    Board board;
    Edge move;
    int player;
    int utility;

    public State(Board board, Edge move, int player) {
        this.board = new Board(board);
        this.move = move;
        this.player = player;
        Move m = new Move();
        m.Row = move.getX();
        m.Col = move.getY();
        m.P = player;
        this.board.updateBoard(m, Utils.getColor(player));
        this.utility = getUtility();
    }

    public List<Edge> generatePossibleMoves() {
        return board.generatePossibleMoves();
    }

    public int getUtility() {
        int score = 0;
        List<Hex> parents = move.getParents();
        for (Hex parent :
                parents) {
            if (parent.numTaken == 5) {
                score -= 5;
            }
            if (parent.numTaken == 4) {
                score += 5;
            }
            if (parent.wasCaptured) {
                score += 10;
            }
        }
        int winner = board.getWinner();
        if (winner == player) {
            score += 50;
        }

        if (player == 2) {
            score = 0 - score;
        }
        return score;
    }


    private int smartUtility() {
        return board.winBy();

    }

//    don't be the 5th edge
//    capture 2?
//    capture 1?
    @Override
    public String toString() {
        return "State{" +
                ", move=(" + move.getX() + ","+move.getY()+
                "), player=" + player +
                ", utility=" + utility +
                '}';
    }
}
