package figures;

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

    public double getRadius() {
        return radius;
    }
    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public void init() {
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
        params.put("radius", this.radius);
        return params;
    }

    @Override
    public void setParameters(HashMap<String, Double> params) {
        this.radius = params.get("radius");
    }
}
