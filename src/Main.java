import figures.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utility.FigureList;
import utility.PluginChooser;
import utility.Serializer;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Figures");

        Group root = new Group();
        Pane board = new Pane();
        board.setPrefSize(800, 600);
        FigureList figureList = new FigureList();
        Serializer serializer = new Serializer(figureList);
        Painter painter = new Painter(board, figureList);
        DrawingController controller = new DrawingController(board, painter, figureList);
        VBox buttonPanel = controller.createButtonPanel();

        Button plugin = new PluginChooser().createChoosePluginButton(primaryStage);

        root.getChildren().addAll(board, buttonPanel, plugin);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);


        Circle circle = new Circle(400, 300);
        circle.setRadius(150);
        //painter.draw(circle);
        figureList.addFigure(circle);
        figureList.addFigure(new Ellipse(400, 200));
        figureList.addFigure(new Rectangle(350, 450));
        figureList.addFigure(new Parallelogram(150, 350));
        figureList.addFigure(new Line(150, 250, 400, 50));
        //figureList.addFigure(new Star(250, 250));


        painter.drawAll();
        serializer.test();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
