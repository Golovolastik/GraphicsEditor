import figures.*;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import utility.FigureList;

import java.util.ArrayList;
import java.util.Optional;

public class DrawingController {
    private Pane pane;
    private boolean drawMode = false;
    private boolean lineDrawingMode = false;
    private Figure currentFigure;
    private FigureList figureList;
    private Painter painter;

    public DrawingController(Pane pane, Painter painter, FigureList figureList) {
        this.pane = pane;
        this.painter = painter;
        this.figureList = figureList;
    }

    private void clearBoard() {
        this.pane.getChildren().clear();
        this.figureList.clear();
        this.drawMode = false;
        this.lineDrawingMode = false;
        this.pane.setCursor(Cursor.DEFAULT);
        this.pane.setPrefSize(800, 600);
    }

    private void enterFigurePoints(Figure figure) {
        Dialog<Double[]> dialog = createDialog(figure);
        Optional<Double[]> result = showDialogAndWait(dialog);

        if (result.isPresent()) {
            Double[] xy = result.get();
            figure.setX(xy[0]);
            figure.setY(xy[1]);
            if (figure instanceof Line) {
                ((Line) figure).setEndX(xy[2]);
                ((Line) figure).setEndY(xy[3]);
            }
            //this.figureList.addFigure(figure);
            this.painter.draw(figure);
        }
    }

    private Dialog<Double[]> createDialog(Figure figure) {
        Dialog<Double[]> dialog = new Dialog<>();
        dialog.setTitle("Введите координаты точки");

        // Создаем поля для ввода x и y
        ArrayList<TextField> fields = new ArrayList<>();
        TextField xField = createTextField("StartX-координата");
        fields.add(xField);
        TextField yField = createTextField("StartY-координата");
        fields.add(yField);

        // Добавляем поля в диалоговое окно
        GridPane grid = new GridPane();
        grid.add(new Label("StartX:"), 0, 0);
        grid.add(xField, 1, 0);
        grid.add(new Label("StartY:"), 0, 1);
        grid.add(yField, 1, 1);
        if (figure instanceof Line) {
            TextField xEndField = createTextField("EndX-координата");
            fields.add(xEndField);
            TextField yEndField = createTextField("EndY-координата");
            fields.add(yEndField);
            grid.add(new Label("EndX:"), 0, 2);
            grid.add(xEndField, 1, 2);
            grid.add(new Label("EndY:"), 0, 3);
            grid.add(yEndField, 1, 3);
        }
        dialog.getDialogPane().setContent(grid);

        // Создаем кнопку "ОК" и добавляем обработчик
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        // Привязываем результат к кнопке "ОК"
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButton) {
                return handleOkButtonClick(fields);
            }
            return null;
        });

        return dialog;
    }

    private TextField createTextField(String promptText) {
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        return textField;
    }

    private Optional<Double[]> showDialogAndWait(Dialog<Double[]> dialog) {
        return dialog.showAndWait();
    }

    private Double[] handleOkButtonClick(ArrayList<TextField> fields) {
        try {
            double startXValue = Double.parseDouble(fields.get(0).getText());
            double startYValue = Double.parseDouble(fields.get(1).getText());
            if (fields.size() > 2) {
                double endXValue = Double.parseDouble(fields.get(2).getText());
                double endYValue = Double.parseDouble(fields.get(3).getText());
                return new Double[] {startXValue, startYValue, endXValue, endYValue};
            } else {
                return new Double[]{startXValue, startYValue};
            }
        } catch (NumberFormatException e) {
            // Обработка ошибки ввода (некорректные числа)
            showErrorDialog("Ошибка ввода", "Введите корректные числа для X и Y.");
            return null;
        }
    }

    private void showErrorDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private Button createFigureButton(javafx.scene.shape.Shape figure) {
        Button drawButton = new Button();
        drawButton.setPrefSize(50, 35);
        figure.setFill(Color.WHITE);
        figure.setStroke(Color.BLACK);
        drawButton.setGraphic(figure);

        return drawButton;
    }

    public void configureButton(Button button, Figure figure) {
        button.setOnMousePressed(event -> {
            if (!drawMode) {
                if (event.isShiftDown()) {
                    enterFigurePoints(figure);
                } else {
                    drawMode = true; // Включаем режим рисования
                    pane.setCursor(Cursor.CROSSHAIR);
                    currentFigure = figure; // Устанавливаем текущую фигуру
                }
            }
        });
        handleDrawing();
    }

    private void handleDrawing() {
        pane.setOnMousePressed(event -> {
            if (drawMode && currentFigure instanceof Line) {
                currentFigure.setX(event.getX());
                currentFigure.setY(event.getY());
                lineDrawingMode = true;
            } else if (drawMode && currentFigure != null) {
                currentFigure.setX(event.getX());
                currentFigure.setY(event.getY());
                //this.figureList.addFigure(currentFigure);
                this.painter.draw(currentFigure);
                pane.setCursor(Cursor.DEFAULT);
                drawMode = false;
            }
        });
        lineDrawing();
    }

    private void lineDrawing() {
        pane.setOnMouseReleased(event -> {
            if (lineDrawingMode && currentFigure instanceof Line) {
                ((Line) currentFigure).setEndX(event.getX());
                ((Line) currentFigure).setEndY(event.getY());
                this.painter.draw(currentFigure);
                //this.figureList.addFigure(currentFigure);
                pane.setCursor(Cursor.DEFAULT);
                drawMode = false;
                lineDrawingMode = false; // Выключаем режим рисования линии после завершения
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
        clearButton.setOnAction(e -> clearBoard());

        // circle
        Button circleButton = createFigureButton(new javafx.scene.shape.Circle(10));
        configureButton(circleButton, new Circle());
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
        configureButton(lineButton, new Line());
        buttonArray.add(lineButton);
        // parallelogram
        javafx.scene.shape.Polygon parallelogram = new javafx.scene.shape.Polygon();
        parallelogram.getPoints().addAll(5.0, 2.0,
                25.0, 2.0,
                20.0, 18.0,
                0.0, 18.0);
        Button parallelogramButton = createFigureButton(parallelogram);
        configureButton(parallelogramButton, new Parallelogram());
        buttonArray.add(parallelogramButton);
        // Размещаем кнопки на панели
        buttonPanel.getChildren().addAll(clearButton);
        for (Button button: buttonArray) {
            buttonPanel.getChildren().add(button);
        }

        return buttonPanel;
    }
}
