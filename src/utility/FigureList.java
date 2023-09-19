package utility;

import figures.Figure;

import java.util.ArrayList;
import java.util.List;

public class FigureList {
    private static volatile FigureList instance;
    private List<Figure> figures = new ArrayList<>();
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
    public void deleteFigure(Figure figure){
        this.figures.remove(figure);
    }

    public void remove(Figure figure) {
        this.figures.remove(figure);
    }

    public void clear() {
        this.figures.clear();
    }
    public void printFigures() {
        System.out.println("Start of List");
        for (Figure figure: this.figures) {
            System.out.println(figure);
        }
        System.out.println("End of List");
    }
}
