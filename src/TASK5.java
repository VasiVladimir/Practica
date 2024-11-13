import java.util.Scanner;

public class TASK5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Запрашиваем ввод текста от пользователя
        System.out.println("Введите текст:");
        String text = scanner.nextLine();

        // Используем регулярное выражение для замены "Ра" на "Ро", если "а" не последняя в слове
        String correctedText = text.replaceAll("Р(?=а[б-яё])", "Ро");

        System.out.println("Исправленный текст: " + correctedText);
    }
}
