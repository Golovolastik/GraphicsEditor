package figures;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Figure {
    protected double x;
    protected double y;
    private Color strokeColor;
    private double lineWidth;
    private PointsSet points;

    public Figure(double x, double y) {
        this.x = x;
        this.y = y;
        this.strokeColor = Color.BLACK;
        this.lineWidth = 2.0;

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
    public Color getStrokeColor() { return strokeColor; }
    public void setStrokeColor(Color color) { this.strokeColor = color; }
    public double getLineWidth() { return this.lineWidth; }
    public void setLineWidth(double width) { this.lineWidth = width; }

    // Этот метод должен быть реализован в подклассах
    public abstract void draw(GraphicsContext gc);

    public abstract double[] getXPoints();

    public abstract double[] getYPoints();

    public abstract int getNumberOfPoints();
}
