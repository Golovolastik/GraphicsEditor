package utility;

import figures.Figure;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.stage.Popup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

// popup window with editing features
public class PopupPanel implements Mover, Sizer {
    private static PopupPanel instance;
    private ArrayList<Button> buttons; // action buttons
    private Pane pane;
    private MouseEvent event;
    private Popup popup = new Popup();
    private HBox panel = new HBox(); // buttons container
    private Figure figure;
    private Polygon polygon;

    private PopupPanel(Pane pane) {
        this.pane = pane;
        stdButtons();
        init();
    }
    public static PopupPanel getInstance(Pane pane) {
        if (instance == null) {
            synchronized (PopupPanel.class) {
                if (instance == null) {
                    instance = new PopupPanel(pane);
                }
            }
        }
        return instance;
    }
    private void init(){
        this.popup.getContent().remove(this.panel);
        this.panel.getChildren().clear();
        this.panel.getChildren().addAll(this.buttons);
        this.popup.getContent().add(this.panel);

    }
    public Popup getPopup() {
        return popup;
    }
    // show popup window at click position
    public void showPopup(MouseEvent event) {
        init();
        this.polygon = this.figure.getPolygon();
        getEvent(event);
        this.panel.toFront(); // new figures appear at first layer and can block buttons
        this.popup.setX(this.event.getX());
        this.popup.setX(this.event.getY());
        this.popup.show(this.pane, this.event.getScreenX(), this.event.getScreenY());
    }
    // init buttons list
    private void stdButtons() {
        ArrayList<Button> buttons = new ArrayList<>();
        Button close = new Button("Close");
        close.setOnAction(e -> {
            this.popup.hide();
        });
        buttons.add(close);
        buttons.add(resizeButton());
        buttons.add(moveButton());
        for (Button b: buttons) {
            b.setPrefSize(60, 30);
        }
        this.buttons = buttons;
    }
    public void addButton(Button button) {
        this.buttons.add(button);
    }
    public void getEvent(MouseEvent event) {
        this.event = event;
    }
    // change figure position
    private Button moveButton() {
        Button move = new Button("Move");
        move.setOnAction(e -> {
            if (this.figure != null) {
                createMoveDialog();
            }
        });
        return move;
    }
    // change figure parameters
    private Button resizeButton() {
        Button resize = new Button("Resize");
        resize.setOnAction(e -> {
            if (this.figure != null) {
                changeParameters();
            }
        });
        return resize;
    }
    // dialog window to handle move actions
    private Dialog<Double[]> createMoveDialog() {
        Dialog<Double[]> dialog = new Dialog<>();
        dialog.setTitle("How to move?");
        HBox dialogButtons = new HBox();
        Button relative = new Button("Relatively");
        relative.setPrefSize(80, 50);
        relative.setOnAction(e -> {
            Optional<Double[]> dialogWindow = innerMoveDialog();
            moveRelatively(dialogWindow);
            this.popup.hide();
        });
        Button absolute = new Button("Absolutely");
        absolute.setPrefSize(80, 50);
        absolute.setOnAction(e -> {
            Optional<Double[]> dialogWindow = innerMoveDialog();
            moveAbsolutely(dialogWindow);
            this.popup.hide();
        });
        dialogButtons.getChildren().addAll(relative, absolute);
        dialog.getDialogPane().setContent(dialogButtons);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        dialog.showAndWait();

        return dialog;
    }
    // create another dialog window and read distance values
    private Optional<Double[]> innerMoveDialog() {
        Dialog<Double[]> dialog = new Dialog<>();
        GridPane grid = new GridPane();
        grid.add(new Label("Move on X:"), 0, 0);
        grid.add(new Label("Move on Y:"), 0, 1);
        ArrayList<TextField> fields = new ArrayList<>();
        TextField xField = new TextField();
        TextField yField = new TextField();
        fields.add(xField);
        fields.add(yField);
        grid.add(xField, 1, 1);
        grid.add(yField, 1, 0);
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return handleOkButtonClick(fields);
            }
            return null;
        });
        return dialog.showAndWait();
    }
    // handle values - show error dialog on wrong values or save on valid
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
            showErrorDialog("Entry error", "Enter correct values");
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
    // change figure position absolutely -
    // enter new coordinates and place figure
    @Override
    public void moveAbsolutely(Optional<Double[]> parseResult) {
        if (!parseResult.isPresent()) {
            return;
        }
        Double[] result = parseResult.get();
        this.figure.setX(result[1]);
        this.figure.setY(result[0]);
        this.pane.getChildren().remove(this.polygon);
        this.figure.init();
        this.polygon.getPoints().clear();
        this.polygon.getPoints().addAll(this.figure.getPoints());
        this.figure.setPolygon(this.polygon);
        this.pane.getChildren().add(this.figure.getPolygon());
    }
    // change figure position from current place
    @Override
    public void moveRelatively(Optional<Double[]> parseResult) {
        if (!parseResult.isPresent()) {
            return;
        }
        Double[] result = parseResult.get();
        moveOnX(result[1]);
        moveOnY(result[0]);
        this.pane.getChildren().remove(this.polygon);
        this.figure.init();
        this.polygon.getPoints().clear();
        this.polygon.getPoints().addAll(this.figure.getPoints());
        this.figure.setPolygon(this.polygon);
        this.pane.getChildren().add(this.figure.getPolygon());
    }
    public void toFront() {
        this.panel.toFront();
    }
    @Override
    public void moveOnX(double distance) {
        this.figure.setX(this.figure.getX() + distance);
    }
    @Override
    public void moveOnY(double distance) {
        this.figure.setY(this.figure.getY() + distance);
    }
    // change figure size
    @Override
    public void changeParameters() {
        HashMap<String, Double> params = this.figure.getParameters();
        createResizeDialog(params);
    }
    // create dialog window with parameters and apply new values
    private void createResizeDialog(HashMap<String, Double> params) {
        Dialog<Double[]> dialog = new Dialog<>();
        dialog.setTitle("Resize");
        GridPane grid = new GridPane();
        int counter = 0;
        ArrayList<Label> labels = new ArrayList<>();
        ArrayList<TextField> fields = new ArrayList<>();
        for (String param: params.keySet()) {
            Label label = new Label(param);
            grid.add(label, 0, counter);
            labels.add(label);
            TextField field = new TextField(params.get(param).toString());
            fields.add(field);
            grid.add(field, 1, counter);
            counter++;
        }
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                double[] values = handleResizeButtonClick(fields);
                if (values == null) {
                    return null;
                }
                HashMap<String, Double> result = new HashMap<>();
                for (int i=0; i<labels.size(); i++){
                    result.put(labels.get(i).getText(), values[i]);
                }
                this.figure.setParameters(result);
                this.pane.getChildren().remove(this.polygon);
                this.figure.init();
                this.polygon.getPoints().clear();
                this.polygon.getPoints().addAll(this.figure.getPoints());
                this.figure.setPolygon(this.polygon);
                this.pane.getChildren().add(this.figure.getPolygon());
            }
            return null;
        });
        dialog.showAndWait();
    }
    private double[] handleResizeButtonClick(ArrayList<TextField> fields) {
        try {
            double[] params = new double[fields.size()];
            int count = 0;
            for (TextField param: fields) {
                    double value = Double.parseDouble(param.getText());
                    params[count] = value;
                    count++;
                }
            return  params;
        } catch (NumberFormatException e) {
            showErrorDialog("Entry error", "Enter correct coordinates");
            return null;
        }
    }
    public void setFigure(Figure figure) {
        this.figure = figure;
    }
    public Figure getFigure() {
        return this.figure;
    }

}
