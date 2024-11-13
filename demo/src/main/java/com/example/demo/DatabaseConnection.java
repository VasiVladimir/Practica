package com.example.demo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/telephone_exchange";  // Укажи свою базу данных
    private static final String USER = "postgres";  // Имя пользователя PostgreSQL (замени на своё)
    private static final String PASSWORD = "Jabl2Ivar";  // Пароль пользователя PostgreSQL

    public static Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Successfully connected to the database.");
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();  // Вывести полное сообщение об ошибке в консоль
        }
        return connection;
    }
}

