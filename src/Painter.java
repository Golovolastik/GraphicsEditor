import figures.Figure;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Painter {
    private GraphicsContext gc;

    public Painter(GraphicsContext gc) {
        this.gc = gc;
    }
    public void draw(Figure figure) {
        //this.gc.setLineWidth(figure.getLineWidth());
        this.gc.setStroke(Color.BLACK);
        figure.init();
        double[] arrayX = figure.getXPoints();
        double[] arrayY = figure.getYPoints();

        for (double x: arrayX) {
            System.out.println(x);
        }

        for (double y: arrayY) {
            System.out.println(y);
        }
        this.gc.strokePolygon(figure.getXPoints(), figure.getYPoints(), figure.getNumberOfPoints());
        //this.gc.strokePolygon(new double[]{20, 20, 600, 600}, new double[] {20, 400, 400, 20}, 4);

    }

}
