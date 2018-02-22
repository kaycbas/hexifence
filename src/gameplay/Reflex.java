package gameplay;

import board.Edge;
import board.Hex;
import board.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kristian on 12/05/16.
 */
public class Reflex extends Hexifence {

    @Override
    public Move makeMove() {
        Move move = new Move();
        // Return row and col of move and color of self
        Map<Edge, Integer> moveValue = new HashMap<>();
        List<Edge> possibleMoves = board.generatePossibleMoves();
        for (Edge edge :
                possibleMoves) {
            moveValue.put(edge, getEdgeValue(edge));
        }

        int bestScore = -100;
        Edge bestMove = null;
        for (Map.Entry entry :
                moveValue.entrySet()) {
            if ((int) entry.getValue() > bestScore) {
                bestMove = (Edge) entry.getKey();
            }

        }

        Edge edge = bestMove;
        move.Row = edge.getX();
        move.Col = edge.getY();
        move.P = this.player;

        board.updateBoard(move, Utils.getColor(player));

        return move;
    }

    public int getEdgeValue(Edge edge) {
        int score = 0;
        List<Hex> parents = edge.getParents();
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

}
