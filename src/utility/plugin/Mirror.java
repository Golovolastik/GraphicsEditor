package utility.plugin;

import figures.Figure;
import figures.Line;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import utility.Painter;
import utility.PopupPanel;

import java.util.HashMap;

public class Mirror {
    private Pane pane;
    private Painter painter;
    private Figure figure;
    private boolean lineDrawMode = false;
    private Figure currentFigure;
    private HashMap<String, Double> figureParams;
    private Line axis = new Line();
    public Mirror(Pane pane){
        this.pane = pane;
        this.painter = Painter.getInstance(this.pane);

    }
    // add button to panel and set action
    public Button createMirrorButton() {
        Button scaleButton = new Button("Mirror");
        scaleButton.setPrefSize(60, 30);
        scaleButton.setOnMouseClicked(e -> {
            PopupPanel popupPanel = PopupPanel.getInstance(this.pane);
            this.figure = popupPanel.getFigure();
            this.figureParams = this.figure.getParameters();
            popupPanel.getPopup().hide();
            try {
                if (!lineDrawMode) {
                    // draw mirror axis
                    Figure newFigure = this.figure.getClass().getDeclaredConstructor().newInstance();
                    this.currentFigure = newFigure;
                    lineDrawMode = true;
                    pane.setCursor(Cursor.CROSSHAIR);
                    handleDrawing();
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        return scaleButton;
    }
    private void handleDrawing() {
        pane.setOnMousePressed(event -> {
            if (lineDrawMode) {
                this.axis.setX(event.getX());
                this.axis.setY(event.getY());
                lineDrawing();
            }
        });
    }
    private void lineDrawing() {
        pane.setOnMouseReleased(event -> {
            if (lineDrawMode) {
                this.axis.setEndX(event.getX());
                this.axis.setEndY(event.getY());
                pane.setCursor(Cursor.DEFAULT);
                lineDrawMode = false;
                drawFigure();
            }
        });
    }
    private void drawFigure(){
        initFigure(calculateIntersectionPoints());
    }
    // find intersection points of mirror axis and perpendicular from figure points
    // then find points of a new figure
    private Double[] calculateIntersectionPoints() {
        double[] xAxis = this.figure.getXPoints();
        double[] yAxis = this.figure.getYPoints();
        // two points of a line
        double x1 = this.axis.getX();
        double y1 = this.axis.getY();
        double x2 = this.axis.getEndX();
        double y2 = this.axis.getEndY();
        Double[] result = new Double[xAxis.length*2];
        for (int i=0; i<xAxis.length; i++) {
            double x3 = xAxis[i];
            double y3= yAxis[i];
            // find coordinates of intersection points
            double intersectionX = ((x2-x1)*(y2-y1)*(y3-y1)+x1*Math.pow(y2-y1, 2)+x3*Math.pow(x2-x1, 2))/(Math.pow(y2-y1, 2)+Math.pow(x2-x1, 2));
            double intersectionY = (y2-y1)*(intersectionX-x1)/(x2-x1)+y1;
            // add offset to find points of a new figure
            double newX = (intersectionX - x3) + intersectionX;
            double newY = (intersectionY - y3) + intersectionY;
            result[i*2] = newX;
            result[i*2+1] = newY;
        }
        return result;
    }
    // add new figure to scene
    private void initFigure(Double[] points) {
        Polygon polygon = new Polygon();
        polygon.getPoints().addAll(points);
        polygon.setFill(Color.rgb(255, 255, 255, 0));
        polygon.setStroke(Color.BLACK);
        polygon.setStrokeWidth(2);
        this.pane.getChildren().add(polygon);
    }
}
