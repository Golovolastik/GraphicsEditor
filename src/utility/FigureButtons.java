package utility;

import javafx.scene.control.Button;

import java.util.ArrayList;

public class FigureButtons {
    private static FigureButtons instance;
    private ArrayList<Button> buttonArray = new ArrayList<>();
    private FigureButtons() {

    }
    public static FigureButtons getInstance() {
        if (instance == null) {
            synchronized (FigureButtons.class) {
                if (instance == null) {
                    instance = new FigureButtons();
                }
            }
        }
        return instance;
    }

    public void addButton(Button button) {
        this.buttonArray.add(button);
    }

    public ArrayList<Button> getButtonArray() {
        return this.buttonArray;
    }
    public void clear(){
        this.buttonArray.clear();
    }
    public void printButtons(){
        for(Button button: this.buttonArray) {
            System.out.println(button.getGraphic());
        }
    }

}
