import Framework.Board;
import Framework.GameConstants;
import Framework.Position;
import Framework.Unit;
import org.junit.Before;
import org.junit.Test;
import standard.BoardImpl;

import static Framework.Unit.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class VanilaTicTacToeTest {
    private Board board;

    @Before
    public void setUp() {
        board = new BoardImpl();
    }

    @Test
    public void crossShouldStartWithTheTurn() {
        assertThat(board.getPlayerInTurn(), is(CROSS));
    }

    @Test
    public void afterEndOfTurnThePlayerInTurnShouldBeCircle() {
        board.endTurn();
        assertThat(board.getPlayerInTurn(), is(CIRCLE));
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
        assertThat(board.getUnit(pos), is(CROSS));
    }

    @Test
    public void whenCirclePlacesMarkItShouldBeMarkedCircle() {
        board.endTurn();
        placeUnit(1,1);
        assertThat(board.getUnit(new Position(1,1)), is(CIRCLE));
    }

    @Test
    public void whenSomeOneMarksSuccessfullyPlayerInTurnShouldChange() {
        assertThat(placeUnit(1,1), is(true));
        assertThat(board.getPlayerInTurn(), is(CIRCLE));
    }

    @Test
    public void thereShouldBeNoWinnerAtTheStart() {
        assertThat(board.getWinner(), is(NONE));
    }

    @Test
    public void whenOnePlayerHas001020RowVictory() {
        assertThat(placeUnit(0,0), is(true));
        assertThat(placeUnit(0,1), is(true));
        assertThat(board.getWinner(), is(NONE));
        assertThat(placeUnit(1,0), is(true));
        assertThat(placeUnit(1,1), is(true));
        assertThat(board.getWinner(), is(NONE));
        assertThat(placeUnit(2,0), is(true));
        assertThat(board.getWinner(), is(CROSS));
        assertThat(placeUnit(2,1), is(false));
    }

    @Test
    public void whenOnePlayerHas000102ColumnVictory() {
        assertThat(placeUnit(0,0), is(true));
        assertThat(placeUnit(1,1), is(true));
        assertThat(board.getWinner(), is(NONE));
        assertThat(placeUnit(0,1), is(true));
        assertThat(placeUnit(1,2), is(true));
        assertThat(board.getWinner(), is(NONE));
        assertThat(placeUnit(0,2), is(true));
        assertThat(board.getWinner(), is(CROSS));
    }

    @Test
    public void whenOnePlayerHas001122DiagonalVidtory() {
        assertThat(placeUnit(0,0), is(true));
        assertThat(placeUnit(1,0), is(true));
        assertThat(board.getWinner(), is(NONE));
        assertThat(placeUnit(1,1), is(true));
        assertThat(placeUnit(1,2), is(true));
        assertThat(board.getWinner(), is(NONE));
        assertThat(placeUnit(2,2), is(true));
        assertThat(board.getWinner(), is(CROSS));
    }

    @Test
    public void whenBoardIsFulLWithNoWinnerTheWinnerIsTie() {
        assertThat(placeUnit(0,0), is(true));
        assertThat(placeUnit(0,2), is(true));
        assertThat(board.getWinner(), is(NONE));
        assertThat(placeUnit(0,1), is(true));
        assertThat(placeUnit(1,0), is(true));
        assertThat(board.getWinner(), is(NONE));
        assertThat(placeUnit(1,1), is(true));
        assertThat(placeUnit(2,2), is(true));
        assertThat(board.getWinner(), is(NONE));
        assertThat(placeUnit(1,2), is(true));
        assertThat(placeUnit(2,1), is(true));
        assertThat(board.getWinner(), is(NONE));
        assertThat(placeUnit(2,0), is(true));
        assertThat(board.getWinner(), is(TIE));
    }

    @Test
    public void theBoardShouldNotContainAnyUnitsWhenGameIsReset() {
        assertThat(placeUnit(1,1), is(true));
        board.resetBoard();
        assertThat(board.getUnit(new Position(1,1)), is(nullValue()));
    }

    //Util methods
    private boolean placeUnit(int r, int c) {
        return board.markField(new Position(r,c));
    }

    private void printBoard() {
        for (int i = 0; i < GameConstants.BOARD_SIZE; i++) {
            System.out.print("|");
            for (int j = 0; j < GameConstants.BOARD_SIZE; j++) {
                System.out.print(convertUnit(board.getUnit(new Position(i,j))));
            }
            System.out.println();
        }
        System.out.println();
    }

    private String convertUnit(Unit u) {
        String result = " ";

        if (null == u) {
            result += "-";
        } else {
            switch (u) {
                case CIRCLE:
                    result += "O";
                    break;
                case CROSS:
                    result += "X";
                    break;
            }
        }

        return result += " |";
    }
}
