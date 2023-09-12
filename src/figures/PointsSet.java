package figures;

import javafx.scene.shape.Polygon;

public class PointsSet {
    private Double[] points;
    private double[] xAxis;
    private double[] yAxis;
    private int number_of_points;
    private Polygon figure;

    public void initPoints(){
        //Double[] points = new Double[this.number_of_points * 2];
        if (xAxis.length != yAxis.length) {
            throw new IllegalArgumentException("Different length of arrays");
        }

        Double[] combinedArray = new Double[xAxis.length * 2]; // Создаем новый массив, в два раза длиннее исходных

        for (int i = 0; i < xAxis.length; i++) {
            combinedArray[i * 2] = xAxis[i]; // Первая половина массива содержит x
            combinedArray[i * 2 + 1] = yAxis[i]; // Вторая половина массива содержит y
        }
        this.points = combinedArray;
    }
    public Polygon getPolygon() {
        this.figure = new Polygon();
        this.figure.getPoints().addAll(this.points);

        return this.figure;
    }
    public Double[] getPoints() {
        return this.points;
    }
    public double[] getX_axis() {
        return xAxis;
    }

    public void setX_axis(double[] x_axis) {
        this.xAxis = x_axis;
    }

    public double[] getY_axis() {
        return yAxis;
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
