package javaapplication1;

import java.util.Scanner;
import java.util.Arrays;

public class JavaApplication1 {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        String[][] grid = {
            {"--------------------------------------------------------------------------------------------------------------"},
            {"players", " ", "2", "3", "4", "5", "6", "7",
                "8", "9", "10", "11", "12", "scores"},
            {"player1", " ", "x", "x", "x", "x", "x", "x",
                "x", "x", "x", "x", "x", "0"},
            {"player2", " ", "x", "x", "x", "x", "x", "x",
                "x", "x", "x", "x", "x", "0"},
            {"player3", " ", "x", "x", "x", "x", "x", "x",
                "x", "x", "x", "x", "x", "0"},
            {"--------------------------------------------------------------------------------------------------------------"}
        };

        int[][] numbers = new int[3][11];
        int[] scores = new int[3];

        int turn = 0;

        while (turn < 33) {

            int player = turn % 3;
            int row = player + 2;

            System.out.println("\nPlayer " + (player + 1) + "'s turn");

            int dice1 = (int) (Math.random() * 6) + 1;
            int dice2 = (int) (Math.random() * 6) + 1;

            int roll = dice1 + dice2;

            System.out.println("The dice rolled " + roll);
            System.out.println("Where would you like to place it?");

            int column;

            while (true) {

                column = in.nextInt();

                if (column < 2 || column > 12) {
                    System.out.println(
                        "Put a correct column number this time:"
                    );
                } else if (!grid[row][column].equals("x")) {
                    System.out.println(
                        "You already used that column. Choose another:"
                    );
                } else {
                    break;
                }
            }

            grid[row][column] = String.valueOf(roll);
            numbers[player][column - 2] = roll;

            for (int r = 0; r < grid.length; r++) {
                for (int c = 0; c < grid[r].length; c++) {
                    System.out.print(grid[r][c] + "\t");
                }
                System.out.println();
            }

            // check whether all three players have filled this column

            boolean full = true;

            for (int p = 0; p < 3; p++) {
                if (grid[p + 2][column].equals("x")) {
                    full = false;
                }
            }

            if (full) {

                int highest = 0;
                int winner = -1;
                boolean tie = false;

                for (int p = 0; p < 3; p++) {

                    int number = numbers[p][column - 2];

                    if (number > highest) {
                        highest = number;
                        winner = p;
                        tie = false;
                    } else if (number == highest) {
                        tie = true;
                    }
                }

                if (!tie) {
                    scores[winner] += column;
                    grid[winner + 2][13] =
                        String.valueOf(scores[winner]);

                    System.out.println(
                        "Player " + (winner + 1)
                        + " wins column " + column
                        + " and gets " + column + " points!"
                    );
                } else {
                    System.out.println(
                        "Column " + column
                        + " is a tie! Nobody gets points."
                    );
                }
            }

            turn++;
        }

        System.out.println("\nGAME OVER!");

        for (int p = 0; p < 3; p++) {
            System.out.println(
                "Player " + (p + 1)
                + "'s final score: " + scores[p]
            );
        }

        int highestScore = Arrays.stream(scores).max().getAsInt();
        int winnerCount = 0;

        for (int p = 0; p < 3; p++) {
            if (scores[p] == highestScore) {
                winnerCount++;
            }
        }

        if (winnerCount > 1) {
            System.out.println("The game ends in a tie!");
        } else {
            for (int p = 0; p < 3; p++) {
                if (scores[p] == highestScore) {
                    System.out.println(
                        "Player " + (p + 1) + " is the winner!"
                    );
                }
            }
        }

        in.close();
    }
}