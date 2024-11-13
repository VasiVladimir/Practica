import java.text.DecimalFormat;
import java.util.Scanner;

public class QuadraticEquation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите коэффициенты a, b и c:");
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        double a = scanner.nextDouble();
        double b = scanner.nextDouble();
        double c = scanner.nextDouble();

        double D = b * b - 4 * a * c;

        if (D > 0) {
            double x1 = (-b + Math.sqrt(D)) / (2 * a);
            double x2 = (-b - Math.sqrt(D)) / (2 * a);
            String result1 = decimalFormat.format(x1);
            String result2 = decimalFormat.format(x2);
            System.out.println("Уравнение имеет два корня: \n" + "x1 = " + result1 + "\nx2 = " + result2);

        } else if (D == 0) {
                double x = -b / (2 * a);
            String result = decimalFormat.format(x);
            System.out.println("Уравнение имеет один корень: \n" + "x = " + result);
        } else {
            System.out.println("Уравнение не имеет действительных корней.");
        }

        scanner.close();
    }
}
