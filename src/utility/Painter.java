package utility;

import figures.Figure;
import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;


public class Painter {
    private static volatile Painter instance;
    private Pane pane;
    private double lineWidth = 2;
    private Color borderColor = Color.BLACK;
    private PopupPanel popupPanel;
    private Polygon polygon;
    private FigureList figureList;

    private Painter(Pane pane){
        this.pane = pane;
        this.popupPanel = new PopupPanel(this.pane);
        this.figureList = FigureList.getInstance();
    }
    public static Painter getInstance(Pane pane) {
        if (instance == null) {
            synchronized (Painter.class) {
                if (instance == null) {
                    instance = new Painter(pane);
                }
            }
        }
        return instance;
    }
    public Pane getPane() {
        return this.pane;
    }

    public FigureList getFigureList() {
        return this.figureList;
    }
//    public utility.Painter(Pane pane, FigureList figureList) {
//        this.pane = pane;
//        this.popupPanel = new PopupPanel(this.pane);
//        this.figureList = figureList;
//    }
    public void draw(Figure figure) {
        figure.init();
        this.polygon = figure.getPolygon();
        this.polygon = initParameters(figure);
        figure.setPolygon(this.polygon);
        this.pane.getChildren().add(figure.getPolygon());
        System.out.println(figure);
        this.figureList.addFigure(figure);
    }

    public void drawAll() {
        for (Figure figure: this.figureList.getFigures()) {
            this.draw(figure);
        }
    }

    private Polygon initParameters(Figure figure){
        this.polygon = new Polygon();
        this.polygon.getPoints().addAll(figure.getPoints());
        this.polygon.setFill(Color.rgb(255, 255, 255, 0));
        this.polygon.setStroke(Color.BLACK);
        this.polygon.setStrokeWidth(this.lineWidth);
        this.polygon.setOnMouseClicked(e -> {
            if (this.pane.getCursor() == Cursor.CROSSHAIR ) {
                return;
            }
            if (e.isShiftDown()) {
                this.polygon = figure.getPolygon();
                this.pane.getChildren().remove(this.polygon);
                this.figureList.remove(figure);
                return;
            }
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
