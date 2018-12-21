package standard;

import Framework.Board;
import Framework.Observer;
import Framework.Position;
import Framework.Unit;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.HashMap;
import java.util.Map;

import static Framework.GameConstants.BOARD_SIZE;

public class VisualTickTackToe implements Observer {
    @FXML
    private VBox boxLayout;
    private Map<Position, Button> buttons;

    private Board board;
    public VisualTickTackToe() {
        board = new BoardImpl(new PlayerStrategyAI());
        buttons = new HashMap<>();
        ((BoardImpl) board).attatch(this);
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
