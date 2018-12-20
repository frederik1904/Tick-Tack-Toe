package standard;

import Framework.GameConstants;
import Framework.Position;
import Framework.Unit;

import java.util.HashMap;

import static Framework.Unit.*;

public class BoardImpl implements Framework.Board {
    private HashMap<Position, Unit> unitMap = new HashMap<>();
    private Unit unitInTurn = CROSS;
    private Unit winner = NONE;

    @Override
    public Unit getUnit(Position pos) {
        return unitMap.get(pos);
    }

    @Override
    public boolean markField(Position pos) {
        if (!getWinner().equals(NONE)) {
            return false;
        }
        if (getUnit(pos) != null) {
            return false;
        }
        unitMap.put(pos, getPlayerInTurn());

        isGameWon(pos);

        endTurn();
        return true;
    }

    private void isGameWon(Position pos) {
        int row = pos.getRow(), col = pos.getColumn(),
                rowUnits = 0, colUnits = 0,
                diagUnitsOne = 0, diagUnitsTwo = 0;
        for (int i = 0; i < GameConstants.BOARD_SIZE; i++) {
            if (getUnit(new Position(i, col)) == getPlayerInTurn()) rowUnits++;
            if (getUnit(new Position(row, i)) == getPlayerInTurn()) colUnits++;
            if (getUnit(new Position(i, i)) == getPlayerInTurn()) diagUnitsOne++;
            if (getUnit(new Position(GameConstants.BOARD_SIZE - 1 - i, GameConstants.BOARD_SIZE - 1 - i)) == getPlayerInTurn()) diagUnitsTwo++;
        }

        if (rowUnits == GameConstants.BOARD_SIZE
                || colUnits == GameConstants.BOARD_SIZE
                || diagUnitsOne == GameConstants.BOARD_SIZE
                || diagUnitsTwo == GameConstants.BOARD_SIZE) {
            winner = getPlayerInTurn();
        } else if (unitMap.keySet().size() == GameConstants.BOARD_SIZE * GameConstants.BOARD_SIZE) {
            winner = TIE;
        }
    }

    @Override
    public void endTurn() {
        unitInTurn = unitInTurn.equals(CROSS) ? CIRCLE : CROSS;
    }

    @Override
    public Unit getPlayerInTurn() {
        return unitInTurn;
    }

    @Override
    public Unit getWinner() {
        return winner;
    }

    @Override
    public void resetBoard() {
        unitMap.clear();
    }
}
