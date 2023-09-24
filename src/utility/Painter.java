package utility;

import figures.Figure;
import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;


public class Painter {
    private static volatile Painter instance;
    private final Pane pane;
    private double lineWidth = 2;
    private Color borderColor = Color.BLACK;
    private final PopupPanel popupPanel;
    private Polygon polygon;
    private final FigureList figureList;

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
    public void draw(Figure figure) {
        figure.init();
        initParameters(figure);
        this.pane.getChildren().add(figure.getPolygon());
        this.figureList.addFigure(figure);
    }

    public void drawAll() {
        for (Figure figure: this.figureList.getFigures()) {
            this.draw(figure);
        }
    }

    private void initParameters(Figure figure){
        this.polygon = figure.getPolygon();
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
    }

    public void setLineWidth(double lineWidth) {
        this.lineWidth = lineWidth;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }
}
