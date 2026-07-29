/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astro.ui;

import java.util.ArrayList;

/**
 *
 * @author Rayan Ivaturi
 */
public class TextChart {

    public static final String RASI = "RASI";
    public static final String AMSA = "AMSA";
    public static final String D3 = " D3 ";
    public static final char SPACE = ' ';
    public static final char SLASH = '*';
    public static final char BSLASH = '*';
    public static final char EXCLAM = '!';
    public static final char DASH = '-';
    public static final char PLUS = '+';
    public static final int ROWS = 43;
    public static final int COLS = 76;
    private static StringBuffer[] line = new StringBuffer[ROWS];

    public static String getChart(ArrayList[] houses, String chartName) {

        drawZodiac(houses, chartName);

        StringBuffer sb = new StringBuffer();

        sb.append(PLUS);
        for (int i = 0; i < TextChart.COLS; i++) {
            sb.append(TextChart.DASH);
        }
        sb.append(PLUS);
        sb.append('\r');
        sb.append('\n');

        for (int i = 0; i < ROWS; i++) {
            sb.append(EXCLAM);
            sb.append(line[i].toString());
            sb.append(EXCLAM);
            sb.append('\r');
            sb.append('\n');
        }

        sb.append(PLUS);
        for (int i = 0; i < TextChart.COLS; i++) {
            sb.append(TextChart.DASH);
        }
        sb.append(PLUS);
        sb.append('\r');
        sb.append('\n');

        return sb.toString();
    }//end getChart

    private static void drawZodiac(ArrayList[] house, String chartName) {
        drawLines(chartName);
        fillFirstHouse(house[0]);
        fillSecondHouse(house[1]);
        fillThirdHouse(house[2]);
        fillFourthHouse(house[3]);
        fillFifthHouse(house[4]);
        fillSixthHouse(house[5]);
        fillSeventhHouse(house[6]);
        fillEighthHouse(house[7]);
        fillNinthHouse(house[8]);
        fillTenthHouse(house[9]);
        fillEleventhHouse(house[10]);
        fillTwelfthHouse(house[11]);
    }//end drawZodiac();

    private static void drawLines(String chartName) {
        for (int i = 0; i < ROWS; i++) {
            line[i] = new StringBuffer(COLS);
        }

        //the following loop draws first 16 lines
        int j = 0;
        int k = COLS;

        for (int i = 0; i < 16; i++) {
            for (int m = 0; m < j; m++) {
                line[i].append(SPACE);
            }
            line[i].append(BSLASH);
            for (int n = j + 1; n < k - 1; n++) {
                line[i].append(SPACE);
            }
            line[i].append(SLASH);
            for (int p = k; p < COLS; p++) {
                line[i].append(SPACE);
            }
            j += 2;
            k -= 2;
        }

        //the following loop draws lines from 17 to 26
        for (int i = 0; i < COLS; i++) {
            line[16].append(DASH);
            for (int q = 17; q < 26; q++) {
                line[q].append(SPACE);
            }
            line[26].append(DASH);
        }

        //the following loop draws lines from 43 to 27
        j = 0;
        k = COLS;
        for (int i = ROWS - 1; i >= 27; i--) {
            for (int m = 0; m < j; m++) {
                line[i].append(SPACE);
            }
            line[i].append(BSLASH);
            for (int n = j + 1; n < k - 1; n++) {
                line[i].append(SPACE);
            }
            line[i].append(SLASH);
            for (int p = k; p < COLS; p++) {
                line[i].append(SPACE);
            }
            j += 2;
            k -= 2;
        }

        for (int i = 0; i < ROWS; i++) {
            line[i].setCharAt(31, EXCLAM);
            line[i].setCharAt(44, EXCLAM);
        }

        char[] letters = chartName.toCharArray();
        line[21].setCharAt(36, letters[0]);
        line[21].setCharAt(37, letters[1]);
        line[21].setCharAt(38, letters[2]);
        line[21].setCharAt(39, letters[3]);
    }//end drawLines

