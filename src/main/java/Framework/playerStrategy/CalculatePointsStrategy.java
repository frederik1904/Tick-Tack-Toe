package Framework.playerStrategy;

import Framework.Board;

public interface CalculatePointsStrategy {
    public static final int WINNER_POS = -1;
    public static final int LOSER_POS = -2;

    int CalculatePoints(int row, int col, Board board);
}
