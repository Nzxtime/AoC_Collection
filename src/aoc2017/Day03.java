package aoc2017;

import misc.Point;

import java.util.*;

public class Day03 {
    public static void main(String[] args) {
        System.out.println(problem1(347991));
        System.out.println(problem2(347991));
    }

    private static int problem1(int input) {
        int nearestSquareFactor = (int) Math.ceil(Math.sqrt(input));
        int nearestSquare = (int) Math.pow(nearestSquareFactor, 2);
        int cornerNumber = (nearestSquare - nearestSquareFactor + 1);

        Point maxPoint = new Point();
        Point cornerPoint = new Point();
        Point myPoint = new Point();

        if (nearestSquareFactor % 2 == 0) {
            maxPoint.setX(-(nearestSquareFactor / 2) + 1);
            maxPoint.setY(-(nearestSquareFactor / 2));
            cornerPoint.setX((nearestSquareFactor / 2));
            cornerPoint.setY(-(nearestSquareFactor / 2));
        } else if (nearestSquareFactor % 2 == 1) {
            maxPoint.setX((nearestSquareFactor / 2));
            maxPoint.setY((nearestSquareFactor / 2));
            cornerPoint.setX(-(nearestSquareFactor / 2));
            cornerPoint.setY((nearestSquareFactor / 2));
        }

        if (nearestSquare >= input && cornerNumber <= input) {
            myPoint.setX(maxPoint.getX() + Math.abs(nearestSquare - input));
            myPoint.setY(maxPoint.getY());
        } else {
            myPoint.setX(cornerPoint.getX());
            myPoint.setY(maxPoint.getY() + Math.abs(cornerNumber - input));
        }

        return myPoint.getManhattenDistance();
    }

    private static int problem2(int input) {
        Map<Point, Integer> map = new HashMap<>();
        map.put(new Point(0, 0), 1);
        Point position = new Point(0, 0);
        int lastNumber = 0;
        int index = 0;

        while (lastNumber < input) {
            if (!map.containsKey(position)) {
                lastNumber = 0;
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (i != 0 || j != 0) {
                            Point tempPoint = new Point(position.getX() + i, position.getY() + j);
                            Integer temp = map.get(tempPoint);
                            if (temp != null) lastNumber += temp;
                        }
                    }
                }
                map.put(new Point(position), lastNumber);
            }
            updatePosition(position, index++);
        }
        return lastNumber;
    }

    private static void updatePosition(Point position, int index) {
        index = index + 1;
        int nearestSquareFactor = (int) Math.ceil(Math.sqrt(index));
        int nearestSquare = (int) Math.pow(nearestSquareFactor, 2);
        int cornerNumber = (nearestSquare - nearestSquareFactor + 1);

        if (nearestSquareFactor % 2 == 0) { // moving on right and top side
            if (index == cornerNumber || index == nearestSquare) position.decX();

            else if (nearestSquare > index && cornerNumber <= index) {
                position.decX();
            } else {
                position.incY();
            }
        } else if (nearestSquareFactor % 2 == 1) { // moving on left and right side
            if (index == cornerNumber || index == nearestSquare) position.incX();
            else if (nearestSquare > index && cornerNumber <= index) {
                position.incX();
            } else {
                position.decY();
            }
        }
    }
}
