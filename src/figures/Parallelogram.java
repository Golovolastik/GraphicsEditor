package figures;

import javafx.scene.canvas.GraphicsContext;

public class Parallelogram extends Figure implements Drawable {
    private double width;
    private double height;
    private double angle; // Угол наклона параллелограмма (в градусах)

    public Parallelogram(double x, double y, double width, double height, double angle) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.angle = angle;
    }

    @Override
    public void draw(GraphicsContext gc) {
        double radianAngle = Math.toRadians(angle);

        double x1 = getX();
        double y1 = getY();
        double x2 = x1 + width;
        double y2 = getY();
        double x3 = x1 + width - height * Math.tan(radianAngle);
        double y3 = y1 + height;
        double x4 = x1 - height * Math.tan(radianAngle);
        double y4 = y1 + height;

        gc.fillPolygon(new double[]{x1, x2, x3, x4}, new double[]{y1, y2, y3, y4}, 4);
    }
}
