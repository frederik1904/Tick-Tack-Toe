package standard;

import Framework.Board;
import Framework.Observer;
import Framework.Position;
import Framework.Unit;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import standard.playerStrategy.PlayerStrategyAIAggressiveSimple;
import standard.playerStrategy.PlayerStrategyNone;

import java.util.HashMap;
import java.util.Map;

import static Framework.GameConstants.BOARD_SIZE;

public class VisualTickTackToe implements Observer {
    @FXML
    private VBox boxLayout;
    private Map<Position, Button> buttons;
    private Label crossWin = new Label(), circleWin = new Label(), tieWin = new Label();
    private int cross = 0, circle = 0, tie = 0;

    private Board board;
    public VisualTickTackToe() {
        board = new BoardImpl(new PlayerStrategyNone(), new PlayerStrategyAIAggressiveSimple());
        buttons = new HashMap<>();
        ((BoardImpl) board).attach(this);
    }

    public Board getBoard() {
        return board;
    }

    public void setUpBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            HBox hBox = new HBox();
            for (int j = 0; j < BOARD_SIZE; j++) {
                Button button = new Button();
                Unit u = board.getUnit(new Position(i,j));
                button.setText("" + (u == null ? "-" : u));
                int row = i, col = j;
                button.setOnMouseClicked(event -> {
                    Position pos = new Position(row, col);

                    if (board.markField(pos)) {
                        button.setText(getStringOfField(pos));
                    }
                });
                button.setFont(Font.font(Math.min(boxLayout.getWidth(), boxLayout.getHeight()) / BOARD_SIZE - 20));
                hBox.getChildren().add(button);
                buttons.put(new Position(row, col), button);
            }
            boxLayout.getChildren().add(hBox);
        }
        Button resetButton = new Button();
        resetButton.setText("RESET GAME");
        resetButton.setFont(Font.font(Math.min(boxLayout.getWidth(), boxLayout.getHeight()) / BOARD_SIZE - 20));
        resetButton.setOnMouseClicked(event -> {
            resetLayout();
        });
        boxLayout.getChildren().add(resetButton);

        HBox hbox = new HBox();
        hbox.getChildren().add(crossWin);
        hbox.getChildren().add(tieWin);
        hbox.getChildren().add(circleWin);
        boxLayout.getChildren().add(hbox);
        crossWin.setFont(Font.font(24));
        tieWin.setFont(Font.font(24));
        circleWin.setFont(Font.font(24));
        setWins();

        board.endTurn();
    }

    public void resetLayout() {
        switch (board.getWinner()) {
            case TIE:
                tie++;
                break;
            case CROSS:
                cross++;
                break;
            case CIRCLE:
                circle++;
                break;
        }

        setWins();

        board.resetBoard();
        for (Position pos : buttons.keySet()) {
            buttons.get(pos).setText(getStringOfField(pos));
        }
    }

    public void setWins() {
        crossWin.setText("Cross win count: " + cross + "  ");
        tieWin.setText("Tie count: " + tie + "  ");
        circleWin.setText("Circle win count: " + circle);
    }

    private String getStringOfField(Position pos) {
        if (pos == null || board.getUnit(pos) == null) {
            return "-";
        }
        switch (board.getUnit(pos)) {
            case NONE:
                return "-";
            case CROSS:
                return "X";
            case CIRCLE:
                return "O";
        }
        return "";
    }

    @Override
    public void update() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Position pos = new Position(i,j);
                buttons.get(pos).setText(getStringOfField(pos));
            }
        }
    }
}
