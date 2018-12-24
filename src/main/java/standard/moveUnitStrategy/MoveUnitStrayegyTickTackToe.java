package standard.moveUnitStrategy;

import Framework.Board;
import Framework.Position;
import Framework.MoveUnitStrayegy;

import static Framework.Unit.NONE;

public class MoveUnitStrayegyTickTackToe implements MoveUnitStrayegy {
    @Override
    public boolean canPlaceUnit(Position pos, Board board) {
        if (!board.getWinner().equals(NONE)) {
            return false;
        }
        if (board.getUnit(pos) != null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean canMoveUnit(Position from, Position to, Board board) {
        return false;
    }
}
