package utility.plugin;

import figures.Figure;
import figures.Line;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
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
                    Figure newFigure = this.figure.getClass().getDeclaredConstructor().newInstance();
                    this.currentFigure = newFigure;
                    lineDrawMode = true;
                    pane.setCursor(Cursor.CROSSHAIR);
                    handleDrawing();
                    System.out.println(axis.getParameters());
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
                //this.painter.draw(currentFigure);
                //this.figureList.addFigure(currentFigure);
                pane.setCursor(Cursor.DEFAULT);
                lineDrawMode = false; // Выключаем режим рисования линии после завершения
            }
        });
    }
}
