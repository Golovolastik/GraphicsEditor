package utility.plugin;

import figures.Figure;
import figures.PointsSet;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.HashMap;

public class Star extends Figure {
    private PointsSet points;
    private Polygon polygon = new Polygon();
    private double radius = 90;

    public Star(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.points = new PointsSet();
    }
    public Star() {
        super(0, 0);
        this.points = new PointsSet();
    }

    @Override
    public void init() {
        // calculating 5 points of a star
        // then connect through one
        int numberOfPoints = 5;
        this.points.setNumber_of_points(numberOfPoints);
        double theta = 270;
        double[] xAxis = new double[numberOfPoints];
        double[] yAxis = new double[numberOfPoints];
        for (int i=0; i<numberOfPoints; i++) {
            xAxis[i] = this.radius * Math.cos(Math.toRadians(theta)) + this.x;
            yAxis[i] = this.radius * Math.sin(Math.toRadians(theta)) + this.y;
            theta += 144;
        }
        this.points.setX_axis(xAxis);
        this.points.setY_axis(yAxis);
        this.points.initPoints();
        Polygon figure = this.points.getPolygon();
        figure.getPoints().addAll(this.points.getPoints());
        figure.setFill(Color.rgb(255, 255, 255, 0));
        figure.setStroke(this.getBorderColor());
        figure.setStrokeWidth(2);
        this.setPolygon(figure);
    }
    public double getRadius() {
        return radius;
    }
    public void setRadius(double radius) {
        this.radius = radius;
    }
    @Override
    public Double[] getPoints() {
        return this.points.getPoints();
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
