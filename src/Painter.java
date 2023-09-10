import figures.Figure;
import figures.PopupPanel;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;


public class Painter {
    private Pane pane;
    private double lineWidth = 2;
    private Color borderColor = Color.BLACK;

    public Painter(Pane pane) {
        this.pane = pane;
    }
    public void draw(Figure figure) {
        figure.init();
        this.pane.getChildren().add(initParameters(figure));
    }

    public void drawAll(FigureList array) {
        for (Figure figure: array.getFigures()) {
            this.draw(figure);
        }
    }

    private Polygon initParameters(Figure figure){
        Polygon fig = new Polygon();
        fig.getPoints().addAll(figure.getPoints());
        fig.setFill(Color.rgb(255, 255, 255, 0));
        fig.setStroke(this.borderColor);
        fig.setStrokeWidth(this.lineWidth);
        fig.setOnMouseClicked(e -> {
            PopupPanel panel = new PopupPanel(this.pane, e);
        });

        return fig;
    }

    public void setLineWidth(double lineWidth) {
        this.lineWidth = lineWidth;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }
}
