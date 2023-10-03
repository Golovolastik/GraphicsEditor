package utility;

import figures.Figure;
import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;

// class that initialize figure and show it on screen
public class Painter {
    private static Painter instance;
    private final Pane pane;
    private final PopupPanel popupPanel;
    private Polygon polygon;
    private final FigureList figureList;

    private Painter(Pane pane){
        this.pane = pane;
        this.popupPanel = PopupPanel.getInstance(this.pane);
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
    // draw figure and add it to list
    public void draw(Figure figure) {
        figure.init();
        initParameters(figure);
        this.pane.getChildren().add(figure.getPolygon());
        this.figureList.addFigure(figure);
    }
    // draw all figures from list
    public void drawAll() {
        for (Figure figure: this.figureList.getFigures()) {
            this.draw(figure);
        }
    }
    // actions with figures on scene
    private void initParameters(Figure figure){
        this.polygon = figure.getPolygon();
        this.polygon.setOnMouseClicked(e -> {
            this.polygon = figure.getPolygon();
            // drawing mode
            if (this.pane.getCursor() == Cursor.CROSSHAIR ) {
                return;
            }
            // delete figure
            if (e.isShiftDown()) {
                this.polygon = figure.getPolygon();
                this.pane.getChildren().remove(this.polygon);
                this.figureList.remove(figure);
                return;
            }
            // show popup window on click
            this.popupPanel.setFigure(figure);
            this.popupPanel.showPopup(e);
            this.popupPanel.toFront();
        });
    }
}
