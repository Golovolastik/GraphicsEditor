package utility;

import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class PluginChooser {
    private Button chooseButton = new Button();

    public Button createChoosePluginButton(Stage stage){
        initParemeters();
        this.chooseButton.setOnAction(e -> loadPluginButtonClicked());

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
            Class<?> starClass = classLoader.loadClass("Ololo");

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
}

