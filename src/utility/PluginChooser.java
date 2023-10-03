package utility;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

// adding plugin button
public class PluginChooser {
    private Button chooseButton = new Button();
    private Pane pane;
    private Group root;

    public Button createChoosePluginButton(Stage stage, Group root, Pane pane) {
        initParemeters();
        this.pane = pane;
        this.root = root;
        this.chooseButton.setOnAction(e -> {
            try {
                loadPluginButtonClicked();
                this.root.getChildren().remove(this.chooseButton);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        return this.chooseButton;
    }

    private void initParemeters() {
        this.chooseButton.setText("Load plugin");
        this.chooseButton.setPrefSize(100, 50);
        this.chooseButton.setTranslateX(690);
        this.chooseButton.setTranslateY(10);
    }
    // load plugin features on click
//    public void loadPluginButtonClicked() throws Exception {
//        RealPlugin loader = new RealPlugin(this.root, this.pane);
//        loader.loadPlugin();
//    }
    public void loadPluginButtonClicked() {
        File pluginFolder = new File("src/utility/plugin");

        if (pluginFolder.exists() && pluginFolder.isDirectory()) {
            File[] files = pluginFolder.listFiles();

            if (files != null) {
                try {
                    URL[] urls = new URL[files.length];
                    for (int i = 0; i < files.length; i++) {
                        urls[i] = files[i].toURI().toURL();
                    }

                    // Загрузка всех классов из папки "plugin"
                    URLClassLoader classLoader = URLClassLoader.newInstance(urls);

                    // Загрузка основного класса
                    Class<?> mainClass = classLoader.loadClass("utility.plugin.RealPlugin"); // Замените "Main" на имя вашего основного класса
                    //Object mainInstance = mainClass.getDeclaredConstructor().newInstance();
                    // Получение конструктора класса с параметрами Group и Pane
                    Constructor<?> constructor = mainClass.getConstructor(Group.class, Pane.class);

                    // Создание объектов Group и Pane
                    Group root = this.root;
                    Pane pane = this.pane;

                    // Создание экземпляра класса RealPlugin с использованием конструктора и передача параметров
                    Object realPluginInstance = constructor.newInstance(root, pane);
                    // Получение метода "doSomething" с помощью рефлексии
                    Method loadPlugin= mainClass.getMethod("loadPlugin");

                    // Вызов метода на экземпляре основного класса
                    loadPlugin.invoke(realPluginInstance);


                    // Вызов методов или операций внутри основного класса
                } catch (Exception e) {
                    e.printStackTrace();
                    // Обработка ошибок при загрузке и инициализации основного класса
                }
            }
        } else {
            System.out.println("Папка 'plugin' не найдена.");
        }

    }
}

