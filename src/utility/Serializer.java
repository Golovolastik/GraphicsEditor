package utility;

import figures.Figure;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Serializer {
    private FigureList list;

    public Serializer(FigureList list) {
        this.list = list;
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
        String name = figure.getClass().getName().split("\\.", 2)[1] + "\n";
        result = result.concat(name);
        String params = new String();
        for (String parameter: figure.getParameters().keySet()) {
            String temp = parameter + " " + figure.getParameters().get(parameter) + "\n";
            params = params.concat(temp);
        }
        result = result.concat(params);
        result = result.concat("-1\n");
        return result;
        //System.out.println(result);
    }
    private String genFileName() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH:mm");
        return currentDateTime.format(formatter);
    }

    // Метод для загрузки списка фигур из файла
    public FigureList load(String filename) {
        return null;
    }
}
