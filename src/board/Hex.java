package board;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kristian Elset Boe 767956
 * and Kevin Bastoul 802847
 * on 17/03/16.
 */
public class Hex {        //Class for representing a Hexagon and storing related data

    Edge nw, ne, e, se, sw, w;    // Stores references to the 6 edges that make up this hexagon
    final int Hx;        // Hexagon x & y coordinates (denoted Hx & Hy to avoid confusion with edge coordinates)
    final int Hy;

    public int numTaken;    // how many edges are claimes

    boolean oneLeft;    // if only one edge is unclaimed

    public boolean wasCaptured;

    public List<Edge> edges = new ArrayList<>();

    int p = 0;

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public boolean hasOneLeft() {
        if (oneLeft) { return true; }
        else { return false; }
    }

    public Hex(Edge nw, Edge ne, Edge e, Edge se, Edge sw, Edge w, int hx, int hy, int numTaken, boolean oneLeft) {
        this.nw = nw;
        this.ne = ne;
        this.e = e;
        this.se = se;
        this.sw = sw;
        this.w = w;
        Hx = hx;
        Hy = hy;
        this.numTaken = numTaken;
        this.oneLeft = oneLeft;

        edges.add(nw);
        edges.add(ne);
        edges.add(e);
        edges.add(se);
        edges.add(sw);
        edges.add(w);
    }

    @Override
    public String toString() {    // Converts hex data to string for outputting
        return "Hex{" +
                "nw=" + nw +
                ", NE=" + ne +
                ", E=" + e +
                ", SE=" + se +
                ", SW=" + sw +
                ", W=" + w +
                ", Hx=" + Hx +
                ", Hy=" + Hy +
                ", numTaken=" + numTaken +
                ", oneLeft=" + oneLeft +
                '}';
    }
}
