package board;

import gameplay.Move;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Kristian Elset Boe 767956
 * and Kevin Bastoul 802847
 * on 17/03/16.
 */
public class Board {

    private List<List<Edge>> edgeMap = new ArrayList<>();        //2D list to hold Edges
    private List<List<Hex>> hexMap = new ArrayList<>();        //2D list to hold Hexes
    private Set<Hex> hexesWtihOneEdgeLeft = new HashSet<>();    //Set to hold Hexes that can be captured in one move
    private int size;
    public int scoreBlue;
    public int scoreRed;


    public Board(int size, List<List<String>> inputAsStringList) {
        this.size = size;
        createEdgeMap(size, inputAsStringList);

        createHexMap(size);

        initializeMap();

    }

    public Board(Board board) {
        this.size = board.size;
        int n = size;
        List<List<Edge>> oldMap = board.getEdgeMap();

        for (int i = 0; i < 4 * n - 1; i++) {
            edgeMap.add(new ArrayList<Edge>());
            for (int j = 0; j < 4 * n - 1; j++) {
                Edge oldEdge = oldMap.get(i).get(j);
                Edge newEdge = new Edge(i, j, new ArrayList<Hex>(), oldEdge.status);
                edgeMap.get(i).add(newEdge);
            }
        }

        createHexMap(size);

        initializeMap();
    }

    private void createEdgeMap(int n, List<List<String>> input) {
        for (int i = 0; i < 4 * n - 1; i++) {        //populates edgeMap using the initial input String
            edgeMap.add(new ArrayList<Edge>());
            for (int j = 0; j < 4 * n - 1; j++) {
                String edge;
                if (input == null) {
                    if (i < 2 * n - 1 && j >= 2 * n + i
                            || i >= 2 * n && j <= i - 2 * n
                            || i % 2 == 1 && j % 2 == 1) {
                        edge = "-";
                    } else {
                        edge = "+";
                    }
                } else {
                    edge = input.get(i).get(j);
                }

                edgeMap.get(i).add(new Edge(i, j, new ArrayList<Hex>(), edge));
            }
        }
    }

    private void createHexMap(int n) {    //creates hexagons and populates hexMap
        for (int i = 0; i < 2 * n - 1; i++) {
            hexMap.add(new ArrayList<Hex>());
            for (int j = 0; j < 2 * n - 1; j++) {
                Edge nw = edgeMap.get(2 * i).get(2 * j);            //
                Edge ne = edgeMap.get(2 * i).get(2 * j + 1);        //
                Edge e = edgeMap.get(2 * i + 1).get(2 * j + 2);     //   Assigns  each hexagon with corresponding edges
                Edge se = edgeMap.get(2 * i + 2).get(2 * j + 2);    //   in the edgeMap
                Edge sw = edgeMap.get(2 * i + 2).get(2 * j + 1);    //
                Edge w = edgeMap.get(2 * i + 1).get(2 * j);         //
                hexMap.get(i).add(new Hex(nw, ne, e, se, sw, w, i, j, 0, false));    // adds constructed hexagon to map and assigns
                // it coordinates
            }
        }
    }

    private void initializeMap() {          // assigns parent hexagons to edges & populates set of hexes that have one edge left
        for (List<Hex> hexRow : hexMap) {
            for (Hex hex : hexRow) {
                int counter = 0;
                for (Edge edge : hex.edges) {
                    edge.parents.add(hex);     // assigns parent hexagons to edges
                    if (edge.status.equals("R") || edge.status.equals("B")) {
                        counter++;
                        hex.numTaken++;
                    }
                    if (counter == 5) {        // if the hex has 5 occupied edges, add to hexesWithOneEdgeLeft
                        hex.oneLeft = true;
                        hexesWtihOneEdgeLeft.add(hex);
                    }
                    if (counter == 6) {  // if 6, remove
                        hex.oneLeft = false;
                        hexesWtihOneEdgeLeft.remove(hex);
                    }
                }
                if (hex.getP() == 1) {
                    scoreBlue++;
                } else if (hex.getP() == 2) {
                    scoreRed++;
                }
            }
        }
    }

    public int updateHexes(String player) { // Updates hexesWithOneLeft, will be used when board changes
        int result = 0;
        for (Hex hex :
                gethexes()) {
            int counter = 0;
            for (Edge edge :
                    hex.edges) {
                if (edge.status.equals("R") || edge.status.equals("B")) {
                    counter++;
                }
                if (counter == 5) {
                    hex.oneLeft = true;
                    hexesWtihOneEdgeLeft.add(hex);
                }
                if (counter == 6) {
                    hex.wasCaptured = true;
                    if (hex.p == 0) {
                        int p = player.equals("B") ? 1 : 2;
                        hex.setP(p);
                        int hx = hex.Hx;
                        int hy = hex.Hy;
                        edgeMap.get(2 * hx + 1).get(2 * hy + 1).setStatus(player.toLowerCase());
                        if (p == 1) {
                            scoreBlue++;
                        } else {
                            scoreRed++;
                        }
//                        System.out.println(String.format("Hex captured by P%d at %d,%d", p, 2 * hx + 1, 2 * hy + 1));
                        result = 1;
                        hexesWtihOneEdgeLeft.remove(hex);
                    }

                }
            }
        }
        return result;

    }

