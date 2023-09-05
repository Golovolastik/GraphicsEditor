import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Optional;

public class DialogWindow {

    private TextInputDialog dialog;

    public DialogWindow() {
        // Создаем диалоговое окно для ввода координат
        dialog = new TextInputDialog();
        dialog.setTitle("Введите координаты");
        dialog.setHeaderText(null);
        dialog.setContentText("Введите координаты (x, y):");
    }

    public Optional<String> showAndWait() {
        return dialog.showAndWait();
    }

    public Circle createCircleFromInput(String input) {
        String[] parts = input.split(",");
        if (parts.length == 2) {
            try {
                double x = Double.parseDouble(parts[0].trim());
                double y = Double.parseDouble(parts[1].trim());

                // Создаем и возвращаем круг
                double radius = 10.0; // Замените на ваше значение радиуса
                Circle circle = new Circle(x, y, radius);
                circle.setFill(Color.BLUE); // Настройте цвет и другие свойства фигуры

                return circle;
            } catch (NumberFormatException e) {
                // Если введенные координаты не удалось преобразовать в числа
                // Обработайте ошибку или выведите сообщение об ошибке
                System.out.println("Неверный формат координат");
            }
        }
        return null; // Возвращаем null в случае ошибки или неверного формата
    }
}
