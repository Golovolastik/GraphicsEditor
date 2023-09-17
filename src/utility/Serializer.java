package utility;

import java.io.*;

public class Serializer {
    private FigureList list;
    public Serializer(FigureList list) {
        this.list = list;
    }
    // Метод для сохранения списка фигур в файл
    public static void save(FigureList list, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(list);
            System.out.println("Список фигур успешно сохранен в файл " + filename);
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении списка фигур: " + e.getMessage());
        }
    }

    // Метод для загрузки списка фигур из файла
    public static FigureList load(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            FigureList list = (FigureList) ois.readObject();
            System.out.println("Список фигур успешно загружен из файла " + filename);
            return list;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка при загрузке списка фигур: " + e.getMessage());
            return null;
        }
    }



}
