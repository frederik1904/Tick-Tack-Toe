package Framework.playerStrategy;

import Framework.Board;
import Framework.Position;

import java.util.Map;

public interface SelectFromPoints {
    void selectField(Board board, Map<Position, Integer> pointMap);
}
