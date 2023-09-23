import figures.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utility.*;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Figures");

        Group root = new Group();
        Pane board = new Pane();
        board.setPrefSize(800, 600);
        FigureList figureList = FigureList.getInstance();
        Serializer serializer = new Serializer(board);
        Painter painter = Painter.getInstance(board);
        DrawingController controller = DrawingController.getInstance(board, painter);
        VBox buttonPanel = controller.getButtonPanel();
        HBox openSavePanel = serializer.createButtonPanel();
        Button plugin = new PluginChooser().createChoosePluginButton(primaryStage, root, board);

        root.getChildren().addAll(board, buttonPanel, plugin, openSavePanel);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);

        Circle circle = new Circle(400, 300);
        circle.setRadius(150);
        figureList.addFigure(circle);
        figureList.addFigure(new Ellipse(400, 200));
        figureList.addFigure(new Rectangle(350, 450));
        figureList.addFigure(new Parallelogram(150, 350));
        figureList.addFigure(new Line(150, 250, 400, 50));

        painter.drawAll();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
