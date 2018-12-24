package Framework;

public interface Board {
    /**
     * Returns the unit at the row and column given, if there is no unit there returns Unit.NONE
     * @param pos The position to get unit from
     * @return the owner of the unit at the place
     */
    Unit getUnit(Position pos);

    /**
     * Used to mark a field for the current user, if there is already another piece it returns false,
     * else true. it is a precondition that the piece is within the bounds.
     * @param pos The position to mark
     * @return weather the marking went well or not
     */
    boolean markField(Position pos);

    /**
     * Used to move a field from one place to another
     * @param from the position to move from
     * @param to the position to move to
     * @return weather the marking went well or not
     */
    boolean moveField(Position from, Position to);

    /**
     * Ends the turn and prompts the ai to take its turn
     */
    void endTurn();

    /**
     * Returns the current player
     * @return the current player
     */
    Unit getPlayerInTurn();

    /**
     * Returns the winner if there is any, if there is none yet it returns Unit.NONE or if it is a tie it returns
     * Unit.TIE
     * @return the winner
     */
    Unit getWinner();

    /**
     * sets the board to it's initial state
     */
    void resetBoard();
}
