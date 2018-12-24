package standard;

import Framework.*;
import Framework.playerStrategy.PlayerStrategy;
import standard.moveUnitStrategy.MoveUnitStrayegyTickTackToe;

import java.util.ArrayList;
import java.util.HashMap;

import static Framework.GameConstants.BOARD_SIZE;
import static Framework.Unit.*;

public class BoardImpl implements Framework.Board, Observable {
    private HashMap<Position, Unit> unitMap = new HashMap<>();
    private Unit unitInTurn = CROSS;
    private Unit winner = NONE;
    private ArrayList<Observer> observers = new ArrayList<>();
    private PlayerStrategy playerOne, playerTwo;
    private MoveUnitStrayegy moveUnitStrayegy;


    public BoardImpl(PlayerStrategy playerOne, PlayerStrategy playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.moveUnitStrayegy = new MoveUnitStrayegyTickTackToe();
    }

    @Override
    public Unit getUnit(Position pos) {
        return unitMap.get(pos);
    }

    @Override
    public boolean markField(Position pos) {
        boolean canBePlaced = moveUnitStrayegy.canPlaceUnit(pos, this);
        if(canBePlaced) {
            unitMap.put(pos, getPlayerInTurn());

            isGameWon(pos);

            endTurn();
        }

        return canBePlaced;
    }

    @Override
    public boolean moveField(Position from, Position to) {
        return moveUnitStrayegy.canMoveUnit(from, to, this);
    }

    private void isGameWon(Position pos) {
        int row = pos.getRow(), col = pos.getColumn(),
                rowUnits = 0, colUnits = 0,
                diagUnitsOne = 0, diagUnitsTwo = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (getUnit(new Position(i, col)) == getPlayerInTurn()) rowUnits++;
            if (getUnit(new Position(row, i)) == getPlayerInTurn()) colUnits++;
            if (getUnit(new Position(i, i)) == getPlayerInTurn()) diagUnitsOne++;
            if (getUnit(new Position(i, BOARD_SIZE - 1 - i)) == getPlayerInTurn()) diagUnitsTwo++;
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
        notifyUpdate();
        Thread.yield();

        if (getPlayerInTurn().equals(CROSS)) {
            unitInTurn = CIRCLE;
            playerOne.makePlay(this);
        } else {
            unitInTurn = CROSS;
            playerTwo.makePlay(this);
        }
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
        winner = NONE;
        unitInTurn = CIRCLE;
    }

    @Override
    public void attach(Observer o) {
        observers.add(o);
    }

    @Override
    public void detach(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyUpdate() {
        observers.forEach(Observer::update);
    }
}
