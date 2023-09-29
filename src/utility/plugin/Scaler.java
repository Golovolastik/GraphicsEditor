package utility.plugin;

import figures.Figure;
import figures.Line;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import utility.Painter;
import utility.PopupPanel;

import java.util.HashMap;
import java.util.Map;

public class Scaler {
    private Pane pane;
    private Painter painter;
    private Figure figure;
    private boolean drawMode = false;
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
            if (this.figure instanceof Line) {
                System.out.println("Not supported for line");
                return;
            }
            popupPanel.getPopup().hide();
            try {
                if (!drawMode) {
                    Figure newFigure = this.figure.getClass().getDeclaredConstructor().newInstance();
                    this.currentFigure = newFigure;
                    drawMode = true;
                    handleDrawing();
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        return scaleButton;
    }

    private void handleDrawing() {
        this.pane.setCursor(Cursor.CROSSHAIR);
        this.pane.setOnMousePressed(event -> {
          if (drawMode && currentFigure != null) {
              double scale = showDialog();
              this.currentFigure.setX(event.getX());
              this.currentFigure.setY(event.getY());
              HashMap<String, Double> params = this.figure.getParameters();
              for (Map.Entry<String, Double> param: params.entrySet()) {
                  params.put(param.getKey(), param.getValue()*scale);

              }
              this.currentFigure.setParameters(params);
              this.painter.draw(currentFigure);
              //this.figure = this.currentFigure;
              this.pane.setCursor(Cursor.DEFAULT);
              drawMode = false;
              currentFigure = null;
            }
        });
    }

    private double showDialog() {
        Dialog dialog = new Dialog();
        dialog.setHeaderText("Enter new size in %");
        TextField field = new TextField("100%");
        dialog.getDialogPane().setContent(field);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);
        double[] result = {1};
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                result[0] = Double.parseDouble(field.getText()) / 100;
            }
            return null;
                });

        dialog.showAndWait();
        return result[0];
    }
}
