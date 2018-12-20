package standard;

import Framework.GameConstants;
import Framework.Position;
import Framework.Unit;

import java.util.HashMap;

public class BoardImpl implements Framework.Board {
    private HashMap<Position, Unit> unitMap = new HashMap<>();
    private Unit unitInTurn = Unit.CROSS;
    private Unit winner = Unit.NONE;
    @Override
    public Unit getUnit(Position pos) {
        return unitMap.get(pos);
    }

    @Override
    public boolean markField(Position pos) {
        if (!getWinner().equals(Unit.NONE)) {
            return false;
        }
        if (getUnit(pos) != null) {
            return false;
        }
        unitMap.put(pos, getPlayerInTurn());

        int row = pos.getRow(), col = pos.getColumn(), straight = 0;
        for (int i = 0; i < GameConstants.BOARD_SIZE; i++) {
            if (getUnit(new Position(i, col)) == getPlayerInTurn()) {
                straight++;
            }
        }

        if (straight == 3) {
            winner = getPlayerInTurn();
        }

        endTurn();
        return true;
    }

    @Override
    public void endTurn() {
        unitInTurn = unitInTurn.equals(Unit.CROSS) ? Unit.CIRCLE : Unit.CROSS;
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

    }
}
