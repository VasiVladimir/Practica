package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class DashboardController {

    @FXML
    private TableView<Call> callTable;  // Таблица для звонков

    @FXML
    private TableColumn<Call, String> fioOutgoingColumn;
    @FXML
    private TableColumn<Call, String> fioIncomingColumn;
    @FXML
    private TableColumn<Call, String> outgoingNumberColumn;
    @FXML
    private TableColumn<Call, String> incomingNumberColumn;

    @FXML
    private TableView<User> debtTable;  // Таблица для долгов

    @FXML
    private TableColumn<User, String> fioColumn;
    @FXML
    private TableColumn<User, String> addressColumn;
    @FXML
    private TableColumn<User, String> tariffColumn;
    @FXML
    private TableColumn<User, Double> priceColumn;
    @FXML
    private TableColumn<User, Double> balanceColumn;
    @FXML
    private TableColumn<User, String> statusColumn;

    @FXML
    private TableView<ClientNumber> clientNumbersTable;  // Новая таблица для номеров клиентов

    @FXML
    private TableColumn<ClientNumber, String> clientFioColumn;
    @FXML
    private TableColumn<ClientNumber, String> clientNumberColumn;
    @FXML
    private TableColumn<ClientNumber, String> clientAddressColumn;
    @FXML
    private TableColumn<ClientNumber, String> clientModelColumn;

    @FXML
    private TableView<Tariff> tariffTable;  // Новая таблица для тарифов

    @FXML
    private TableColumn<Tariff, String> tariffFioColumn;
    @FXML
    private TableColumn<Tariff, String> tariffNumberColumn;
    @FXML
    private TableColumn<Tariff, String> tariffTypeColumn;
    @FXML
    private TableColumn<Tariff, Double> tariffPriceColumn;


    @FXML
    public void initialize() {
        // Привязка столбцов долгов к полям объекта User
        fioColumn.setCellValueFactory(new PropertyValueFactory<>("fio"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        tariffColumn.setCellValueFactory(new PropertyValueFactory<>("tariff"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Привязка столбцов звонков к полям объекта Call
        fioOutgoingColumn.setCellValueFactory(new PropertyValueFactory<>("fioOutgoing"));
        fioIncomingColumn.setCellValueFactory(new PropertyValueFactory<>("fioIncoming"));
        outgoingNumberColumn.setCellValueFactory(new PropertyValueFactory<>("outgoingNumber"));
        incomingNumberColumn.setCellValueFactory(new PropertyValueFactory<>("incomingNumber"));

        // Скрываем обе таблицы при загрузке
        debtTable.setVisible(false);
        callTable.setVisible(false);

        // Привязка столбцов для таблицы "Номера клиентов"
        clientFioColumn.setCellValueFactory(new PropertyValueFactory<>("fio"));
        clientNumberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        clientAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        clientModelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));

        // Привязка столбцов для таблицы тарифов
        tariffFioColumn.setCellValueFactory(new PropertyValueFactory<>("fio"));
        tariffNumberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        tariffTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        tariffPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    // Метод для отображения клиентов с долгами
    @FXML
    private void handleDebtQueryAction() {
        ObservableList<User> allClients = FXCollections.observableArrayList();

        // Очищаем таблицу перед загрузкой новых данных
        debtTable.getItems().clear();

        // SQL-запрос для получения данных о клиентах с долгами
        String query = "SELECT Абонент.ФИО, Абонент.Адрес, Тариф.Тариф, Тариф.Цена, Статус_Клиента.Баланс, Статус_Клиента.Заблокирован_Разблокирован " +
                "FROM Статус_Клиента " +
                "JOIN Абонент ON Статус_Клиента.Абонентid = Абонент.Абонентid " +
                "JOIN Тариф ON Абонент.Абонентid = Тариф.Абонентid " +
                "WHERE Статус_Клиента.Баланс < 0";  // Фильтрация клиентов с долгами

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                User client = new User(
                        rs.getString("ФИО"),
                        rs.getString("Адрес"),
                        rs.getString("Тариф"),
                        rs.getDouble("Цена"),
                        rs.getDouble("Баланс"),
                        rs.getString("Заблокирован_Разблокирован")
                );

                allClients.add(client);  // Добавляем клиента в список
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Скрываем таблицу звонков и отображаем таблицу долгов
        callTable.setVisible(false);
        debtTable.setVisible(true);
        clientNumbersTable.setVisible(false);
        tariffTable.setVisible(false);
        debtTable.setItems(allClients);  // Устанавливаем данные в таблицу
    }

    // Метод для отображения разблокированных клиентов без долгов
    @FXML
    private void handleUnlockedClientsAction() {
        ObservableList<User> unlockedClients = FXCollections.observableArrayList();

        // Очищаем таблицу перед загрузкой новых данных
        debtTable.getItems().clear();

        String query = "SELECT Абонент.ФИО, Абонент.Адрес, Тариф.Тариф, Тариф.Цена, Статус_Клиента.Баланс, Статус_Клиента.Заблокирован_Разблокирован " +
                "FROM Статус_Клиента " +
                "JOIN Абонент ON Статус_Клиента.Абонентid = Абонент.Абонентid " +
                "JOIN Тариф ON Абонент.Абонентid = Тариф.Абонентid " +
                "WHERE Статус_Клиента.Баланс >= 0";  // Фильтрация клиентов без долгов

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                User client = new User(
                        rs.getString("ФИО"),
                        rs.getString("Адрес"),
                        rs.getString("Тариф"),
                        rs.getDouble("Цена"),
                        rs.getDouble("Баланс"),
                        rs.getString("Заблокирован_Разблокирован")
                );

                unlockedClients.add(client);  // Добавляем клиента в список разблокированных
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Скрываем таблицу звонков и отображаем таблицу долгов
        callTable.setVisible(false);
        debtTable.setVisible(true);
        clientNumbersTable.setVisible(false);
        tariffTable.setVisible(false);
        debtTable.setItems(unlockedClients);  // Устанавливаем данные о разблокированных клиентах в таблицу
    }

    // Метод для отображения звонков
    @FXML
    private void handleCallsQueryAction() {
        ObservableList<Call> callList = FXCollections.observableArrayList();

        // Очищаем таблицу перед загрузкой новых данных
        callTable.getItems().clear();

        // SQL-запрос для получения данных о звонках
        String query = "SELECT a1.ФИО AS ФИО_исходящего, " +
                "a2.ФИО AS ФИО_входящего, " +
                "Звонки.Исходящий_звонок_номер, " +
                "Звонки.Входящий_звонок_номер " +
                "FROM Звонки " +
                "JOIN Абонент a1 ON Звонки.Абонентid = a1.Абонентid " +
                "JOIN Абонент a2 ON Звонки.телефонid = a2.Абонентid";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Call call = new Call(
                        rs.getString("ФИО_исходящего"),
                        rs.getString("ФИО_входящего"),
                        rs.getString("Исходящий_звонок_номер"),
                        rs.getString("Входящий_звонок_номер")
                );

                callList.add(call);  // Добавляем звонок в список
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Скрываем таблицу долгов и отображаем таблицу звонков
        debtTable.setVisible(false);
        callTable.setVisible(true);
        tariffTable.setVisible(false);
        callTable.setItems(callList);  // Устанавливаем данные о звонках в таблицу
    }

    // Метод для отображения номеров клиентов
    @FXML
    private void handleClientNumbersAction() {
        ObservableList<ClientNumber> clientNumbersList = FXCollections.observableArrayList();

        // SQL-запрос для получения данных о номерах клиентов
        String query = "SELECT Абонент.ФИО, Абонент.Адрес, Телефон.Номер, Телефон.Модель " +
                "FROM Абонент " +
                "JOIN Телефон ON Абонент.Абонентid = Телефон.Абонентid";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ClientNumber clientNumber = new ClientNumber(
                        rs.getString("ФИО"),
                        rs.getString("Номер"),
                        rs.getString("Адрес"),
                        rs.getString("Модель")
                );

                clientNumbersList.add(clientNumber);  // Добавляем данные в список
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        clientNumbersTable.setItems(clientNumbersList);
        clientNumbersTable.setVisible(true);

        // Скрываем другие таблицы
        debtTable.setVisible(false);
        callTable.setVisible(false);
        tariffTable.setVisible(false);
    }

    // Метод для отображения тарифов
    @FXML
    private void handleTariffsAction() {
        ObservableList<Tariff> tariffList = FXCollections.observableArrayList();

        // Очищаем таблицу перед загрузкой новых данных
        tariffTable.getItems().clear();

        // SQL-запрос для получения данных о тарифах
        String query = "SELECT Абонент.ФИО, Телефон.Номер, Тариф.Тариф, Тариф.Цена " +
                "FROM Абонент " +
                "JOIN Телефон ON Абонент.Абонентid = Телефон.Абонентid " +
                "JOIN Тариф ON Абонент.Абонентid = Тариф.Абонентid";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Tariff tariff = new Tariff(
                        rs.getString("ФИО"),
                        rs.getString("Номер"),
                        rs.getString("Тариф"),
                        rs.getDouble("Цена")
                );

                tariffList.add(tariff);  // Добавляем данные в список
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Заполняем таблицу и отображаем её
        tariffTable.setItems(tariffList);
        tariffTable.setVisible(true);

        // Скрываем другие таблицы
        debtTable.setVisible(false);
        callTable.setVisible(false);
        clientNumbersTable.setVisible(false);
    }

    // Метод для добавления данных
    @FXML
    private void handleAddButtonAction() {
        Stage addStage = new Stage();
        VBox addOptions = new VBox(10);
        Button addUserButton = new Button("Добавить пользователя");

        addUserButton.setOnAction(e -> openUserAddForm());

        addOptions.getChildren().addAll(addUserButton);
        Scene addScene = new Scene(addOptions, 300, 200);
        addStage.setScene(addScene);
        addStage.setTitle("Добавить данные");
        addStage.show();
    }

    private void openUserAddForm() {
        Stage userStage = new Stage();
        GridPane userForm = new GridPane();

        // Поля для ввода данных пользователя
        TextField fioField = new TextField();
        TextField addressField = new TextField();
        TextField outgoingCallField = new TextField();
        TextField incomingCallField = new TextField();
        TextField outgoingNumberField = new TextField();
        TextField incomingNumberField = new TextField();
        TextField balanceField = new TextField();
        TextField statusField = new TextField();
        TextField tariffField = new TextField();
        TextField priceField = new TextField();
        TextField phoneNumberField = new TextField();
        TextField modelField = new TextField();

        // Добавляем элементы на форму
        userForm.add(new Text("ФИО:"), 0, 0);
        userForm.add(fioField, 1, 0);
        userForm.add(new Text("Адрес:"), 0, 1);
        userForm.add(addressField, 1, 1);
        userForm.add(new Text("Исходящий звонок:"), 0, 2);
        userForm.add(outgoingCallField, 1, 2);
        userForm.add(new Text("Входящий звонок:"), 0, 3);
        userForm.add(incomingCallField, 1, 3);
        userForm.add(new Text("Исходящий номер:"), 0, 4);
        userForm.add(outgoingNumberField, 1, 4);
        userForm.add(new Text("Входящий номер:"), 0, 5);
        userForm.add(incomingNumberField, 1, 5);
        userForm.add(new Text("Баланс:"), 0, 6);
        userForm.add(balanceField, 1, 6);
        userForm.add(new Text("Статус:"), 0, 7);
        userForm.add(statusField, 1, 7);
        userForm.add(new Text("Тариф:"), 0, 8);
        userForm.add(tariffField, 1, 8);
        userForm.add(new Text("Цена:"), 0, 9);
        userForm.add(priceField, 1, 9);
        userForm.add(new Text("Номер телефона:"), 0, 10);
        userForm.add(phoneNumberField, 1, 10);
        userForm.add(new Text("Модель:"), 0, 11);
        userForm.add(modelField, 1, 11);

        Button saveButton = new Button("Сохранить");
        saveButton.setOnAction(e -> {
            if (saveUserData(fioField.getText(), addressField.getText(), outgoingCallField.getText(),
                    incomingCallField.getText(), outgoingNumberField.getText(), incomingNumberField.getText(),
                    Double.parseDouble(balanceField.getText()), statusField.getText(),
                    tariffField.getText(), Double.parseDouble(priceField.getText()),
                    phoneNumberField.getText(), modelField.getText())) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Успех");
                alert.setContentText("Данные успешно добавлены!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setContentText("Не удалось добавить данные.");
                alert.showAndWait();
            }
            userStage.close();
        });

        userForm.add(saveButton, 1, 12);
        Scene userScene = new Scene(userForm, 600, 400);
        userStage.setScene(userScene);
        userStage.setTitle("Добавить пользователя");
        userStage.show();
    }

    private boolean saveUserData(String fio, String address, String outgoingCall, String incomingCall,
                                 String outgoingNumber, String incomingNumber, double balance, String status,
                                 String tariff, double price, String phoneNumber, String model) {
        String insertUserQuery = "INSERT INTO Абонент (ФИО, Адрес) VALUES (?, ?)";
        String insertCallQuery = "INSERT INTO Звонки (Абонентid, Исходящий_звонок, Входящий_звонок, Исходящий_звонок_номер, Входящий_звонок_номер) VALUES (?, ?, ?, ?, ?)";
        String insertClientStatusQuery = "INSERT INTO Статус_Клиента (Абонентid, Баланс, Заблокирован_Разблокирован) VALUES (?, ?, ?)";
        String insertTariffQuery = "INSERT INTO Тариф (Абонентid, Тариф, Цена) VALUES (?, ?, ?)";
        String insertPhoneQuery = "INSERT INTO Телефон (Абонентid, Номер, Модель) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect()) {
            // Сохраняем пользователя
            try (PreparedStatement userStmt = conn.prepareStatement(insertUserQuery)) {
                userStmt.setString(1, fio);
                userStmt.setString(2, address);
                userStmt.executeUpdate();
            }

            // Получаем ID последнего добавленного абонента
            int subscriberId = getLastInsertedId(conn, "Абонент");

            // Сохраняем данные о звонках
            try (PreparedStatement callStmt = conn.prepareStatement(insertCallQuery)) {
                callStmt.setInt(1, subscriberId);
                callStmt.setString(2, outgoingCall);
                callStmt.setString(3, incomingCall);
                callStmt.setString(4, outgoingNumber);
                callStmt.setString(5, incomingNumber);
                callStmt.executeUpdate();
            }

            // Сохраняем статус клиента
            try (PreparedStatement statusStmt = conn.prepareStatement(insertClientStatusQuery)) {
                statusStmt.setInt(1, subscriberId);
                statusStmt.setDouble(2, balance);
                statusStmt.setBoolean(3, status.equalsIgnoreCase("true")); // Преобразуем статус в boolean
                statusStmt.executeUpdate();
            }

            // Сохраняем тариф
            try (PreparedStatement tariffStmt = conn.prepareStatement(insertTariffQuery)) {
                tariffStmt.setInt(1, subscriberId);
                tariffStmt.setString(2, tariff);
                tariffStmt.setDouble(3, price);
                tariffStmt.executeUpdate();
            }

            // Сохраняем телефон
            try (PreparedStatement phoneStmt = conn.prepareStatement(insertPhoneQuery)) {
                phoneStmt.setInt(1, subscriberId);
                phoneStmt.setString(2, phoneNumber);
                phoneStmt.setString(3, model);
                phoneStmt.executeUpdate();
            }

            return true; // Все данные успешно сохранены
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Ошибка
        }
    }

    private int getLastInsertedId(Connection conn, String tableName) throws SQLException {
        String query = "SELECT MAX(Абонентid) AS LastId FROM " + tableName;
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("LastId");
            }
        }
        return -1; // Если не найден ID
    }

}