    private static void fill(ArrayList house, int start, int end, int row, int col) {
        int rowPos = row;
        int colPos = col;

        for (int i = start; i < end; i++) {
            colPos = col;
            String name = (String) house.get(i);
            for (int j = 0; j < name.length(); j++) {
                line[rowPos].setCharAt(colPos, name.charAt(j));
                colPos++;
            }
            rowPos++;
        }
    } //end print

    private static void fillFirstHouse(ArrayList house) {
        if (house.isEmpty()) {
            return;
        }

        int start = 0;
        int end = house.size();
        int row = (16 - house.size()) / 2;
        int col = 33;

        fill(house, start, end, row, col);
    }//end fillFirstHouse

    private static void fillSecondHouse(ArrayList house) {
        if (house.isEmpty()) {
            return;
        }

        int start = 0;
        int end = house.size();
        int row = 0;
        int col = 0;

        if (end <= 7) {
            row = (7 - end) / 2;
            col = 14;
            fill(house, start, end, row, col);
        } else if (end <= 10) {
            row = (10 - end) / 2;
            col = 20;
            fill(house, start, end, row, col);
        } else if (end <= 15) {
            row = 0;
            col = 9;
            fill(house, start, 5, row, col);

            row = 0;
            col = 20;
            fill(house, 5, end, row, col);
        } else {
            row = 0;
            col = 9;
            fill(house, start, 5, row, col);

            row = 0;
            col = 20;
            fill(house, 5, 15, row, col);

            row = 10;
            col = 21;
            fill(house, 15, end, row, col);
        }
    } //end fillSecondHouse

    private static void fillThirdHouse(ArrayList house) {
        if (house.isEmpty()) {
            return;
        }

        int start = 0;
        int end = house.size();
        int row = 0;
        int col = 0;

        if (end <= 8) {
            row = 8 + (8 - end) / 2;
            col = 6;
            fill(house, start, end, row, col);
        } else if (end <= 11) {
            row = 5 + (11 - end) / 2;
            col = 0;
            fill(house, start, end, row, col);
        } else {
            row = 5;
            col = 0;
            fill(house, start, 11, row, col);

            row = 11;
            col = 11;
            fill(house, 11, end, row, col);
        }
    } //end fillThirdHouse

    private static void fillFourthHouse(ArrayList house) {
        if (house.isEmpty()) {
            return;
        }

        int start = 0;
        int end = house.size();
        int row = 0;
        int col = 0;

        if (end <= 9) {
            row = 17 + (9 - end) / 2;
            col = 14;
            fill(house, start, end, row, col);
        } else {
            row = 17;
            col = 9;
            fill(house, start, 9, row, col);

            row = 17 + (18 - end) / 2;
            col = 20;
            fill(house, 9, end, row, col);
        }
    } //end fillFourthHouse

    private static void fillFifthHouse(ArrayList house) {
        if (house.isEmpty()) {
            return;
        }

        int start = 0;
        int end = house.size();
        int row = 0;
        int col = 0;

        if (end <= 8) {
            row = 27 + (8 - end) / 2;
            col = 5;
            fill(house, start, end, row, col);
        } else if (end <= 11) {
            row = 27 + (11 - end) / 2;
            col = 0;
            fill(house, start, end, row, col);
        } else {
            row = 27;
            col = 0;
            fill(house, start, 11, row, col);

            row = 27;
            col = 11;
            fill(house, 11, end, row, col);
        }
    }//end fillFifthHouse

    private static void fillSixthHouse(ArrayList house) {
        if (house.isEmpty()) {
            return;
        }

        int start = 0;
        int end = house.size();
        int row = 0;
        int col = 0;

        if (end <= 7) {
            row = 36 + (7 - end) / 2;
            col = 14;
            fill(house, start, end, row, col);
        } else if (end <= 10) {
            row = 33 + (10 - end) / 2;
            col = 20;
            fill(house, start, end, row, col);
        } else if (end <= 15) {
            row = 33;
            col = 20;
            fill(house, start, 10, row, col);

            row = 38;
            col = 9;
            fill(house, 10, end, row, col);
        } else {
            row = 32;
            col = 21;
            fill(house, start, 1, row, col);

            row = 33;
            col = 20;
            fill(house, 1, 11, row, col);

            row = 38;
            col = 9;
            fill(house, 11, end, row, col);
        }
    } //end fillSixthHouse

