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

    public Line() {
        super(0, 0);
        this.endX = 20;
        this.endY = 20;
    }

    public void setEndX(double endX) {
        this.endX = endX;
    }

    public void setEndY(double endY) {
        this.endY = endY;
    }
    @Override
    public void draw(GraphicsContext gc) {
        gc.setLineWidth(getLineWidth());
        gc.setStroke(getStrokeColor());
        gc.strokeLine(getX(), getY(), endX, endY);
    }
}
