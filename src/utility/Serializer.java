package utility;

import figures.Figure;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class Serializer {
    private FigureList list;
    private Pane pane;
    private Painter painter;

    public Serializer(Pane pane)
    {
        this.list = FigureList.getInstance();
        this.pane = pane;
        this.painter = Painter.getInstance(this.pane);
    }
    public void save(){
        String filename = genFileName();
        String content = serializeList();
        writeToFile(filename, content);
    }
    // Метод для сохранения списка фигур в файл
    private void writeToFile(String filename, String content) {
        try {
            // Создайте объект File с указанным путем
            File file = new File(filename + ".yge");

            // Создайте FileWriter для записи в файл
            FileWriter fileWriter = new FileWriter(file);

            // Создайте BufferedWriter для более эффективной записи
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Создайте PrintWriter для записи строки в файл
            PrintWriter printWriter = new PrintWriter(bufferedWriter);

            // Запишите строку в файл
            printWriter.println(content);

            // Закройте PrintWriter, BufferedWriter и FileWriter
            printWriter.close();
            bufferedWriter.close();
            fileWriter.close();

            System.out.println("Строка успешно записана в файл.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String serializeList() {
        String result = new String();
        for (Figure figure: this.list.getFigures()) {
            result = result.concat(prepareFigure(figure));
        }
        return result;
    }
    private String prepareFigure(Figure figure) {
        String result = new String();
        String name = figure.getClass().getName() + "\n";
        result = result.concat(name);
        result = result.concat(figure.getBorderColor().toString() + "\n");
        double x = figure.getX();
        double y = figure.getY();
        result = result.concat("x " + Double.toString(x) + "\n");
        result = result.concat("y " + Double.toString(y) + "\n");
        String params = new String();
        for (String parameter: figure.getParameters().keySet()) {
            String temp = parameter + " " + figure.getParameters().get(parameter) + "\n";
            params = params.concat(temp);
        }
        result = result.concat(params);
        result = result.concat("-1\n");
        return result;
    }
    private String genFileName() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH:mm");
        return currentDateTime.format(formatter);
    }

    // Метод для загрузки списка фигур из файла
    public void load() throws Exception {
        clearBoard();
        File file = chooseFile();
        String content = readFile(file);
        ArrayList<String> figures = extractFigures(content);
        initFigureList(figures);
        this.painter.drawAll();
    }
    private ArrayList<String> extractFigures(String content) {
        ArrayList<String> figures = new ArrayList<>();
        String[] lines = content.split("\n");
        String temp = new String();
        for (String line: lines) {
            if (line.equals("-1")) {
                figures.add(temp);
                temp = "";
                continue;
            }
            temp = temp.concat(line + "\n");
        }
        return figures;
    }
    private void initFigureList(ArrayList<String> figures) {
        for (String figureString: figures) {
            String[] lines = figureString.split("\n");
            try {
                String className = lines[0].trim();
                Class<?> obj = Class.forName(className);
                Figure figure = (Figure) obj.getDeclaredConstructor().newInstance();
                figure.setBorderColor(Color.valueOf(lines[1]));
                figure.setX(Double.parseDouble(lines[2].split(" ")[1]));
                figure.setY(Double.parseDouble(lines[3].split(" ")[1]));
                HashMap<String, Double> params = new HashMap<>();
                for (int i=4; i<lines.length; i++){
                    String[] paramsString = lines[i].split(" ");
                    params.put(paramsString[0], Double.parseDouble(paramsString[1]));
                }
                figure.setParameters(params);
                this.list.addFigure(figure);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void clearBoard(){
        this.pane.getChildren().clear();
        this.list.clear();
    }
    private String readFile(File file) {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line);
                content.append("\n");
            }
            bufferedReader.close();
            fileReader.close();
            return content.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private File chooseFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Editor's files", "*.yge")
        );
        fileChooser.setTitle("Choose file");
        java.io.File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            System.out.println("File opened: " + selectedFile.getAbsolutePath());
        }
        return selectedFile;
    }

    public HBox createButtonPanel() {
        HBox panel = new HBox();

        Button saveButton = new Button("Save");
        saveButton.setOnMousePressed(e -> save());

        Button openButton = new Button("Open");
        openButton.setOnMousePressed(e -> {
            try {
                load();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        panel.getChildren().addAll(saveButton, openButton);
        panel.setLayoutX(70);
        panel.setLayoutY(10);

        return panel;
    }
}
