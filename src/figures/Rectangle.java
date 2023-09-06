package figures;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rectangle extends Figure  {
    private double x;
    private double y;
    private double width;
    private double height;

    public Rectangle(double x, double y) {
        super(x, y);
        this.width = 100;
        this.height = 50;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setLineWidth(getLineWidth());
        gc.setFill(Color.BLACK);
        gc.setStroke(getStrokeColor());
        gc.strokeRect(getX(), getY(), width, height);
    }
}
