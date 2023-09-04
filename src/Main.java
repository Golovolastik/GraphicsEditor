import figures.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Figures");

        StackPane root = new StackPane();
        Canvas canvas = new Canvas(800, 600);
        DrawingController controller = new DrawingController(canvas);
        // Создаем панель с кнопками
        HBox buttonPanel = controller.createButtonPanel();
        root.getChildren().addAll(canvas, buttonPanel);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        FigureList figureList = new FigureList();
        figureList.addFigure(new Circle(100, 100, 50));
        figureList.addFigure(new Ellipse(200, 200, 80, 40));
        figureList.addFigure(new Rectangle(250, 200, 100, 50));
        figureList.addFigure(new Parallelogram(50, 300, 100, 50, 30));
        figureList.addFigure(new Line(50, 200, 100, 50));

        for (Figure figure: figureList.getFigures()) {
            //figure.setColor(Color.AQUA);
            figure.draw(gc);
        }

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
