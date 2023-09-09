import figures.Figure;

import java.util.ArrayList;
import java.util.List;

public class FigureList {
    private List<Figure> figures = new ArrayList<>();

    public void addFigure(Figure figure) {
        figures.add(figure);
    }
    public List<Figure> getFigures() {
        return this.figures;
    }
}
