import figures.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Figures");

        StackPane root = new StackPane();
        Canvas canvas = new Canvas(400, 400);
        root.getChildren().add(canvas);

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.RED);

        FigureList figureList = new FigureList();
        figureList.addFigure(new Circle(100, 100, 50));
        figureList.addFigure(new Ellipse(200, 200, 80, 40));
        figureList.addFigure(new Rectangle(250, 200, 100, 50));
        figureList.addFigure(new Parallelogram(50, 300, 100, 50, 30));
        figureList.addFigure(new Line(50, 200, 100, 50));

        figureList.drawAll(gc);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
