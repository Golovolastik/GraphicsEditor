package figures;

import java.util.HashMap;

public class Rectangle extends Figure  {
    private double width;
    private double height;
    private PointsSet points;


    public Rectangle(double x, double y) {
        super(x, y);
        this.width = 100;
        this.height = 50;
        this.points = new PointsSet();
    }

    public Rectangle() {
        this(0, 0);
        this.points = new PointsSet();
    }
    @Override
    public void init() {
        this.points.setNumber_of_points(4);
        this.points.setX_axis(new double[]{this.x, this.x, this.x+width, this.x+width});
        this.points.setY_axis(new double[]{this.y, this.y+height, this.y+height, this.y});
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
        params.put("width", this.width);
        params.put("height", this.height);
        return params;
    }
}
