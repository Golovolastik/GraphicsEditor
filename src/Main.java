import figures.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Figures");

        Group root = new Group();
        Canvas canvas = new Canvas(800, 600);
        DrawingController controller = new DrawingController(canvas);
        // Создаем панель с кнопками
        VBox buttonPanel = controller.createButtonPanel();
        root.getChildren().addAll(canvas, buttonPanel);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        FigureList figureList = new FigureList();
        figureList.addFigure(new Circle(200, 100));
        figureList.addFigure(new Ellipse(400, 200));
        figureList.addFigure(new Rectangle(350, 450));
        figureList.addFigure(new Parallelogram(150, 350));
        figureList.addFigure(new Line(150, 250, 400, 50));

        for (Figure figure: figureList.getFigures()) {
            figure.setStrokeColor(Color.RED);
            figure.draw(gc);
        }

        Rectangle test = new Rectangle(456, 345);
        Painter painter = new Painter(gc);
        painter.draw(test);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
