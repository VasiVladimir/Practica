package com.example.demo;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    // Метод для обработки нажатия на кнопку "Login"
    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (validateLogin(username, password)) {
            // Переход на новое окно с навигационной панелью после успешной авторизации
            try {
                // Загружаем новый FXML файл (dashboard.fxml)
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/demo/dashboard.fxml"));
                Parent dashboardRoot = fxmlLoader.load();

                // Создаем новую сцену для нового окна с указанными размерами
                Scene dashboardScene = new Scene(dashboardRoot, 800, 600);

                // Подключаем CSS файл
                dashboardScene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

                // Получаем текущее окно (Stage)
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Устанавливаем новую сцену для текущего окна
                window.setScene(dashboardScene);
                window.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Выводим сообщение об ошибке, если авторизация не прошла
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password!");
            alert.showAndWait();
        }
    }

    // Метод для проверки введенных данных (логин и пароль) через базу данных
    private boolean validateLogin(String username, String password) {
        // Пример логики проверки через SQL-запрос
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        boolean isValid = false;

        try (Connection conn = DatabaseConnection.connect();  // Устанавливаем соединение с базой данных
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Устанавливаем параметры запроса
            stmt.setString(1, username);
            stmt.setString(2, password);

            // Выполняем запрос к базе данных
            ResultSet rs = stmt.executeQuery();

            // Если найдена запись с такими логином и паролем, авторизация успешна
            if (rs.next()) {
                isValid = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isValid;
    }
}
