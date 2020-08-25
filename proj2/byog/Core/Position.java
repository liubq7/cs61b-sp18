package byog.Core;

public class Position {
    public int x;
    public int y;

    public Position(int X, int Y) {
        x = X;
        y = Y;
    }

    public Position leftPosition() {
        return new Position(x - 1, y);
    }
    public Position rightPosition() {
        return new Position(x + 1, y);
    }
    public Position upPosition() {
        return new Position(x, y + 1);
    }
    public Position downPosition() {
        return new Position(x, y - 1);
    }
}
