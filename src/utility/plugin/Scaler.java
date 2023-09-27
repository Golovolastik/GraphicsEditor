package utility.plugin;

import figures.Figure;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import utility.Painter;
import utility.PopupPanel;

public class Scaler {
    private Pane pane;
    private Painter painter;
    private Figure figure;
    private boolean drawMode = false;
    private boolean lineDrawingMode = false;
    private Figure currentFigure;
    public Scaler(Pane pane){
        this.pane = pane;
        this.painter = Painter.getInstance(this.pane);
    }
    public Button createScaledButton() {
        Button scaleButton = new Button("Scale");
        scaleButton.setPrefSize(60, 30);
        scaleButton.setOnMouseClicked(e -> {
            PopupPanel popupPanel = PopupPanel.getInstance(this.pane);
            this.figure = popupPanel.getFigure();
            popupPanel.getPopup().hide();
            try {
                if (!drawMode) {
                    Figure newFigure = this.figure.getClass().getDeclaredConstructor().newInstance();
                    this.currentFigure = newFigure;
                    drawMode = true;
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            handleDrawing();
        });

        return scaleButton;
    }

    private void handleDrawing() {
        this.pane.setCursor(Cursor.CROSSHAIR);
        pane.setOnMousePressed(event -> {
          if (drawMode && currentFigure != null) {
              this.currentFigure.setX(event.getX());
              this.currentFigure.setY(event.getY());
              this.painter.draw(currentFigure);
              this.pane.setCursor(Cursor.DEFAULT);
              drawMode = false;
            }
        });
    }
}
