package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.*;

public class Main extends Application {
    public static void main(String[] args) {
        System.out.println("Запуск приложения JavaFX");
        launch(args);
    }

    public void init() {//initialize
        System.out.println("В теле метода init()");
    }

    MySQLFX database = new MySQLFX();

    public void start(Stage myStage) {
        System.out.println("В теле метода start()");
        myStage.setTitle("\uD835\uDC0B\uD835\uDC28\uD835\uDC20\uD835\uDC22\uD835\uDC27 \uD835\uDC2D\uD835\uDC28 \uD835\uDC13\uD835\uDC1A\uD835\uDC2C\uD835\uDC24\uD835\uDC01\uD835\uDC28\uD835\uDC28\uD835\uDC24");
        database.connection();

        // Labels
        Label l_username = new Label("\uD835\uDDE8\uD835\uDE00\uD835\uDDF2\uD835\uDDFF\uD835\uDDFB\uD835\uDDEE\uD835\uDDFA\uD835\uDDF2: ");
        l_username.setStyle("-fx-text-fill: white");
        TextField t_username = new TextField();
        Label l_password = new Label("\uD835\uDDE3\uD835\uDDEE\uD835\uDE00\uD835\uDE00\uD835\uDE04\uD835\uDDFC\uD835\uDDFF\uD835\uDDF1: ");
        l_password.setStyle("-fx-text-fill: white");
        PasswordField t_password = new PasswordField();
        Label l_delete = new Label("\uD835\uDDD8\uD835\uDDFB\uD835\uDE01\uD835\uDDF2\uD835\uDDFF \uD835\uDDDC\uD835\uDDD7: ");
        l_delete.setStyle("-fx-text-fill: white");
        TextField t_delete = new TextField();


        // Login and Show new window
        Alert alert = new Alert(Alert.AlertType.NONE);
        Button log_in = new Button("Log In");
        log_in.setStyle("-fx-background-color: #ADFF2F");
        log_in.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                database.connection();
                if (t_username.getText().isEmpty()) {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText(null);
                    alert.setContentText("You didn't enter your username\nPlease enter username");
                    alert.show();
                    return;
                } else if (t_password.getText().isEmpty()) {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText(null);
                    alert.setContentText("You didn't enter your password\nPlease enter password");
                    alert.show();
                    return;
                } else {
                    String sqlcommand = "SELECT * FROM problembook.prlbook_info WHERE username='" +
                            t_username.getText() + "' and password='" + t_password.getText() + "'";
                    database.show_homepage_window(sqlcommand);
                }
            }
        });

        // Create new user
        Button create = new Button("Create");
        create.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                database.connection();
                if (t_username.getText().isEmpty()) {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText(null);
                    alert.setContentText("You didn't enter your username\nPlease enter username");
                    alert.show();
                    return;
                } else if (t_password.getText().isEmpty()) {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText(null);
                    alert.setContentText("You didn't enter your password\nPlease enter password");
                    alert.show();
                    return;
                } else {
                    database.create_button(t_username.getText(), t_password.getText());
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setTitle("Notification");
                    alert.setHeaderText(null);
                    alert.setContentText("Account successful created! Your ID: " + database.getUserbyId(t_username.getText(), t_password.getText()));
                    alert.show();
                }
            }
        });

        // Delete event
        EventHandler<ActionEvent> delete_event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                database.delete(Integer.parseInt(t_delete.getText()));
                database.delete_homepage_data(Integer.parseInt(t_delete.getText()));
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setTitle("DATA DELETED");
                alert.setHeaderText(null);
                alert.setContentText("Data deleted!");
                alert.show();
            }
        };

        // User and UserData delete
        Button delete = new Button("Delete");
        delete.setStyle("-fx-background-color: red");
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (t_delete.getText().isEmpty()) {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText(null);
                    alert.setContentText("You didn't enter user ID\nPlease enter username");
                    alert.show();
                    return;
                } else {
                    delete.setOnAction(delete_event);
                }
            }
        });

        //####################################################################
        GridPane gridPane = new GridPane();
        gridPane.setMinSize(900, 900);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.TOP_CENTER);
        //####################################################################

        // Themes
        Button styleDay = new Button("Light");          // Light
        styleDay.setStyle("-fx-background-color: #B0C4DE");
        styleDay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gridPane.setStyle("-fx-background-color: #B0C4DE");
            }
        });

        Button styleNight = new Button("Dark");         // Dark
        styleNight.setStyle("-fx-background-color: #969696");
        styleNight.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gridPane.setStyle("-fx-background-color: #454545");
            }
        });

        // ADD ELEMENTS (GridPane)
        gridPane.add(styleDay, 2, 4);
        gridPane.add(styleNight, 2, 5);
        gridPane.add(l_username, 0, 1);
        gridPane.add(t_username, 1, 1);
        gridPane.add(l_password, 0, 2);
        gridPane.add(t_password, 1, 2);
        gridPane.add(delete, 2, 3);
        gridPane.add(create, 2, 1);
        gridPane.add(log_in, 2, 2);
        gridPane.add(l_delete, 0, 3);
        gridPane.add(t_delete, 1, 3);

        gridPane.setStyle("-fx-background-color: #b3b3b3");
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        Scene myScene = new Scene(gridPane, 400, 400);
        myStage.setScene(myScene);
        myStage.show();
    }

    public void stop() {
        database.closeconnection();
        System.out.println("В теле метода stop()");
    }
}

class MySQLFX {
    Connection con = null;
    ResultSet rset = null;
    int id_user = 0;

    void connection() {
        String url = "jdbc:mysql://localhost:3306/problembook?serverTimezone=UTC";
        String username = "root";
        String password = "123456";
        try {
            con = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Delete user
    void delete(int i) {
        String sqlcommand = "DELETE FROM prlbook_info WHERE id = ?";
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sqlcommand, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, String.valueOf(i));
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Delete user data
    void delete_homepage_data(int i) {
        String sqlcommand = "DELETE FROM homepage_data WHERE userid = ?";
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sqlcommand, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, String.valueOf(i));
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Take user id
    int getUserbyId(String username, String password) {
        try {
            String sqlcommand = "SELECT * FROM prlbook_info WHERE username ='" + username + "' and password= '" + password + "'";
            Statement statement = con.createStatement();
            rset = statement.executeQuery(sqlcommand);
            if (rset.next()) {
                int user_id = rset.getInt(1);
                return user_id;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    // Create user
    int create_button(String username, String password) {
        try {
            String sqlcommand = "INSERT INTO prlbook_info(username,password) values(?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(sqlcommand, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected == 1) {
                int user_id = 0;
                rset = preparedStatement.getGeneratedKeys();
                if (rset.next()) {
                    user_id = rset.getInt(1);
                    System.out.println(user_id);
                    id_user = user_id;
                    return user_id;
                }
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());

        }
        return 0;
    }

    // Alert (message box)
    Alert alert = new Alert(Alert.AlertType.NONE);

    // Homepage
    void show_homepage_window(String sqlcommand) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rset = stmt.executeQuery(sqlcommand);
            boolean if_user = false;
            int user_id = 0;
            while (rset.next()) {
                if_user = true;
                user_id = rset.getInt(1);
                HomePage homePage = new HomePage();
                homePage.stage.show();
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setTitle("Notification");
                alert.setHeaderText(null);
                alert.setContentText("Successful login!");
                alert.show();
                homePage.stage.setTitle("HomePage");
                homePage.stage.setHeight(550);
                homePage.stage.setWidth(800);
                homePage.show_window(user_id);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Close connection
    void closeconnection() {
        try {
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}