package board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by Kristian on 10/05/16.
 */
public class Utils {

    public static String getColor(int p) {
        return p == 1 ? "B" : "R";
    }

    public static int getOponent(int player) {
        return player == 1 ? 2 : 1;
    }

    public static List<List<String>> generateTestBoard(String boardString) {
        List<List<String>> inputAsStringList = new ArrayList<>();
        for (String line :
                boardString.substring(2).split("\n")) {
            String[] lineArray = line.split(" ");
            List<String> row = new ArrayList<>();
            row.addAll(Arrays.asList(lineArray));
            inputAsStringList.add(row);
        }

        return inputAsStringList;
    }
}
