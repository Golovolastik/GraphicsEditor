package figures;

import javafx.scene.canvas.GraphicsContext;

public class Rectangle extends Figure  {
    private double x;
    private double y;
    private double width;
    private double height;

    public Rectangle(double x, double y, double width, double height) {
        super(x, y);
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setLineWidth(getLineWidth());
        gc.setStroke(getStrokeColor());
        gc.strokeRect(getX(), getY(), width, height);
    }
}
