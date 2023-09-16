package utility;

import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class PluginChooser {
    private Button chooseButton = new Button();

    public Button createChoosePluginButton(Stage stage) {
        initParemeters();
        this.chooseButton.setOnAction(e -> loadPluginButtonClicked());
//        this.chooseButton.setOnAction(e -> {
//            try {
//                fromStackOverFlow();
//            } catch (Exception ex) {
//                throw new RuntimeException(ex);
//            }
//        });

        return this.chooseButton;
    }

    private void initParemeters() {
        this.chooseButton.setText("Load plugin");
        this.chooseButton.setPrefSize(100, 50);
        this.chooseButton.setTranslateX(690);
        this.chooseButton.setTranslateY(10);
    }
    public void loadPluginButtonClicked() {
//        String currentDirectory = System.getProperty("user.dir");
//        System.out.println(currentDirectory);
        try {
            // Получить текущий рабочий каталог
            String currentDirectory = System.getProperty("user.dir");

            // Создать URL для каталога с плагином (папка plugin)
            File utilityDirectory = new File(currentDirectory, "utility");
            File pluginDirectory = new File(utilityDirectory, "plugin");
            System.out.println(pluginDirectory);

            URLClassLoader classLoader = new URLClassLoader(new URL[]{pluginDirectory.toURI().toURL()});

            System.out.println(classLoader);

            // Загрузить класс Star из директории plugin
            Class<?> starClass = classLoader.loadClass("Star");

            System.out.println(starClass);

            // Создать экземпляр класса Star
            Object starInstance = starClass.getDeclaredConstructor().newInstance();

//            // Вызвать методы рисования и изменения размера
            Method drawMethod = starClass.getDeclaredMethod("hello");
            drawMethod.invoke(starInstance);
//
//            Method resizeMethod = starClass.getDeclaredMethod("resize", int.class);
//            resizeMethod.invoke(starInstance, 50);

            // Закрыть класслоадер
            classLoader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fromStackOverFlow() throws Exception {
        // Путь к JAR-файлу, который нужно загрузить
        String jarFilePath = "/Users/aleksejankovic/Desktop/Study/bsuir/3курс/1сем/ООП/ipr1/GraphicsEditor/src/utility/plugin/JarExample.jar";

        // Преобразование пути к JAR-файлу в формат URL
        URL jarUrl = new URL("file:" + jarFilePath);

        // Создаем новый URLClassLoader с JAR-файлом
        URLClassLoader classLoader = new URLClassLoader(new URL[]{jarUrl});
        // Имя класса, который вы хотите загрузить и вызвать
        String className = "Ololo"; // Замените на имя вашего класса

        // Загрузка класса из JAR-файла
        Class<?> loadedClass = classLoader.loadClass(className);

        // Создание экземпляра класса (предполагается, что у класса есть конструктор по умолчанию)
        Object instance = loadedClass.newInstance();

        // Вызов метода из загруженного класса
        Method method = loadedClass.getMethod("hello"); // Замените на имя метода
        Object result = method.invoke(instance);

        // Вывод результата
        System.out.println("Результат выполнения метода: " + result);

        // Закрываем класслоадер после использования
        classLoader.close();
    }
}

