package gameplay;

import board.Board;
import board.Edge;
import board.Hex;
import board.Utils;

import java.util.*;

/**
 * Created by Kristian on 03/05/16.
 */
public class Agent extends Hexifence {
    @Override
    public Move makeMove() {
        Move move = new Move();
        // Return row and col of move and color of self
        List<Edge> pm = board.generatePossibleMoves();
        if(pm.size() <= 50) {
            System.out.println("Made with minimax");
            move = minimaxDecision(board, player);
            move.P = this.player;
        } else {
            Edge edge = determineNextMove(board);
            move.Row = edge.getX();
            move.Col = edge.getY();
            move.P = this.player;
        }

        board.updateBoard(move, Utils.getColor(player));

        return move;
    }

    public Move minimaxDecision(Board board, int player) {
        Map<Edge, Integer> moveValue = new HashMap<>();
        List<Edge> possibleMoves = board.generatePossibleMoves();
        for (Edge move :
                possibleMoves) {
            int depth = 0;
            moveValue.put(move, minimaxValue(new State(board, move, player), depth));
        }

        int bestScore = -100;
        Edge bestMove = null;
        System.out.println(board);
        for (Map.Entry entry :
                moveValue.entrySet()) {
            if ((int) entry.getValue() > bestScore) {
                bestMove = (Edge) entry.getKey();
            }

        }
        Move move = new Move();
        move.Row = bestMove.getX();
        move.Col = bestMove.getY();
        move.P = player;

        return move;
    }

    private int minimaxValue(State state, int depth) {
        String out = "";
        for (int i = 0; i < depth; i++) {
            out += " ";
        }
//        System.out.print();
        System.out.println(out + depth + " " + state.toString());

        int terminalState = state.board.getWinner();
        if (depth > 6 || terminalState > 0) {
            return state.getUtility();
        } else if (state.player == 1) {
            int score = -1;
            List<State> successorStates = generateSuccesorStates(state);
            for (State successor :
                    successorStates) {
                depth++;
                int successorScore = minimaxValue(successor, depth);
                score = score > successorScore ? score : successorScore;
//                if (score == 1) {
//                    break;
//                }
            }
            return score;
        } else {
            int score = 1;
            List<State> successorStates = generateSuccesorStates(state);
            for (State successor :
                    successorStates) {
                depth++;
                int successorScore = minimaxValue(successor, depth);
                score = score < successorScore ? score : successorScore;
//                if (score == 1) {
//                    break;
//                }
            }
            return score;
        }


    }

    List<State> generateSuccesorStates(State state) {
        List<State> successorStates = new ArrayList<>();
        for (Edge move :
                state.generatePossibleMoves()) {
            successorStates.add(new State(state.board, move, Utils.getOponent(state.player)));
        }
        return successorStates;
    }

    public Edge determineNextMove(Board board) {
        List<Edge> possibleMoves = board.generatePossibleMoves();
        updateEdgeValues(possibleMoves, board);
        Edge mvEdge = possibleMoves.get(0);
        for (Edge e : possibleMoves) {
            if (e.getMoveValue() > mvEdge.getMoveValue()) {
                mvEdge = e;
            }
        }
        return mvEdge;
    }

    private void updateEdgeValues(List<Edge> possibleMoves, Board board) {
        int captCount;
        int simplePositionValue;
        int moveValue;
        for (Edge edge : possibleMoves) {
            captCount = captureCount(edge);
            edge.setStatus("R");
            simplePositionValue = board.maxNumberCaptureInOneMove(board);
            edge.setStatus("+");
            moveValue = captCount == 0 ? captCount - simplePositionValue : captCount + simplePositionValue;
            edge.setMoveValue(moveValue);
        }
    }

    private int captureCount(Edge e) {
        int captCount = 0;
        List<Hex> parents = e.getParents();
        for (Hex p : parents) {
            if (p.hasOneLeft()) {
                captCount++;
            }
        }
        return captCount;
    }

}