    public List<List<Edge>> getEdgeMap() {
        return edgeMap;
    }

    public List<List<Hex>> getHexMap() {
        return hexMap;
    }

    public Set<Hex> getHexesWtihOneEdgeLeft() {
        return hexesWtihOneEdgeLeft;
    }

    public int updateBoard(Move m, String player) {
        try {
            Edge edge = edgeMap.get(m.Row).get(m.Col);
            edge.setStatus(player);
            for (Hex hex: edge.getParents()) {
                hex.numTaken++;
            }
            return updateHexes(player);
        } catch (IndexOutOfBoundsException e) {
            return -1;
        }
    }

    // Returns a flat list of all hexes
    public List<Hex> gethexes() {
        List<Hex> hexes = new ArrayList<>();
        for (List<Hex> hexRow : hexMap) {
            hexes.addAll(hexRow);
        }
        return hexes;
    }

    public List<Edge> generatePossibleMoves() {
        List<Edge> possibleMoves = new ArrayList<>();
        for (List<Edge> row :
                edgeMap) {
            for (Edge edge :
                    row) {
                if (edge.status.equals("+")) {
                    possibleMoves.add(edge);
                }
            }

        }
        return possibleMoves;
    }


    @Override
    public String toString() {        // Converts board to string for outputting
        String s = "";
        int rowCount = 0;
        for (List<Edge> line : edgeMap) {
            int colCount = 0;
            for (int i = 0; i < Math.abs((size * 4 - 2) / 2 - rowCount); i++) {
                s += " ";
            }
            for (Edge edge : line) {
                String e = edge.toString().toUpperCase();
                if (e.equals("+") || e.equals("B") || e.equals("R")) {
                    s += edge.toString() + " ";
                } else if (rowCount > 0 && rowCount < 4 * size - 2 && colCount > 0 && colCount < 4 * size - 2) {
                    if (e.equals("-") && !line.get(colCount - 1).toString().equals("-") && !line.get(colCount + 1).toString().equals("-")) {
                        s += edge.toString() + " ";
                    }
                }
                colCount++;
            }
            rowCount++;
            s += "\n";
        }
        String score = String.format("B: %d, R: %d\n", scoreBlue, scoreRed);
        return "Board{\n" +
                s +
                "}\n" +
                score;
    }

    public int getWinner() {
        /*
        int p1 = 0;
        int p2 = 0;

        for (Hex hex :
                gethexes()) {
            if (hex.getP() == 1) {
                p1++;
            } else if (hex.getP() == 2) {
                p2++;
            }
        }
        int max_points = gethexes().size();
        if (p1 > max_points / 2) {
            return 1;
        } else if (p2 > max_points / 2) {
            return 2;
        } else */
        if (generatePossibleMoves().isEmpty()) {
            if (scoreBlue > scoreRed) {
                return 1;
            } else {
                return 2;
            }
        } else {
            return 0;
        }
    }

<<<<<<< HEAD
    public int winBy() {
        int p1 = 0;
        int p2 = 0;

        for (Hex hex :
                gethexes()) {
            if (hex.getP() == 1) {
                p1++;
            } else if (hex.getP() == 2) {
                p2++;
            }
        }
        /*
        if (player == 1) {
            System.out.print("P1 score = ");
            System.out.println(p1);
            System.out.print("P2 score = ");
            System.out.println(p2);
        }*/

        if (generatePossibleMoves().isEmpty()) {
            return p1-p2; //will never = 0 bc odd number of hexagons
        } else {
            return 0;
        }
    }

}
=======

    public static int maxNumberCaptureInOneMove(Board board) {    // answers query 2
        Set<Hex> canBeCaptured = board.getHexesWtihOneEdgeLeft();    //retrieves set of hexes with one edge left

        if (canBeCaptured.size() == 0) return 0;    // if this set empty, return 0

        for (Hex hex : canBeCaptured) {        // if this set has two hexes that share an edge, return 2
            for (Edge edge : hex.edges) {
                if (edge.getStatus().equals("+")) {
                    if (edge.getParents().size() == 2 && canBeCaptured.containsAll(edge.getParents())) {
                        return 2;
                    }
                }

            }
        }
        return 1;    // if neither of these, return 1
    }

    public static int howManyHaveOneLeft(Board board) {        // answers query 3
        return board.getHexesWtihOneEdgeLeft().size();    // retrieves set of hexes with one left and checks its size
    }

}
>>>>>>> 126b81f695696c5e5e8cf0c739fc3aa9802ea8c1
