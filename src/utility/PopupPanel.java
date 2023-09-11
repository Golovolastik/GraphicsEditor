package utility;

import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Popup;

import java.util.ArrayList;
import java.util.Optional;

public class PopupPanel implements Mover {
    private ArrayList<Button> buttons;
    private Pane pane;
    private MouseEvent event;
    private Popup popup = new Popup();
    private HBox panel = new HBox();
    private Polygon figure;

    public PopupPanel(Pane pane){
        this.pane = pane;
        init();
    }
    private void init(){
        stdButtons();
        this.panel.getChildren().addAll(this.buttons);
        this.popup.getContent().add(this.panel);
    }

    public void showPopup(MouseEvent event, Polygon figure) {
        this.figure = figure;
        this.popup.hide();
        getEvent(event);
        this.popup.show(this.pane, this.event.getScreenX(), this.event.getScreenY());
    }

    private void stdButtons() {
        ArrayList<Button> buttons = new ArrayList<>();
        Button close = new Button("Close");
        close.setOnAction(e -> {
            this.popup.hide();
        });
        buttons.add(close);
        buttons.add(moveButton());
        this.buttons = buttons;
    }

    private ArrayList<Button> getButtons() {
        return this.buttons;
    }
    public void addButton(Button button) {

    }

    public void getEvent(MouseEvent event) {
        this.event = event;
    }

    private Button moveButton() {
        Button move = new Button("Move");
        move.setOnAction(e -> {
            if (this.figure != null) {
                this.figure.setFill(Color.AQUA);
                createMoveDialog();
            }
        });
        return move;
    }

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
        // Привязываем результат к кнопке "ОК"
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return handleOkButtonClick(fields);
            }
            return null;
        });
        return dialog.showAndWait();

        //return dialog;
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

    @Override
    public void moveAbsolutely(Optional<Double[]> parseResult) {
        if (!parseResult.isPresent()) {
            return;
        }
        Double[] result = parseResult.get();
        this.figure.setTranslateX(result[1]);
        this.figure.setTranslateY(result[0]);
    }

    @Override
    public void moveRelatively(Optional<Double[]> parseResult) {
        if (!parseResult.isPresent()) {
            return;
        }
        Double[] result = parseResult.get();
        System.out.println("X: " + result[1]);
        this.figure.setTranslateX(result[1]);
        System.out.println("Y: " + result[0]);
        this.figure.setTranslateY(result[0]);
    }
}