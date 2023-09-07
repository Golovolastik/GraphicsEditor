package figures;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Figure {
    private double x;
    private double y;
    private Color strokeColor;
    private double lineWidth;

    public Figure(double x, double y) {
        this.x = x;
        this.y = y;
        this.strokeColor = Color.BLACK; // Значение по умолчанию для цвета обводки
        this.lineWidth = 2.0;
    }

    public Figure() {
        this(0, 0);
    }

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
}
