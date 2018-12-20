import Framework.Board;
import Framework.Position;
import Framework.Unit;
import org.junit.Before;
import org.junit.Test;
import standard.BoardImpl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class VanilaTicTacToeTest {
    private Board board;

    @Before
    public void setUp() {
        board = new BoardImpl();
    }

    @Test
    public void crossShouldStartWithTheTurn() {
        assertThat(board.getPlayerInTurn(), is(Unit.CROSS));
    }

    @Test
    public void afterEndOfTurnThePlayerInTurnShouldBeCircle() {
        board.endTurn();
        assertThat(board.getPlayerInTurn(), is(Unit.CIRCLE));
    }

    @Test
    public void middleShouldReturnTrueWhenNothingIsMarked() {
        assertThat(placeUnit(1,1), is(true));
    }

    @Test
    public void middleShouldReturnFalseWhenItIsAlreadyMarked() {
        placeUnit(1,1);
        assertThat(placeUnit(1,1), is(false));
    }

    @Test
    public void whenCrossPlacesAMarkItShouldBeMarkedCross() {
        Position pos = new Position(1,1);
        board.markField(pos);
        assertThat(board.getUnit(pos), is(Unit.CROSS));
    }

    @Test
    public void whenCirclePlacesMarkItShouldBeMarkedCircle() {
        board.endTurn();
        placeUnit(1,1);
        assertThat(board.getUnit(new Position(1,1)), is(Unit.CIRCLE));
    }

    @Test
    public void whenSomeOneMarksSuccessfullyPlayerInTurnShouldChange() {
        assertThat(placeUnit(1,1), is(true));
        assertThat(board.getPlayerInTurn(), is(Unit.CIRCLE));
    }

    @Test
    public void thereShouldBeNoWinnerAtTheStart() {
        assertThat(board.getWinner(), is(Unit.NONE));
    }

    @Test
    public void whenOnePlayerHas102030() {
        assertThat(placeUnit(0,0), is(true));
        assertThat(placeUnit(0,1), is(true));
        assertThat(board.getWinner(), is(Unit.NONE));
        assertThat(placeUnit(1,0), is(true));
        assertThat(placeUnit(1,1), is(true));
        assertThat(board.getWinner(), is(Unit.NONE));
        assertThat(placeUnit(2,0), is(true));
        assertThat(board.getWinner(), is(Unit.CROSS));
        assertThat(placeUnit(2,1), is(false));
    }

    //Util methods
    private boolean placeUnit(int r, int c) {
        return board.markField(new Position(r,c));
    }
}
