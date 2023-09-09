import figures.Figure;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Painter {
    private GraphicsContext gc;
    private double lineWidth = 2;
    private Color borderColor = Color.BLACK;


    public Painter(GraphicsContext gc) {
        this.gc = gc;
    }
    public void draw(Figure figure) {
        figure.init();
        this.gc.setLineWidth(this.lineWidth);
        this.gc.setStroke(this.borderColor);
        this.gc.strokePolygon(figure.getXPoints(), figure.getYPoints(), figure.getNumberOfPoints());
    }

    public void drawAll(FigureList array) {
        for (Figure figure: array.getFigures()) {
            this.draw(figure);
        }
    }

    public void setLineWidth(double lineWidth) {
        this.lineWidth = lineWidth;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }
}
