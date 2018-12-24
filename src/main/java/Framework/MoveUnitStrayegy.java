package Framework;

public interface MoveUnitStrayegy {
    /**
     * Places a unit on the board if this is not possible it should do nothing and return false.
     * @param pos the position to move the unit to.
     * @param board the board to change.
     * @return weather the unit could be placed or not.
     */
    boolean canPlaceUnit(Position pos, Board board);

    /**
     * moves a unit from one position to another if this is possible else it does nothing and returns false.
     * @param from the position to move from.
     * @param to the position to move to.
     * @param board the board to change.
     * @return weather the unit could be moved or not.
     */
    boolean canMoveUnit(Position from, Position to, Board board);
}
