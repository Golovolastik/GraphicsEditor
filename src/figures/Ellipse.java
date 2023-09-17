package figures;

import java.util.HashMap;

public class Ellipse extends Figure {
    private double radiusX;
    private double radiusY;
    private PointsSet points;


    public Ellipse(double centerX, double centerY) {
        super(centerX, centerY);
        this.radiusX = 80;
        this.radiusY = 40;
        this.points = new PointsSet();

    }

    public Ellipse() {
        this(0, 0);
        this.points = new PointsSet();
    }

    @Override
    public void init(){
        int numberOfPoints = 360;
        this.points.setNumber_of_points(numberOfPoints);
        double theta = 0;
        double[] xAxis = new double[numberOfPoints];
        double[] yAxis = new double[numberOfPoints];
        for (int i=0; i<numberOfPoints; i++) {
            xAxis[i] = this.x + this.radiusX*Math.cos(theta);
            yAxis[i] = this.y + this.radiusY*Math.sin(theta);
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
        params.put("radiusX", this.radiusX);
        params.put("radiusY", this.radiusY);
        return params;    }
}
