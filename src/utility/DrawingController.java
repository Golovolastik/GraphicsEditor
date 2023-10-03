package utility;

import figures.*;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Optional;

// figures displaying logic
public class DrawingController {
    private static DrawingController instance;
    private final Pane pane;
    private boolean drawMode = false;
    private boolean lineDrawingMode = false;
    private Figure currentFigure;
    private final FigureList figureList;
    private final Painter painter;
    private VBox buttonPanel;

    private DrawingController(Pane pane, Painter painter) throws Exception {
        this.pane = pane;
        this.painter = painter;
        this.figureList = FigureList.getInstance();
        setStdButtons();
        this.buttonPanel = createButtonPanel();
    }
    public static DrawingController getInstance(Pane pane, Painter painter) throws Exception {
        if (instance == null) {
            synchronized (Painter.class) {
                if (instance == null) {
                    instance = new DrawingController(pane, painter);
                }
            }
        }
        return instance;
    }

    private void clearBoard() {
        this.pane.getChildren().clear();
        this.figureList.clear();
        this.drawMode = false;
        this.lineDrawingMode = false;
        this.pane.setCursor(Cursor.DEFAULT);
        this.pane.setPrefSize(800, 600);
    }

    // set figure coordinates with dialog window
    private void enterFigurePoints(Figure figure) {
        Dialog<Double[]> dialog = createDialog(figure);
        Optional<Double[]> result = showDialogAndWait(dialog);
        if (result.isPresent()) {
            Double[] xy = result.get();
            figure.setX(xy[0]);
            figure.setY(xy[1]);
            // init additional points to draw line
            if (figure instanceof Line) {
                ((Line) figure).setEndX(xy[2]);
                ((Line) figure).setEndY(xy[3]);
            }
            this.painter.draw(figure);
        }
    }
    // create dialog window
    private Dialog<Double[]> createDialog(Figure figure) {
        Dialog<Double[]> dialog = new Dialog<>();
        dialog.setTitle("Enter coordinates");
        // x and y entry fields
        ArrayList<TextField> fields = new ArrayList<>();
        TextField xField = createTextField("StartX");
        fields.add(xField);
        TextField yField = createTextField("StartY");
        fields.add(yField);
        // add fields on grid
        GridPane grid = new GridPane();
        grid.add(new Label("StartX:"), 0, 0);
        grid.add(xField, 1, 0);
        grid.add(new Label("StartY:"), 0, 1);
        grid.add(yField, 1, 1);
        // end of line coordinates
        if (figure instanceof Line) {
            TextField xEndField = createTextField("EndX");
            fields.add(xEndField);
            TextField yEndField = createTextField("EndY");
            fields.add(yEndField);
            grid.add(new Label("EndX:"), 0, 2);
            grid.add(xEndField, 1, 2);
            grid.add(new Label("EndY:"), 0, 3);
            grid.add(yEndField, 1, 3);
        }
        dialog.getDialogPane().setContent(grid);
        // add Ok and Cancel buttons
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);
        // handle Ok click
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
    // save entered values on Ok click
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
            // handle wrong entry values
            showErrorDialog("Entry error", "Enter correct coordinates");
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
    // init figure button with figure icon
    private Button createFigureButton(javafx.scene.shape.Shape figure) {
        Button drawButton = new Button();
        drawButton.setPrefSize(50, 35);
        figure.setFill(Color.WHITE);
        figure.setStroke(Color.BLACK);
        drawButton.setGraphic(figure);
        return drawButton;
    }
    // set logic on figure button click
    public void configureButton(Button button, Class<? extends Figure> figureClass) throws Exception {
            button.setOnMousePressed(event -> {
                try {
                    Figure figure = figureClass.getDeclaredConstructor().newInstance();
                    if (!drawMode) {
                        // init figure with x and y values with shift-click
                        if (event.isShiftDown()) {
                            enterFigurePoints(figure);
                        } else {
                            drawMode = true; // enter drawing mode
                            pane.setCursor(Cursor.CROSSHAIR); // changing mouse pointer
                            this.currentFigure = figure;
                        }
                    }
                    handleDrawing(); // handle action in drawing mode
            } catch (Exception e) {
            System.out.println(e);
        }
            });
    }

    private void handleDrawing() {
        pane.setOnMousePressed(event -> {
            // another logic for drawing line
            if (drawMode && currentFigure instanceof Line) {
                currentFigure.setX(event.getX());
                currentFigure.setY(event.getY());
                lineDrawingMode = true;
            }
            // place figure on click coordinates
            else if (drawMode && currentFigure != null) {
                currentFigure.setX(event.getX());
                currentFigure.setY(event.getY());
                this.painter.draw(currentFigure);
                pane.setCursor(Cursor.DEFAULT);
                drawMode = false;
            }
        });
        lineDrawing();
    }
    // first point of line on click, second on mouse button released
    private void lineDrawing() {
        pane.setOnMouseReleased(event -> {
            if (lineDrawingMode && currentFigure instanceof Line) {
                ((Line) currentFigure).setEndX(event.getX());
                ((Line) currentFigure).setEndY(event.getY());
                this.painter.draw(currentFigure);
                pane.setCursor(Cursor.DEFAULT);
                drawMode = false;
                lineDrawingMode = false;
            }
        });
    }
    // init button array
    private void setStdButtons() throws Exception {
        FigureButtons buttonArray = FigureButtons.getInstance();
        // circle
        Button circleButton = createFigureButton(new javafx.scene.shape.Circle(10));
        configureButton(circleButton, Circle.class);
        buttonArray.addButton(circleButton);
        // ellipse
        Button ellipseButton = createFigureButton(new javafx.scene.shape.Ellipse(15, 10));
        configureButton(ellipseButton, Ellipse.class);
        buttonArray.addButton(ellipseButton);
        // rectangle
        Button rectangleButton = createFigureButton(new javafx.scene.shape.Rectangle(25, 17));
        configureButton(rectangleButton, Rectangle.class);
        buttonArray.addButton(rectangleButton);
        // line
        Button lineButton = createFigureButton(new javafx.scene.shape.Line(15, 15, 1, 1));
        configureButton(lineButton, Line.class);
        buttonArray.addButton(lineButton);
        // parallelogram
        javafx.scene.shape.Polygon parallelogram = new javafx.scene.shape.Polygon();
        parallelogram.getPoints().addAll(5.0, 2.0,
                25.0, 2.0,
                20.0, 18.0,
                0.0, 18.0);
        Button parallelogramButton = createFigureButton(parallelogram);
        configureButton(parallelogramButton, Parallelogram.class);
        buttonArray.addButton(parallelogramButton);

    }
    // init button panel
    public VBox createButtonPanel() throws Exception {
        VBox buttonPanel = new VBox(10);
        buttonPanel.setStyle("-fx-padding: 10px;");
        FigureButtons buttonArray = FigureButtons.getInstance();
        // clear button
        Button clearButton = new Button("Clear");
        clearButton.setPrefSize(50, 35);
        clearButton.setOnAction(e -> clearBoard());
        // place figure buttons
        buttonPanel.getChildren().addAll(clearButton);
        for (Button button: buttonArray.getButtonArray()) {
            buttonPanel.getChildren().add(button);
        }
        return buttonPanel;
    }
    public VBox getButtonPanel(){
        return this.buttonPanel;
    }
}
