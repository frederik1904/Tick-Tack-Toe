package standard.playerStrategy.selectFromPointsStrategy;

import Framework.Board;
import Framework.Position;
import Framework.playerStrategy.SelectFromPoints;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class SelectFromPointsRandomWithTopPoints implements SelectFromPoints {
    @Override
    public void selectField(Board board, Map<Position, Integer> pointMap) {
        ArrayList<Position> bestPos = new ArrayList<>();
        int mostPoints = -1;
        for (Position pos : pointMap.keySet()) {
            int currentPoints = pointMap.get(pos);

            if (currentPoints > mostPoints) {
                bestPos.clear();
                bestPos.add(pos);
                mostPoints = pointMap.get(pos);
            } else if (currentPoints == mostPoints) {
                bestPos.add(pos);
            }
        }
        board.markField(bestPos.get(new Random().nextInt(bestPos.size())));
    }
}
