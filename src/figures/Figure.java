package figures;

public abstract class Figure {
    protected double x;
    protected double y;
    private PointsSet points;

    public Figure(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Figure() {
        this(0, 0);
    }

    public abstract void init();
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public abstract double[] getXPoints();

    public abstract double[] getYPoints();

    public abstract int getNumberOfPoints();
}
