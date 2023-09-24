package figures;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.HashMap;

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
        Polygon figure = new Polygon();
        figure.getPoints().addAll(this.points.getPoints());
        figure.setFill(Color.rgb(255, 255, 255, 0));
        figure.setStroke(Color.BLACK);
        figure.setStrokeWidth(2);
        this.setPolygon(figure);

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

    @Override
    public HashMap<String, Double> getParameters() {
        HashMap<String, Double> params = new HashMap<>();
        params.put("width", this.width);
        params.put("height", this.height);
        params.put("angle", this.angle);
        return params;
    }

    @Override
    public void setParameters(HashMap<String, Double> params) {
        this.width = params.get("width");
        this.height = params.get("height");
        this.angle = params.get("angle");
    }
}
