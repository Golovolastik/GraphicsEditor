import figures.Circle;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Optional;

public class DrawingController {
    private Canvas canvas;
    private GraphicsContext gc;
    private Group root;

    public DrawingController(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
    }

    public void clearCanvas() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void drawFigure() {
        DialogWindow window = new DialogWindow();
        Optional<String> result = window.showAndWait();
    }

    public void drawCircle() {
        Circle circle = new Circle(150, 150, 50);
        circle.draw(gc);
    }

    private Button createFigureButton(javafx.scene.shape.Shape figure) {
        Button drawButton = new Button();
        drawButton.setPrefSize(50, 35);
        figure.setFill(Color.WHITE);
        figure.setStroke(Color.BLACK);
        drawButton.setGraphic(figure);

        return drawButton;
    }

    public VBox createButtonPanel() {
        VBox buttonPanel = new VBox(10); // 10 - расстояние между кнопками
        //buttonPanel.setBackground(Color.GRAY);
        buttonPanel.setStyle("-fx-padding: 10px;"); // Добавляем отступы

        ArrayList<Button> buttonArray = new ArrayList<>();

        // Добавляем кнопку для очистки холста
        Button clearButton = new Button("Clear");
        clearButton.setPrefSize(50, 35);
        clearButton.setOnAction(e -> clearCanvas());

        // circle
        Button circleButton = createFigureButton(new javafx.scene.shape.Circle(10));
        circleButton.setOnAction(e -> drawCircle());
        buttonArray.add(circleButton);
        // rectangle
        Button rectangleButton = createFigureButton(new javafx.scene.shape.Rectangle(25, 17));
        rectangleButton.setOnAction(e -> drawCircle()); // need method
        buttonArray.add(rectangleButton);
        // ellipse
        Button ellipseButton = createFigureButton(new javafx.scene.shape.Ellipse(15, 10));
        ellipseButton.setOnAction(e -> drawFigure()); // need method
        buttonArray.add(ellipseButton);
        // line
        Button lineButton = createFigureButton(new javafx.scene.shape.Line(15, 15, 1, 1));
        lineButton.setOnAction(e -> drawCircle()); // need method
        buttonArray.add(lineButton);
        // parallelogram
        javafx.scene.shape.Polygon parallelogram = new javafx.scene.shape.Polygon();
        parallelogram.getPoints().addAll(new Double[]{
                3.0, 2.0,
                23.0, 2.0,
                20.0, 18.0,
                0.0, 18.0});
        Button parallelogramButton = createFigureButton(parallelogram);
        parallelogramButton.setOnAction(e -> drawCircle()); // need method
        buttonArray.add(parallelogramButton);
        // Размещаем кнопки на панели
        buttonPanel.getChildren().addAll(clearButton);
        for (Button button: buttonArray) {
            buttonPanel.getChildren().add(button);
        }
        return buttonPanel;
    }
}
