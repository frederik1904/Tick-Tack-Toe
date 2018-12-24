package Framework.playerStrategy;

import Framework.Board;

public interface PlayerStrategy {

    /**
     * Takes the action for the player in turn.
     */
    void makePlay(Board board);
}
