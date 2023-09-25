package utility.plugin;


import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ColorChanger {
    private Button changeColor;
    public Button changeColorButton() {
        Button changeColor = new Button();
        changeColor.setPrefSize(60, 30);
        changeColor.setGraphic(createIcon());
        this.changeColor = changeColor;

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
