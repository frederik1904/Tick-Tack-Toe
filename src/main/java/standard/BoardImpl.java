package standard;

import Framework.Position;
import Framework.Unit;

import java.util.HashMap;

public class BoardImpl implements Framework.Board {
    private HashMap<Position, Unit> unitMap = new HashMap<>();

    @Override
    public Unit getUnit(Position pos) {
        return unitMap.get(pos);
    }

    @Override
    public boolean markField(Position pos) {
        if (getUnit(pos) != null) {
            return false;
        }
        unitMap.put(pos, getPlayerInTurn());
        return true;
    }

    @Override
    public void endTurn() {

    }

    @Override
    public Unit getPlayerInTurn() {
        return Unit.CROSS;
    }

    @Override
    public Unit getWinner() {
        return null;
    }

    @Override
    public void resetBoard() {

    }
}
