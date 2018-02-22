package board;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Kristian Elset Boe 767956
 * and Kevin Bastoul 802847
 * on 17/03/16.
 */
public class InputAndValidation {
    private int size;
    private List<List<String>> inputAsStringList; //2D String list to hold board data

    public InputAndValidation() {
        size = 0;
        inputAsStringList = new ArrayList<>();
    }

    public int getSize() {
        return size;
    }

    public List<List<String>> getInputFromSysIn() { //Initiates reading board data from console
        InputStreamReader cin = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(cin);

        getMapFromReaderAndValidate(reader);     //Passes reader to the parse/validation method

        return inputAsStringList;
    }

    private void getMapFromReaderAndValidate(BufferedReader reader) {
        try {
            size = Integer.parseInt(reader.readLine());

            if (size < 0 && size > 4) {
                throw new IllegalArgumentException(String.format("Expected a size between 1 and 3, but got %d", size));
            }

            while (reader.ready()) {  //Reads input line by line, splits by white spaces,
                String line = reader.readLine(); //and stores data in a String list
                String[] lineArray = line.split(" ");
                List<String> row = new ArrayList<>();
                validateRow(row);
                row.addAll(Arrays.asList(lineArray));
                inputAsStringList.add(row);
            }
            validateMapSize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void validateMapSize() {   //Checks if map size corresponds to given dimensions
        int lol = (inputAsStringList.size() + 1) / 4;
        if (size != (inputAsStringList.size() + 1) / 4) {
            throw new IllegalArgumentException("The board size is wrong vertically");
        } else if (size != (inputAsStringList.get(0).size() + 1) / 4) {
            throw new IllegalArgumentException("The board size is wrong horizontally");
        }
    }

    private void validateRow(List<String> line) {  //Checks if input line is in a valid format
        String prevChar = "";
        for (String s : line) {
            if (prevChar.equals(s))
                throw new IllegalArgumentException(String.format("Repeated character in line: %s", line));
            prevChar = s;
            switch (s) {
                case " ":
                    break;
                case "R":
                    break;
                case "B":
                    break;
                case "+":
                    break;
                case "-":
                    break;
                default:
                    throw new IllegalArgumentException(String.format("Expected (' ', R, B, + or -, got %s", s));
            }
        }
    }

    /* Had some misconceptions about how input would be given so this method is techniclaly legacy. Chose to leave it in
     * for later use */
    public List<List<String>> getInputAsStringList(String fileName) {   //Initiates reading board data from file

        try {    //Opens a buffered reader of the file & passes it to the parse/validation method
            String f = "/Users/Kristian/Developer/Java/Hexafence/src/" + fileName;
            InputStream inputFile = new FileInputStream(f);
            InputStreamReader input = new InputStreamReader(inputFile);
            BufferedReader reader = new BufferedReader(input);
            getMapFromReaderAndValidate(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputAsStringList;
    }
}
