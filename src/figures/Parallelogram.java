package figures;

import javafx.scene.shape.Polygon;

public class Parallelogram extends Figure {
    private double width;
    private double height;
    private double angle;
    private PointsSet points;


    public Parallelogram(double x, double y) {
        super(x, y);
        this.width = 100;
        this.height = 50;
        this.angle = 30;
        this.points = new PointsSet();
    }
    public Parallelogram() {
        super(0, 0);
        this.width = 100;
        this.height = 50;
        this.angle = 30;
        this.points = new PointsSet();
    }

    @Override
    public Polygon getFigure() {
        return this.points.getPolygon();
    }

    @Override
    public void init() {
        this.points.setNumber_of_points(4);
        double radianAngle = Math.toRadians(angle);
        double x1 = getX();
        double y1 = getY();
        double x2 = x1 + width;
        double y2 = getY();
        double x3 = x1 + width - height * Math.tan(radianAngle);
        double y3 = y1 + height;
        double x4 = x1 - height * Math.tan(radianAngle);
        double y4 = y1 + height;
        this.points.setX_axis(new double[]{x1, x2, x3, x4});
        this.points.setY_axis(new double[]{y1, y2, y3, y4});
        this.points.initPoints();

    }
    public Double[] getPoints() {
        return this.points.getPoints();
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
}
