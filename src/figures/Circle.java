package figures;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.HashMap;

public class Circle extends Figure {
    private double radius;
    private PointsSet points;

    public Circle(double x, double y) {
        super(x, y);
        this.radius = 50;
        this.points = new PointsSet();
    }
    public Circle() {
        this(0, 0);
        this.points = new PointsSet();
    }
    public void setRadius(double radius) {
        this.radius = radius;
    }
    @Override
    public void init() {
        // calculate each point of circle
        int numberOfPoints = 360;
        this.points.setNumber_of_points(numberOfPoints);
        double theta = 0;
        double[] xAxis = new double[numberOfPoints];
        double[] yAxis = new double[numberOfPoints];
        for (int i=0; i<numberOfPoints; i++) {
            xAxis[i] = this.x + this.radius*Math.cos(theta);
            yAxis[i] = this.y + this.radius*Math.sin(theta);
            theta += 2*Math.PI / numberOfPoints;
        }
        this.points.setX_axis(xAxis);
        this.points.setY_axis(yAxis);
        this.points.initPoints();
        // creating figure polygon
        Polygon figure = this.points.getPolygon();
        figure.getPoints().addAll(this.points.getPoints());
        figure.setFill(Color.rgb(255, 255, 255, 0));
        figure.setStroke(this.getBorderColor());
        figure.setStrokeWidth(2);
        this.setPolygon(figure);
    }
    public Double[] getPoints() {
        return this.points.getPoints();
    }
    public double[] getYPoints() {
        return this.points.getY_axis();
    }
    @Override
    public HashMap<String, Double> getParameters() {
        HashMap<String, Double> params = new HashMap<>();
        params.put("radius", this.radius);
        return params;
    }
    @Override
    public void setParameters(HashMap<String, Double> params) {
        this.radius = params.get("radius");
    }
}

