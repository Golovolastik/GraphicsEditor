package figures;

import javafx.scene.shape.Polygon;

public class PointsSet {
    private Double[] points;
    private double[] xAxis;
    private double[] yAxis;
    private int number_of_points;
    private Polygon figure = new Polygon();

    public void initPoints(){
        // javafx polygon takes one array to initializing
        // array = [x1, y1, x2, y2, ..., xn, yn]
        // merging x and y array
        if (xAxis.length != yAxis.length) {
            throw new IllegalArgumentException("Different length of arrays");
        }
        Double[] combinedArray = new Double[xAxis.length * 2];
        for (int i = 0; i < xAxis.length; i++) {
            combinedArray[i * 2] = xAxis[i];
            combinedArray[i * 2 + 1] = yAxis[i];
        }
        this.points = combinedArray;
    }
    public Polygon getPolygon() {
        return this.figure;
    }
    public void setPolygon(Polygon polygon) {
        this.figure = polygon;
    }
    public Double[] getPoints() {
        return this.points;
    }
    public double[] getX_axis() {
        return this.xAxis;
    }
    public void setX_axis(double[] x_axis) {
        this.xAxis = x_axis;
    }
    public double[] getY_axis() {
        return this.yAxis;
    }
    public void setY_axis(double[] y_axis) {
        this.yAxis = y_axis;
    }
    public int getNumber_of_points() {
        return number_of_points;
    }
    public void setNumber_of_points(int number_of_points) {
        this.number_of_points = number_of_points;
    }

}
