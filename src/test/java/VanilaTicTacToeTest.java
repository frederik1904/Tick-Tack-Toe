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
    public void CrossShouldStartWithTheTurn() {
        assertThat(board.getPlayerInTurn(), is(Unit.CROSS));
    }



    @Test
    public void middleShouldReturnTrueWhenNothingIsMarked() {
        assertThat(board.markField(new Position(1,1)), is(true));
    }

    @Test
    public void middleShouldReturnFalseWhenItIsAlreadyMarked() {
        Position pos = new Position(1,1);
        board.markField(pos);
        assertThat(board.markField(pos), is(false));
    }

    @Test
    public void whenCrossPlacesAMarkItShouldBeMarkedCross() {
        Position pos = new Position(1,1);
        board.markField(pos);
        assertThat(board.getUnit(pos), is(Unit.CROSS));
    }



}
