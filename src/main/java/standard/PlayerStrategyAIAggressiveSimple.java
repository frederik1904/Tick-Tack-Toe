package standard;

import Framework.Board;
import Framework.PlayerStrategy;
import Framework.Position;
import Framework.Unit;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

import static Framework.GameConstants.BOARD_SIZE;

public class PlayerStrategyAI implements PlayerStrategy {
    private Stack<Position> winnerPos;
    private Stack<Position> loserPos;
    private Map<Position, Integer> pointMap = new HashMap<>();

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

        System.out.println(winnerPos);
        System.out.println(loserPos);
        System.out.println(pointMap);

        if (!winnerPos.empty()) {
            Position pos = winnerPos.pop();
            System.out.println("WINNING POSITION: " + pos);
            board.markField(pos);
            return;
        }
        if (!loserPos.empty()) {
            Position pos = loserPos.pop();
            System.out.println("LOSIONG POSITION: " + pos);
            board.markField(pos);
            return;
        }

        Position bestPos = null;
        int mostPoints = -1;
        for (Position pos : pointMap.keySet()) {
            if (pointMap.get(pos) > mostPoints) {
                bestPos = pos;
                mostPoints = pointMap.get(pos);
            }
        }
        System.out.println("BEST POSITION TO PLACE AT: " + bestPos + " WITH: " + mostPoints + "POINTS");
        board.markField(bestPos);
    }

    @SuppressWarnings("Duplicates")
    private void calculatePoints(int row, int col, Board board) {
        if (board.getUnit(new Position(row, col)) != null) {
            return;
        }

        int points = 0;
        int enemyRow = 0, enemyCol = 0, friendlyRow = 0, friendlyCol = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            Unit boardUnit = board.getUnit(new Position(row, i));
            if (!Objects.equals(boardUnit, board.getPlayerInTurn()) && boardUnit != null) enemyRow++;
            if (Objects.equals(boardUnit, board.getPlayerInTurn())) friendlyRow++;

            boardUnit = board.getUnit(new Position(i, col));
            if (!Objects.equals(boardUnit, board.getPlayerInTurn()) && boardUnit != null) enemyCol++;
            if (Objects.equals(boardUnit, board.getPlayerInTurn())) friendlyCol++;
        }

        if (enemyRow == 0) points++;
        if (enemyCol == 0) points++;

        if (friendlyRow == BOARD_SIZE - 1) winnerPos.add(new Position(row, col));
        if (friendlyCol == BOARD_SIZE - 1) winnerPos.add(new Position(row, col));
        if (enemyCol == BOARD_SIZE - 1 || enemyRow == BOARD_SIZE - 1) loserPos.add(new Position(row, col));

        int diagFriendly = 0, diagEnemy = 0;
        if (row == col) {
            for (int i = 0; i < BOARD_SIZE; i++) {
                Unit boardUnit = board.getUnit(new Position(i, i));
                if (!Objects.equals(boardUnit, board.getPlayerInTurn()) && boardUnit != null) diagEnemy++;
                if (Objects.equals(boardUnit, board.getPlayerInTurn())) diagFriendly++;
            }


            if (diagEnemy == 0) points++;
            if (diagFriendly == BOARD_SIZE - 1) winnerPos.add(new Position(row, col));
            if (diagEnemy == BOARD_SIZE - 1) loserPos.add(new Position(row, col));
        }

        if (col == BOARD_SIZE - 1 - row) {
            diagFriendly = 0;
            diagEnemy = 0;

            System.out.println("ROW: " + row + " COL: " + col);
            for (int i = 0; i < BOARD_SIZE; i++) {
                Unit boardUnit = board.getUnit(new Position(i, BOARD_SIZE - 1 - i));
                if (!Objects.equals(boardUnit, board.getPlayerInTurn()) && boardUnit != null) diagEnemy++;
                if (Objects.equals(boardUnit, board.getPlayerInTurn())) diagFriendly++;
            }


            if (diagEnemy == 0) points++;
            if (diagFriendly == BOARD_SIZE - 1) winnerPos.add(new Position(row, col));
            if (diagEnemy == BOARD_SIZE - 1) loserPos.add(new Position(row, col));
        }

        pointMap.put(new Position(row, col), points);
    }
}
