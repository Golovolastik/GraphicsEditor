package figures;

import javafx.scene.canvas.GraphicsContext;

public class Line extends Figure {
    private double endX;
    private double endY;

    public Line(double startX, double startY, double endX, double endY) {
        super(startX, startY);
        this.endX = endX;
        this.endY = endY;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setLineWidth(getLineWidth());
        gc.setStroke(getStrokeColor());
        gc.strokeLine(getX(), getY(), endX, endY);
    }
}
