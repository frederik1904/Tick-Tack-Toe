package standard.playerStrategy.selectFromPointsStrategy;

import Framework.Board;
import Framework.Position;
import Framework.playerStrategy.SelectFromPoints;

import java.util.*;

public class SelectFromPointsTotalRandom implements SelectFromPoints {
    @Override
    public void selectField(Board board, Map<Position, Integer> pointMap) {
        ArrayList<Touple> touples = new ArrayList<>();
        int sum = 0;
        for (Position pos : pointMap.keySet()) {
            int currentPoint = pointMap.get(pos);
            touples.add(new Touple(pos, currentPoint));
            sum += currentPoint;
        }

        int pieceToCheck = new Random().nextInt(sum + 1);

        Position pos = null;
        int currentAccum = 0;
        for (int i = 0; i < touples.size(); i++) {
            currentAccum += touples.get(i).getPoints();
            if (currentAccum >= pieceToCheck) {
                pos = touples.get(i).getPos();
                break;
            }
        }
        board.markField(pos);
    }

    private class Touple implements Comparable<Touple> {
        private Position pos;
        private int points;

        public Touple(Position pos, int points) {
            this.pos = pos;
            this.points = points;
        }

        public Position getPos() {
            return pos;
        }

        public int getPoints() {
            return points;
        }

        @Override
        public int compareTo(Touple o) {
            return points - o.points;
        }
    }
}
