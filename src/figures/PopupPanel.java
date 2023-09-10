package figures;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;

import java.util.ArrayList;

public class PopupPanel {
    private ArrayList<Button> buttons;
    private Pane pane;
    private MouseEvent event;
    private Popup popup = new Popup();
    private HBox panel = new HBox();

    public PopupPanel(Pane pane, MouseEvent event){
        this.pane = pane;
        this.event = event;
        init();
    }
    private void init(){
        stdButtons();
        this.panel.getChildren().addAll(this.buttons);
        this.popup.getContent().add(this.panel);
        this.popup.show(this.pane, event.getScreenX(), event.getScreenY());
    }

    private void stdButtons() {
        ArrayList<Button> buttons = new ArrayList<>();
        Button close = new Button("Close");
        close.setOnAction(e -> {
            this.popup.hide();
        });
        buttons.add(close);
        Button move = new Button("Move");
        move.setOnAction(e -> {

        });
        buttons.add(move);
        this.buttons = buttons;
    }

    private ArrayList<Button> getButtons() {
        return this.buttons;
    }
    public void addButton(Button button) {

    }

}
