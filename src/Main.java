import figures.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Figures");

        Group root = new Group();
        Pane board = new Pane();
        board.setPrefSize(800, 600);
        DrawingController controller = new DrawingController(board);
        VBox buttonPanel = controller.createButtonPanel();
        root.getChildren().addAll(board, buttonPanel);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);


        FigureList figureList = new FigureList();
        Circle circle = new Circle(400, 300);
        circle.setRadius(150);
        figureList.addFigure(circle);
        figureList.addFigure(new Ellipse(400, 200));
        figureList.addFigure(new Rectangle(350, 450));
        figureList.addFigure(new Parallelogram(150, 350));
        figureList.addFigure(new Line(150, 250, 400, 50));

        Painter painter = new Painter(board);
        painter.drawAll(figureList);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
