package utility;

import figures.Figure;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public void test() {
        save();
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
        //System.out.println(content);
        extractFigures(content);
        this.painter.drawAll();
    }
    private void extractFigures(String content) {
        HashMap<String, Double> params = new HashMap<>();

        String[] lines = content.split("\n");
        for (String line : lines) {
            if (line.startsWith("figures.")) {
                // Создаем новый объект класса на основе имени
                try {
                    String className = line.trim();
                    Class<?> obj = Class.forName(className);
                    Figure figure = (Figure) obj.getDeclaredConstructor().newInstance();
                    this.list.addFigure(figure);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (line.startsWith("x")) {
                String[] parts = line.split(" ");
                System.out.println("x" + parts[1]);
                this.list.getFigures().get(this.list.getFigures().size() - 1).setX(Double.parseDouble(parts[1]));
            } else if (line.startsWith("y")) {
                String[] parts = line.split(" ");
                System.out.println("y" + parts[1]);
                this.list.getFigures().get(this.list.getFigures().size() - 1).setY(Double.parseDouble(parts[1]));
            } else if (!line.equals("-1")) {
                // Разбиваем строку на имя параметра и значение параметра
                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    String paramName = parts[0];
                    double paramValue = Double.parseDouble(parts[1]);
                    params.put(paramName, paramValue);
                }
            } else {
                // Это конец фигуры, устанавливаем параметры фигуры и очищаем HashMap
                    this.list.getFigures().get(this.list.getFigures().size() - 1).setParameters(params);
                    params.clear();
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
