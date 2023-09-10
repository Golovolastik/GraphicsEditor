import figures.Figure;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Painter {
    private Pane pane;
    private GraphicsContext gc;
    private double lineWidth = 2;
    private Color borderColor = Color.BLACK;


    public Painter(Pane pane) {
        this.pane = pane;
    }
    public void draw(Figure figure) {
        figure.init();
        Polygon fig = new Polygon();
        fig.getPoints().addAll(figure.getPoints());
        fig.setFill(Color.rgb(255, 255, 255, 0));
        fig.setStroke(this.borderColor);
        fig.setStrokeWidth(this.lineWidth);
        this.pane.getChildren().add(fig);
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
