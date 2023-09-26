package utility.plugin;


import figures.Figure;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import utility.PopupPanel;

public class ColorChanger {
    private Button changeColorButton;
    private HBox colorPallete = new HBox();
    private Figure figure;
    public Button changeColorButton() {
        Button changeColor = new Button();
        changeColor.setPrefSize(60, 30);
        changeColor.setGraphic(gradientIcon());
        changeColor.setOnMouseClicked(e -> {
            this.figure = PopupPanel.getInstance(new Pane()).getFigure();
            Dialog dialog = colorChooseDialog();
            dialog.showAndWait();
        });

        this.changeColorButton = changeColor;

        return changeColor;
    }
    private Rectangle gradientIcon() {
        Rectangle square = new Rectangle();
        square.setHeight(15);
        square.setWidth(15);
        Stop[] stops = new Stop[] { new Stop(0, Color.BLUE), new Stop(1, Color.RED)};
        LinearGradient lg = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
        square.setFill(lg);

        return square;
    }
    private Rectangle createIcon(Color color) {
        Rectangle square = new Rectangle();
        square.setHeight(15);
        square.setWidth(15);
        square.setFill(color);

        return square;
    }
    private Dialog colorChooseDialog() {
        Dialog dialog = new Dialog();
        createPalette();
        dialog.getDialogPane().setContent(this.colorPallete);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        return dialog;
    }
    private void createPalette() {
        int maxColor = 0xffffff;
        int amount = 9;
        int step = maxColor / amount;
        int start = 0;
        HBox container = new HBox();
        for (int i=1; i<=Math.sqrt(amount); i++) {
            VBox column = new VBox();
            for (int j=1; j<=Math.sqrt(amount); j++) {
                Button button = new Button();
                button.setPrefSize(60, 30);
                String colorString = String.format("%06x", start);
                Color color = Color.web(colorString);
                button.setGraphic(createIcon(color));
                button.setOnMousePressed(e -> {
                    this.figure.setBorderColor(color);
                });
                column.getChildren().add(button);
                start += step;
            }
            container.getChildren().add(column);
        }
        this.colorPallete = container;
    }
}
