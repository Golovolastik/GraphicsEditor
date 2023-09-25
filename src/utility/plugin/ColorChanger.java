package utility.plugin;


import figures.Figure;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import utility.PopupPanel;

public class ColorChanger {
    private Button changeColorButton;
    private Figure figure;
    public Button changeColorButton() {
        Button changeColor = new Button();
        changeColor.setPrefSize(60, 30);
        changeColor.setGraphic(createIcon());
        changeColor.setOnAction(e -> {
            this.figure = PopupPanel.getInstance(new Pane()).getFigure();
            this.figure.setBorderColor(Color.CRIMSON);
        });

        this.changeColorButton = changeColor;

        return changeColor;
    }
    private Rectangle createIcon() {
        Rectangle square = new Rectangle();
        square.setHeight(15);
        square.setWidth(15);
        square.setFill(Color.BLUE);

        return square;
    }
    private void configureButton() {

    }
}
