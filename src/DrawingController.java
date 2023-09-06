import figures.Circle;
import figures.Ellipse;
import figures.Figure;
import figures.Rectangle;
import javafx.scene.Cursor;
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
    private boolean drawMode = false;
    private Figure currentFigure;

    public DrawingController(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
    }

    public void clearCanvas() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void enterFigurePoints() {
        DialogWindow window = new DialogWindow();
        Optional<String> result = window.showAndWait();
    }

    public void drawCircle() {
        Circle circle = new Circle(150, 150);
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

//    public void configureButton(Button button, Figure figure) {
//        button.setOnMousePressed(event -> {
//            if (!this.drawMode) {
//                if (event.isShiftDown()) {
//                    enterFigurePoints();
//                } else {
//                    this.drawMode = true;
//                    canvas.setCursor(Cursor.CROSSHAIR);
//                }
//            }
//        });
//        handleDrawing(figure);
//    }

    public void configureButton(Button button, Figure figure) {
        button.setOnMousePressed(event -> {
            if (!drawMode) {
                if (event.isShiftDown()) {
                    enterFigurePoints();
                } else {
                    drawMode = true; // Включаем режим рисования
                    canvas.setCursor(Cursor.CROSSHAIR);
                    currentFigure = figure; // Устанавливаем текущую фигуру
                }
            }
        });
        handleDrawing(currentFigure);
    }

    private void handleDrawing(Figure figure) {
        canvas.setOnMousePressed(event -> {
            if (drawMode && currentFigure != null) {
                currentFigure.setX(event.getX());
                currentFigure.setY(event.getY());
                currentFigure.draw(gc); // Рисуем текущую фигуру
                canvas.setCursor(Cursor.DEFAULT);
                drawMode = false; // Выключаем режим рисования
            }
        });
    }


    public VBox createButtonPanel() {
        VBox buttonPanel = new VBox(10);
        buttonPanel.setStyle("-fx-padding: 10px;");

        ArrayList<Button> buttonArray = new ArrayList<>();

        // Добавляем кнопку для очистки холста
        Button clearButton = new Button("Clear");
        clearButton.setPrefSize(50, 35);
        clearButton.setOnAction(e -> clearCanvas());

        // circle
        Button circleButton = createFigureButton(new javafx.scene.shape.Circle(10));
        circleButton.setOnAction(e -> drawCircle());
        buttonArray.add(circleButton);
        // ellipse
        Button ellipseButton = createFigureButton(new javafx.scene.shape.Ellipse(15, 10));
        configureButton(ellipseButton, new Ellipse());
        buttonArray.add(ellipseButton);
        // rectangle
        Button rectangleButton = createFigureButton(new javafx.scene.shape.Rectangle(25, 17));
        configureButton(rectangleButton, new Rectangle());
        buttonArray.add(rectangleButton);

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
