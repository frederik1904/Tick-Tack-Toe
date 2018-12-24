package standard.playerStrategy;

import Framework.*;
import Framework.playerStrategy.PlayerStrategy;
import Framework.playerStrategy.SelectFromPoints;
import standard.playerStrategy.selectFromPointsStrategy.SelectFromPointsTotalRandom;

import java.util.*;

import static Framework.GameConstants.BOARD_SIZE;

public class PlayerStrategyAIAggressiveSimple implements PlayerStrategy {
    private Stack<Position> winnerPos;
    private Stack<Position> loserPos;
    private Map<Position, Integer> pointMap = new HashMap<>();
    private SelectFromPoints selectFromPoints = new SelectFromPointsTotalRandom();

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

        if (!winnerPos.empty()) {
            Position pos = winnerPos.pop();
            board.markField(pos);
            return;
        }
        if (!loserPos.empty()) {
            Position pos = loserPos.pop();
            board.markField(pos);
            return;
        }

        selectFromPoints.selectField(board, pointMap);
    }

    @SuppressWarnings("Duplicates")
    private void calculatePoints(int row, int col, Board board) {
        if (board.getUnit(new Position(row, col)) != null) {
            return;
        }

        int     points = 0,
                enemyRow = 0, enemyCol = 0,
                friendlyRow = 0, friendlyCol = 0,
                diagRFriendly = 0, diagREnemy = 0,
                diagLFriendly = 0, diagLEnemy = 0;

        for (int i = 0; i < BOARD_SIZE; i++) {
            Unit boardUnit = board.getUnit(new Position(row, i));
            if (isEnemyUnit(board, boardUnit)) enemyRow++;
            if (isOwnUnit(board, boardUnit)) friendlyRow++;

            boardUnit = board.getUnit(new Position(i, col));
            if (isEnemyUnit(board, boardUnit)) enemyCol++;
            if (isOwnUnit(board, boardUnit)) friendlyCol++;

            if (col == BOARD_SIZE - 1 - row) {
                boardUnit = board.getUnit(new Position(i, i));
                if (isEnemyUnit(board, boardUnit)) diagREnemy++;
                if (isOwnUnit(board, boardUnit)) diagRFriendly++;
            }

            if (col == BOARD_SIZE - 1 - row) {
                boardUnit = board.getUnit(new Position(i, BOARD_SIZE - 1 - i));
                if (isEnemyUnit(board, boardUnit)) diagLEnemy++;
                if (isOwnUnit(board, boardUnit)) diagLFriendly++;
            }
        }

        if (enemyRow == 0) points++;
        if (enemyCol == 0) points++;

        if (friendlyRow == BOARD_SIZE - 1) winnerPos.add(new Position(row, col));
        if (friendlyCol == BOARD_SIZE - 1) winnerPos.add(new Position(row, col));
        if (enemyCol == BOARD_SIZE - 1 || enemyRow == BOARD_SIZE - 1) loserPos.add(new Position(row, col));

        if (row == col) {
            if (diagREnemy == 0) points++;
            if (diagRFriendly == BOARD_SIZE - 1) winnerPos.add(new Position(row, col));
            if (diagREnemy == BOARD_SIZE - 1) loserPos.add(new Position(row, col));
        }

        if (col == BOARD_SIZE - 1 - row) {
            if (diagLEnemy == 0) points++;
            if (diagLFriendly == BOARD_SIZE - 1) winnerPos.add(new Position(row, col));
            if (diagLEnemy == BOARD_SIZE - 1) loserPos.add(new Position(row, col));
        }

        pointMap.put(new Position(row, col), points);
    }

    private boolean isOwnUnit(Board board, Unit boardUnit) {
        return board.getPlayerInTurn().equals(boardUnit);
    }

    private boolean isEnemyUnit(Board board, Unit boardUnit) {
        return !isOwnUnit(board, boardUnit) && boardUnit != null;
    }
}
