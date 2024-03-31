package utils;

public class Point2D {
    private final double x;
    private final double y;

    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static double len(Point2D p0, Point2D p1) {
        double dx = p0.x - p1.x;
        double dy = p0.y - p1.y;
        return (double)Math.sqrt(dx*dx+dy*dy);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
