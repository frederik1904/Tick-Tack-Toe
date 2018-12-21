package standard;

import Framework.*;

import java.util.ArrayList;
import java.util.HashMap;

import static Framework.GameConstants.BOARD_SIZE;
import static Framework.Unit.*;

public class BoardImpl implements Framework.Board, Observable {
    private HashMap<Position, Unit> unitMap = new HashMap<>();
    private Unit unitInTurn = CROSS;
    private Unit winner = NONE;
    private PlayerStrategy playerStrategy;
    private ArrayList<Observer> observers = new ArrayList<>();

    public BoardImpl(PlayerStrategy playerStrategy) {
        this.playerStrategy = playerStrategy;
    }

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
        System.out.println(pos);
        int row = pos.getRow(), col = pos.getColumn(),
                rowUnits = 0, colUnits = 0,
                diagUnitsOne = 0, diagUnitsTwo = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (getUnit(new Position(i, col)) == getPlayerInTurn()) rowUnits++;
            if (getUnit(new Position(row, i)) == getPlayerInTurn()) colUnits++;
            if (getUnit(new Position(i, i)) == getPlayerInTurn()) diagUnitsOne++;
            if (getUnit(new Position(BOARD_SIZE - 1 - i, BOARD_SIZE - 1 - i)) == getPlayerInTurn()) diagUnitsTwo++;
        }

        if (rowUnits == BOARD_SIZE
                || colUnits == BOARD_SIZE
                || diagUnitsOne == BOARD_SIZE
                || diagUnitsTwo == BOARD_SIZE) {
            winner = getPlayerInTurn();
        } else if (unitMap.keySet().size() == BOARD_SIZE * BOARD_SIZE) {
            winner = TIE;
        }
    }

    @Override
    public void endTurn() {
        if (getPlayerInTurn().equals(CROSS)) {
            unitInTurn = CIRCLE;
            playerStrategy.makePlay(this);
        } else {
            unitInTurn = CROSS;
        }
        notifyUpdate();
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

    @Override
    public void attatch(Observer o) {
        observers.add(o);
    }

    @Override
    public void detatch(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyUpdate() {
        observers.forEach(Observer::update);
    }
}
