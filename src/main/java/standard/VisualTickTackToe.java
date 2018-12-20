package standard;

import Framework.Board;
import Framework.Position;
import Framework.Unit;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.HashMap;
import java.util.Map;

import static Framework.GameConstants.BOARD_SIZE;

public class VisualTickTackToe {
    @FXML
    private VBox boxLayout;
    private Map<Position, Label> labels;

    private Board board;
    public VisualTickTackToe() {
        board = new BoardImpl();
        labels = new HashMap<>();
    }


    public void setUpBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            HBox hBox = new HBox();
            for (int j = 0; j < BOARD_SIZE; j++) {
                Label label = new Label();
                Unit u = board.getUnit(new Position(i,j));
                label.setText(" | " + (u == null ? "-" : u));
                int row = i, col = j;
                label.setOnMouseClicked(event -> {
                    Position pos = new Position(row, col);
                    if (board.markField(pos)) {
                        String result = " | ";
                        switch (board.getUnit(pos)) {
                            case NONE:
                                result += "-";
                                break;
                            case CROSS:
                                result += "X";
                                break;
                            case CIRCLE:
                                result += "O";
                                break;
                        }
                        label.setText(result);
                    }
                });
                label.setFont(Font.font(Math.min(boxLayout.getWidth(), boxLayout.getHeight()) / BOARD_SIZE - 20));
                hBox.getChildren().add(label);
            }
            boxLayout.getChildren().add(hBox);
        }
    }
}