    private static void fillSeventhHouse(ArrayList house) {
        if (house.isEmpty()) {
            return;
        }

        int start = 0;
        int end = house.size();
        int row = 0;
        int col = 0;

        row = 27 + (16 - house.size()) / 2;
        col = 33;
        fill(house, start, end, row, col);
    }//end fillSeventhHouse

    private static void fillEighthHouse(ArrayList house) {
        if (house.isEmpty()) {
            return;
        }

        int start = 0;
        int end = house.size();
        int row = 0;
        int col = 0;

        if (end <= 7) {
            row = 36 + (7 - end) / 2;
            col = 51;
            fill(house, start, end, row, col);
        } else if (end <= 10) {
            row = 33 + (10 - end) / 2;
            col = 46;
            fill(house, start, end, row, col);
        } else if (end <= 15) {
            row = 33;
            col = 46;
            fill(house, start, 10, row, col);

            row = 38;
            col = 57;
            fill(house, 10, end, row, col);
        } else {
            row = 32;
            col = 45;
            fill(house, start, 1, row, col);

            row = 33;
            col = 46;
            fill(house, 1, 11, row, col);

            row = 38;
            col = 57;
            fill(house, 11, end, row, col);
        }
    } //end fillEighthHouse

    private static void fillNinthHouse(ArrayList house) {
        if (house.isEmpty()) {
            return;
        }

        int start = 0;
        int end = house.size();
        int row = 0;
        int col = 0;

        if (end <= 8) {
            row = 27 + (8 - end) / 2;
            col = 60;
            fill(house, start, end, row, col);
        } else if (end <= 11) {
            row = 27 + (11 - end) / 2;
            col = 66;
            fill(house, start, end, row, col);
        } else {
            row = 27;
            col = 55;
            fill(house, start, 5, row, col);

            row = 27;
            col = 66;
            fill(house, 5, end, row, col);
        }
    }//end fillNinthHouse

    private static void fillTenthHouse(ArrayList house) {
        if (house.isEmpty()) {
            return;
        }

        int start = 0;
        int end = house.size();
        int row = 0;
        int col = 0;

        if (end <= 9) {
            row = 17 + (9 - end) / 2;
            col = 60;
            fill(house, start, end, row, col);
        } else {
            row = 17;
            col = 57;
            fill(house, start, 9, row, col);

            row = 17 + (18 - end) / 2;
            col = 46;
            fill(house, 9, end, row, col);
        }
    } //end fillTenthHouse

    private static void fillEleventhHouse(ArrayList house) {
        if (house.isEmpty()) {
            return;
        }

        int start = 0;
        int end = house.size();
        int row = 0;
        int col = 0;

        if (end <= 8) {
            row = 8 + (8 - end) / 2;
            col = 60;
            fill(house, start, end, row, col);
        } else if (end <= 11) {
            row = 5 + (11 - end) / 2;
            col = 66;
            fill(house, start, end, row, col);
        } else {
            row = 5;
            col = 66;
            fill(house, start, 11, row, col);

            row = 11;
            col = 55;
            fill(house, 11, end, row, col);
        }
    } //end fillEleventhHouse

    private static void fillTwelfthHouse(ArrayList house) {
        if (house.isEmpty()) {
            return;
        }

        int start = 0;
        int end = house.size();
        int row = 0;
        int col = 0;

        if (end <= 7) {
            row = (7 - end) / 2;
            col = 51;
            fill(house, start, end, row, col);
        } else if (end <= 10) {
            row = (10 - end) / 2;
            col = 46;
            fill(house, start, end, row, col);
        } else if (end <= 15) {
            row = 0;
            col = 46;
            fill(house, start, 10, row, col);

            row = 0;
            col = 57;
            fill(house, 10, end, row, col);
        } else {
            row = 0;
            col = 46;
            fill(house, start, 10, row, col);

            row = 0;
            col = 57;
            fill(house, 10, 15, row, col);

            row = 10;
            col = 45;
            fill(house, 15, end, row, col);
        }
    } //end fillTwelfthHouse
}
