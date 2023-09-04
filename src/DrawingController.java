import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class DrawingController {
    private Canvas canvas;
    private GraphicsContext gc;

    public DrawingController(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
    }

    public void clearCanvas() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void drawCircle() {
        gc.setFill(Color.BLUE); // Задайте цвет для круга
        gc.fillOval(100, 100, 50, 50); // Рисуем круг
    }

    public HBox createButtonPanel() {
        HBox buttonPanel = new HBox(10); // 10 - расстояние между кнопками
        buttonPanel.setStyle("-fx-padding: 10px;"); // Добавляем отступы

        // Добавляем кнопку для очистки холста
        Button clearButton = new Button("Очистить");
        clearButton.setOnAction(e -> clearCanvas());

        // Добавляем кнопку для рисования круга
        Button drawCircleButton = new Button("Рисовать круг");
        drawCircleButton.setOnAction(e -> drawCircle());

        // Размещаем кнопки на панели
        buttonPanel.getChildren().addAll(clearButton, drawCircleButton);

        return buttonPanel;
    }
}
