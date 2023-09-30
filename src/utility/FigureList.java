package utility;

import figures.Figure;

import java.util.ArrayList;
import java.util.List;

// singleton class to keeping all figures
public class FigureList {
    private static volatile FigureList instance;
    private final List<Figure> figures = new ArrayList<>();
    private FigureList(){

    }
    public static FigureList getInstance() {
        if (instance == null) {
            synchronized (FigureList.class) {
                if (instance == null) {
                    instance = new FigureList();
                }
            }
        }
        return instance;
    }
    public void addFigure(Figure figure) {
        if (figures.contains(figure)) {
            return;
        }
        figures.add(figure);

    }
    public List<Figure> getFigures() {
        return this.figures;
    }
    public void remove(Figure figure) {
        this.figures.remove(figure);
    }
    public void clear() {
        this.figures.clear();
    }
}
