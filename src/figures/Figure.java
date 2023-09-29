package figures;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.io.Serializable;
import java.util.HashMap;

public abstract class Figure implements Serializable {
    // root points
    protected double x;
    protected double y;
    // figure points
    private final PointsSet points;
    // javafx figure representation
    private Polygon polygon = new Polygon();
    private Color borderColor = Color.BLACK;
    // class constructor
    public Figure(double x, double y) {
        this.x = x;
        this.y = y;
        this.points = new PointsSet();
    }
    public Figure() {
        this(0, 0);
    }
    public abstract void init();
    public abstract Double[] getPoints();
    public abstract HashMap<String, Double> getParameters();
    public abstract void setParameters(HashMap<String, Double> params);
    public Polygon getPolygon(){
        return this.polygon;
    }
    public void setPolygon(Polygon polygon){
        this.polygon = polygon;
        this.points.setPolygon(this.polygon);
    }
    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }
    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }
    public double[] getXPoints() {
        return this.points.getX_axis();
    }
    public double[] getYPoints(){
        return this.points.getY_axis();
    }
    public Color getBorderColor() {
        return borderColor;
    }
    public void setBorderColor(Color borderColor) {
        Polygon polygon = this.points.getPolygon();
        polygon.setStroke(borderColor);
        setPolygon(polygon);
        this.borderColor = borderColor;
    }
}
