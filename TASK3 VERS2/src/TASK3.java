import java.util.Arrays;

public class TASK3 {
    public static void main(String[] args) {
        try {
            Wing leftWing = new Wing(10, 500);
            Wing rightWing = new Wing(-10, 500);
            Chassis chassis = new Chassis(3);
            Engine engine1 = new Engine("Джет", 100000);
            Engine engine2 = new Engine("Джет", 100000);

            Airplane airplane = new Airplane(
                    Arrays.asList(leftWing, rightWing),
                    chassis,
                    Arrays.asList(engine1, engine2)
            );

            airplane.setRoute("Из Нью-Йорка в Лондон");
            airplane.printRoute();
            airplane.fly();
        } catch (InvalidWingLengthException | InvalidEnginePowerException | InsufficientEnginesException | RouteNotSetException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } catch (OutOfMemoryError e) {
            System.out.println("Недостаточно памяти для выполнения программы.");
        } catch (Exception e) {
            System.out.println("Неожиданная ошибка: " + e.getMessage());
        }
    }
}
