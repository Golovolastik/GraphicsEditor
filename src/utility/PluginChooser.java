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
    // button parameters
    private void initParemeters() {
        this.chooseButton.setText("Load plugin");
        this.chooseButton.setPrefSize(100, 50);
        this.chooseButton.setTranslateX(690);
        this.chooseButton.setTranslateY(10);
    }
    // load plugin features on click
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
                    // upload plugin classes
                    URLClassLoader classLoader = URLClassLoader.newInstance(urls);
                    // upload main class - RealPlugin
                    Class<?> mainClass = classLoader.loadClass("utility.plugin.RealPlugin");
                    // class constructor
                    Constructor<?> constructor = mainClass.getConstructor(Group.class, Pane.class);
                    // pane and group objects
                    Group root = this.root;
                    Pane pane = this.pane;
                    // plugin class instance
                    Object realPluginInstance = constructor.newInstance(root, pane);
                    // get loadPlugin method
                    Method loadPlugin= mainClass.getMethod("loadPlugin");
                    // run method
                    loadPlugin.invoke(realPluginInstance);
                } catch (Exception e) {
                    // no plugin exception
                    System.out.println("No plugin");
                    e.printStackTrace();
                }
            }
        } else {
            // can't find plugin folder
            System.out.println("No plugin directory");
        }
    }
}

