package board;

import java.util.List;

/**
 * Created by Kristian Elset Boe 767956
 * and Kevin Bastoul 802847
 * on 17/03/16.
 */
public class Edge {        //Class for representing an edge an storing related data
    final int x;    // x & y coordinates
    final int y;
    List<Hex> parents;    // List of hexes this edge belongs to
    String status; // B, R, +, -
    int moveValue;

    public Edge(int x, int y, List<Hex> parents, String status) {
        this.x = x;
        this.y = y;
        this.parents = parents;
        this.status = status;
    }

    public List<Hex> getParents() {
        return parents;
    }

    public void setParents(List<Hex> parents) {
        this.parents = parents;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getMoveValue() {
        return moveValue;
    }

    public void setMoveValue(int val) {
        moveValue = val;
    }

}
