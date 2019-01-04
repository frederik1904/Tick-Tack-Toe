package standard.playerStrategy;

import Framework.*;
import Framework.playerStrategy.CalculatePointsStrategy;
import Framework.playerStrategy.PlayerStrategy;
import Framework.playerStrategy.SelectFromPoints;
import standard.playerStrategy.selectFromPointsStrategy.CalculatePointsStrategyAggrasive;
import standard.playerStrategy.selectFromPointsStrategy.CalculatePointsStrategyDefensive;
import standard.playerStrategy.selectFromPointsStrategy.SelectFromPointsRandomWithTopPoints;
import standard.playerStrategy.selectFromPointsStrategy.SelectFromPointsTotalRandom;

import java.util.*;

import static Framework.GameConstants.BOARD_SIZE;

public class PlayerStrategyBotSimple implements PlayerStrategy {
    private Stack<Position> winnerPos;
    private Stack<Position> loserPos;
    private Map<Position, Integer> pointMap = new HashMap<>();
    private SelectFromPoints selectFromPoints = new SelectFromPointsRandomWithTopPoints();
    private CalculatePointsStrategy calculatePointsStrategy = new CalculatePointsStrategyAggrasive();

    @Override
    public void makePlay(Board board) {
        winnerPos = new Stack<>();
        loserPos = new Stack<>();
        pointMap = new HashMap<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                calculatePoints(i, j, board);
            }
        }
        System.out.println(pointMap);
        if (!winnerPos.empty()) {
            System.out.println("winner");
            Position pos = winnerPos.pop();
            board.markField(pos);
            return;
        }

        if (!loserPos.empty()) {
            System.out.println("loser");
            Position pos = loserPos.pop();
            board.markField(pos);
            return;
        }
        System.out.println("points");
        selectFromPoints.selectField(board, pointMap);
    }

    @SuppressWarnings("Duplicates")
    private void calculatePoints(int row, int col, Board board) {
        if (board.getUnit(new Position(row, col)) != null) {
            return;
        }

        int points = calculatePointsStrategy.CalculatePoints(row, col, board);

        if (points == CalculatePointsStrategy.WINNER_POS) {
            winnerPos.add(new Position(row, col));
        }

        if (points == CalculatePointsStrategy.LOSER_POS) {
            loserPos.add(new Position(row, col));
        }

        pointMap.put(new Position(row, col), points);
    }


}
