import figures.Figure;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Painter {
    private GraphicsContext gc;

    public Painter(GraphicsContext gc) {
        this.gc = gc;
    }
    public void draw(Figure figure) {
        figure.init();
        this.gc.setStroke(Color.BLACK);
        double[] arrayX = figure.getXPoints();
        double[] arrayY = figure.getYPoints();
        this.gc.strokePolygon(figure.getXPoints(), figure.getYPoints(), figure.getNumberOfPoints());
    }

}
