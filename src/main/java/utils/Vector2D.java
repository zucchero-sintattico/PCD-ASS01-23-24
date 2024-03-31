package utils;

public class Vector2D {
    private final double x;
    private final double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Vector2D makeV2d(Point2D from, Point2D to) {
        return new Vector2D(to.getX() - from.getX(), to.getY() - from.getY());
    }

    public Vector2D sum(Vector2D v) {
        return new Vector2D(x + v.x, y + v.y);
    }

    public double abs() {
        return (double) Math.sqrt(x * x + y * y);
    }

    public Vector2D getNormalized() {
        double module = (double) Math.sqrt(x * x + y * y);
        return new Vector2D(x / module, y / module);
    }

    public Vector2D mul(double fact) {
        return new Vector2D(x * fact, y * fact);
    }

    public String toString() {
        return "V2d(" + x + "," + y + ")";
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

}
