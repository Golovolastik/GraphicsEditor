import figures.Figure;
import utility.PopupPanel;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;


public class Painter {
    private Pane pane;
    private double lineWidth = 2;
    private Color borderColor = Color.BLACK;
    private PopupPanel popupPanel;
    private Polygon polygon;

    public Painter(Pane pane) {
        this.pane = pane;
        this.popupPanel = new PopupPanel(this.pane);
    }
    public void draw(Figure figure) {
        figure.init();
        this.polygon = figure.getPolygon();
        this.polygon = initParameters(figure);
        figure.setPolygon(this.polygon);
        this.pane.getChildren().add(figure.getPolygon());
    }

    public void drawAll(FigureList array) {
        for (Figure figure: array.getFigures()) {
            this.draw(figure);
        }
    }

    private Polygon initParameters(Figure figure){
        this.polygon.getPoints().addAll(figure.getPoints());
        this.polygon.setFill(Color.rgb(255, 255, 255, 0));
        this.polygon.setStroke(Color.BLACK);
        this.polygon.setStrokeWidth(this.lineWidth);
        this.polygon.setOnMouseClicked(e -> {
            this.polygon = figure.getPolygon();
            figure.setPolygon(this.polygon);
            this.popupPanel.showPopup(e, figure);
            this.popupPanel.toFront();
        });

        return this.polygon;
    }

    public void setLineWidth(double lineWidth) {
        this.lineWidth = lineWidth;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }
}
