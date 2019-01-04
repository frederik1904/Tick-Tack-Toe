package standard.playerStrategy.selectFromPointsStrategy;

import Framework.Board;
import Framework.Position;
import Framework.Unit;
import Framework.playerStrategy.CalculatePointsStrategy;

import static Framework.GameConstants.BOARD_SIZE;

@SuppressWarnings("Duplicates")
public class CalculatePointsStrategyAggrasive implements CalculatePointsStrategy {
    @Override
    public int CalculatePoints(int row, int col, Board board) {
        int     points = 0,
                enemyRow = 0, enemyCol = 0,
                friendlyRow = 0, friendlyCol = 0,
                diagRFriendly = 0, diagREnemy = 0,
                diagLFriendly = 0, diagLEnemy = 0;

        boolean winnerPos = false, loserPos = false;

        for (int i = 0; i < BOARD_SIZE; i++) {
            Unit boardUnit = board.getUnit(new Position(row, i));
            if (isEnemyUnit(board, boardUnit)) enemyRow++;
            if (isOwnUnit(board, boardUnit)) friendlyRow++;

            boardUnit = board.getUnit(new Position(i, col));
            if (isEnemyUnit(board, boardUnit)) enemyCol++;
            if (isOwnUnit(board, boardUnit)) friendlyCol++;

            if (col == row) {
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

        if (friendlyRow == BOARD_SIZE - 1 || friendlyCol == BOARD_SIZE - 1) winnerPos = true;
        if (enemyCol == BOARD_SIZE - 1 || enemyRow == BOARD_SIZE - 1) loserPos = true;

        if (row == col) {
            if (diagREnemy == 0) points++;
            if (diagRFriendly == BOARD_SIZE - 1) winnerPos = true;
            if (diagREnemy == BOARD_SIZE - 1) loserPos = true;
        }

        if (col == BOARD_SIZE - 1 - row) {
            if (diagLEnemy == 0) points++;
            if (diagLFriendly == BOARD_SIZE - 1) winnerPos = true;
            if (diagLEnemy == BOARD_SIZE - 1) loserPos = true;
        }

        if (winnerPos) {
            return CalculatePointsStrategy.WINNER_POS;
        }
        if (loserPos) {
            return CalculatePointsStrategy.LOSER_POS;
        }
        return points;
    }
    private boolean isOwnUnit(Board board, Unit boardUnit) {
        return board.getPlayerInTurn().equals(boardUnit);
    }

    private boolean isEnemyUnit(Board board, Unit boardUnit) {
        return !isOwnUnit(board, boardUnit) && boardUnit != null;
    }
}
