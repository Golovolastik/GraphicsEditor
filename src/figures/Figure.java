package figures;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.io.Serializable;
import java.util.HashMap;

public abstract class Figure implements Serializable {
    protected double x;
    protected double y;
    private PointsSet points;
    private Polygon polygon = new Polygon();
    private Color borderColor = Color.BLACK;

    public Figure(double x, double y) {
        this.x = x;
        this.y = y;
        this.points = new PointsSet();
    }

    public Figure() {
        this(0, 0);
    }
    public Polygon getPolygon(){
        return this.polygon;
    }
    public void setPolygon(Polygon polygon){
        this.polygon = polygon;
        this.points.setPolygon(this.polygon);
    }
    public void delete() {

    }

    public abstract void init();
    public abstract Double[] getPoints();
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

    public void setXPoints(double[] xPoints) {
        this.points.setX_axis(xPoints);
    }

    public double[] getYPoints(){
        return this.points.getY_axis();
    }

    public void setYPoints(double[] yPoints) {
        this.points.setY_axis(yPoints);
    }

    public abstract int getNumberOfPoints();
    public abstract HashMap<String, Double> getParameters();
    public abstract void setParameters(HashMap<String, Double> params);

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
