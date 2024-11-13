import java.util.ArrayList;
import java.util.List;

public class TASK3 {
    public static void main(String[] args) {
        try {
            // Попробуем создать огромный список, чтобы вызвать OutOfMemoryError
            List<Wing> wings = new ArrayList<>();
            while (true) {
                wings.add(new Wing(20.0, 50.0));  // Постоянно добавляем объекты
            }
        } catch (OutOfMemoryError e) {
            System.out.println("Недостаточно памяти для выполнения программы.");
        } catch (Exception e) {
            System.out.println("Неожиданная ошибка: " + e.getMessage());
        }
    }
}
