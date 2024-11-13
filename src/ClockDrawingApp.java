import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

// Главный класс приложения
public class ClockDrawingApp {
    public static void main(String[] args) {
        // Создаем основное окно
        JFrame frame = new JFrame("Задания");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        // Меню
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Задания");

        // Пункты меню
        JMenuItem task1 = new JMenuItem("Задание 1: Нарисовать часы");
        JMenuItem task2 = new JMenuItem("Задание 2: Меню в форме столбца");
        JMenuItem task3 = new JMenuItem("Задание 3: Игра Математико");

        // Добавляем пункты в меню
        menu.add(task1);
        menu.add(task2);
        menu.add(task3);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);

        // Панель для различных задач
        ClockPanel clockPanel = new ClockPanel();
        ColumnMenuPanel columnMenuPanel = new ColumnMenuPanel();
        GamePanel gamePanel = new GamePanel();
        frame.add(clockPanel);

        // Обработчики для пунктов меню
        task1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.add(clockPanel);
                frame.revalidate();
                frame.repaint();
            }
        });

        task2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.add(columnMenuPanel);
                frame.revalidate();
                frame.repaint();
            }
        });

        task3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.add(gamePanel);
                frame.revalidate();
                frame.repaint();
            }
        });

        // Отображаем окно
        frame.setVisible(true);
    }
}

// Панель для рисования часов
class ClockPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Рисуем циферблат
        g.drawOval(100, 50, 200, 200);

        // Центр циферблата
        int centerX = 200;
        int centerY = 150;
        int radius = 90;

        // Все цифры на циферблате
        String[] hours = {"12", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};
        g.setFont(new Font("Arial", Font.BOLD, 20));

        // Отрисовываем цифры
        for (int i = 0; i < 12; i++) {
            double angle = Math.toRadians((i * 30) - 60); // угол для каждой цифры
            int x = centerX + (int) (Math.cos(angle) * radius) - 10; // смещение по горизонтали
            int y = centerY + (int) (Math.sin(angle) * radius) + 10; // смещение по вертикали

            g.drawString(hours[i], x, y);
        }

        // Часовая стрелка (на 10 часов)
        drawArrow(g, centerX, centerY, 150, 100);

        // Минутная стрелка (на 2 часа)
        drawArrow(g, centerX, centerY, 250, 150);
    }

    // Метод для рисования стрелок с наконечником
    private void drawArrow(Graphics g, int x1, int y1, int x2, int y2) {
        g.drawLine(x1, y1, x2, y2);

        // Рисуем наконечник стрелки
        int arrowSize = 10; // размер наконечника
        double angle = Math.atan2(y2 - y1, x2 - x1); // угол стрелки
        int x3 = (int) (x2 - arrowSize * Math.cos(angle - Math.PI / 6));
        int y3 = (int) (y2 - arrowSize * Math.sin(angle - Math.PI / 6));
        int x4 = (int) (x2 - arrowSize * Math.cos(angle + Math.PI / 6));
        int y4 = (int) (y2 - arrowSize * Math.sin(angle + Math.PI / 6));

        g.drawLine(x2, y2, x3, y3);
        g.drawLine(x2, y2, x4, y4);
    }
}

// Панель для меню в столбик
class ColumnMenuPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Рисуем меню в виде столбца
        g.setFont(new Font("Arial", Font.BOLD, 16));
        String[] menuItems = {"Пункт 1", "Пункт 2", "Пункт 3", "Пункт 4"};

        for (int i = 0; i < menuItems.length; i++) {
            g.drawString(menuItems[i], 50, 50 + i * 30);
        }
    }
}

// Панель для игры "Математико"
class GamePanel extends JPanel {
    private int[][] board = new int[5][5]; // Поле 5x5

    public GamePanel() {
        fillBoard(); // Заполняем поле
    }

    private void fillBoard() {
        ArrayList<Integer> cards = new ArrayList<>();
        for (int i = 1; i <= 13; i++) {
            for (int j = 0; j < 4; j++) {
                cards.add(i); // Добавляем каждое число по 4 раза
            }
        }
        Collections.shuffle(cards); // Перемешиваем карты

        // Заполняем поле
        int index = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                board[i][j] = cards.get(index++);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Рисуем поле 5x5
        g.setFont(new Font("Arial", Font.BOLD, 20));
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                g.drawRect(50 + j * 50, 50 + i * 50, 50, 50); // Клетки
                g.drawString(Integer.toString(board[i][j]), 65 + j * 50, 85 + i * 50); // Числа в клетках
            }
        }
    }
}
