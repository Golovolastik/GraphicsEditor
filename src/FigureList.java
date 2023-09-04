import figures.Figure;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class FigureList {
    private List<Figure> figures = new ArrayList<>();

    public void addFigure(Figure figure) {
        figures.add(figure);
    }

    public void drawAll(GraphicsContext gc) {
        for (Figure figure : figures) {
            figure.draw(gc);
        }
    }
}
