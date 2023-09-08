package figures;

import javafx.scene.canvas.GraphicsContext;

public class Line extends Figure {
    private double endX;
    private double endY;
    private PointsSet points;


    public Line(double startX, double startY, double endX, double endY) {
        super(startX, startY);
        this.endX = endX;
        this.endY = endY;
        this.points = new PointsSet();
        init();
    }

    public Line() {
        super(0, 0);
        this.endX = 20;
        this.endY = 20;
        this.points = new PointsSet();
        init();
    }

    public void setEndX(double endX) {
        this.endX = endX;
    }

    public void setEndY(double endY) {
        this.endY = endY;
    }

    public double getEndX() {
        return endX;
    }

    public double getEndY() {
        return endY;
    }

    @Override
    public void init(){
        this.points.setNumber_of_points(2);
        this.points.setX_axis(new double[]{getX(), getEndX()});
        this.points.setY_axis(new double[]{getY(), getEndY()});
    }

    public double[] getXPoints() {
        return this.points.getX_axis();
    }

    public double[] getYPoints() {
        return this.points.getY_axis();
    }

    public int getNumberOfPoints() {
        return this.points.getNumber_of_points();
    }
    @Override
    public void draw(GraphicsContext gc) {
        gc.setLineWidth(getLineWidth());
        gc.setStroke(getStrokeColor());
        gc.strokeLine(getX(), getY(), endX, endY);
    }
}
