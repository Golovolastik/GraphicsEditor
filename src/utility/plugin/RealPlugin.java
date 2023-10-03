package utility.plugin;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import utility.*;

public class RealPlugin {
    private Group root;
    private Pane pane;
    public RealPlugin(Group root, Pane pane){
        this.root = root;
        this.pane = pane;
    }
    // load plugin features
    public void loadPlugin() throws Exception {
        addStarButton();
        addColorButton();
        addScaleButton();
        addMirrorButton();
    }
    // configure star button
    private Button createStarButton() throws Exception {
        Button star = new Button();
        star.setPrefSize(50, 35);
        Polygon icon = createStarIcon();
        star.setGraphic(icon);
        DrawingController controller = DrawingController.getInstance(this.pane, Painter.getInstance(this.pane));
        controller.configureButton(star, Star.class);
        return star;
    }
    // icon for star button
    private Polygon createStarIcon() {
        Star pic = new Star(15, 15, 15);
        pic.init();
        Polygon star = new Polygon();
        star.setFill(Color.WHITE);
        star.setStroke(Color.BLACK);
        star.getPoints().addAll(pic.getPoints());
        return star;
    }
    // add button to button panel
    private void addStarButton() throws Exception {
        Button star = createStarButton();
        FigureButtons buttons = FigureButtons.getInstance();
        DrawingController controller = DrawingController.getInstance(this.pane, Painter.getInstance(this.pane));
        this.root.getChildren().remove(controller.getButtonPanel());
        buttons.addButton(star);
        VBox newPanel = controller.createButtonPanel();
        this.root.getChildren().add(newPanel);
    }
    // add change color button to popup panel
    private void addColorButton() {
        ColorChanger changer = new ColorChanger();
        Button changeColor = changer.changeColorButton();
        PopupPanel popup = PopupPanel.getInstance(this.pane);
        popup.addButton(changeColor);
    }
    // add change scale button to popup panel
    private void addScaleButton() {
        Scaler changer = new Scaler(this.pane);
        Button scale = changer.createScaledButton();
        PopupPanel popup = PopupPanel.getInstance(this.pane);
        popup.addButton(scale);
    }
    // add mirroring button
    private void addMirrorButton() {
        Mirror mirror = new Mirror(this.pane);
        Button mir = mirror.createMirrorButton();
        PopupPanel popup = PopupPanel.getInstance(this.pane);
        popup.addButton(mir);
    }
}
