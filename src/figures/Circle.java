package figures;

import javafx.scene.canvas.GraphicsContext;

public class Circle extends Figure {
    private double radius;

    public Circle(double x, double y) {
        super(x, y);
        this.radius = 50;
    }
    public Circle() {
        this(0, 0);
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public void init(){
        System.out.println("Hello");
    }
    @Override
    public void draw(GraphicsContext gc) {
        gc.setLineWidth(getLineWidth());
        gc.setStroke(getStrokeColor());
        gc.strokeOval(getX() - radius, getY() - radius, 2 * radius, 2 * radius);    }

    @Override
    public double[] getXPoints() {
        return new double[0];
    }

    @Override
    public double[] getYPoints() {
        return new double[0];
    }

    @Override
    public int getNumberOfPoints() {
        return 0;
    }
}
