package utility;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utility.plugin.PluginLoader;

public class PluginChooser {
    private Button chooseButton = new Button();
    private Pane pane;
    private Group root;

    public Button createChoosePluginButton(Stage stage, Group root, Pane pane) {
        initParemeters();
        this.pane = pane;
        this.root = root;
        this.chooseButton.setOnAction(e -> {
            try {
                loadPluginButtonClicked();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        return this.chooseButton;
    }

    private void initParemeters() {
        this.chooseButton.setText("Load plugin");
        this.chooseButton.setPrefSize(100, 50);
        this.chooseButton.setTranslateX(690);
        this.chooseButton.setTranslateY(10);
    }
    public void loadPluginButtonClicked() throws Exception {
        PluginLoader loader = new PluginLoader(this.root, this.pane);
        loader.loadPlugin();
    }


}

