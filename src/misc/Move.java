package misc;

import java.util.ArrayList;
import java.util.List;

public class Move {
    private char direction;
    private int moves;

    public Move(char direction, int moves) {
        this.direction = direction;
        this.moves = moves;
    }

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    public static List<Move> convertToMoveList(String[] input) {
        List<Move> output = new ArrayList<>();
        for (String s : input) {
            output.add(new Move(s.charAt(0), Integer.parseInt(s.substring(1))));
        }
        return output;
    }

    @Override
    public String toString() {
        return String.format("%c:%d", this.direction, this.moves);
    }
}