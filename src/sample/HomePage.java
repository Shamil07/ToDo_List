package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.util.Optional;

public class HomePage extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("HomePage");
        database.connection();

        // Labels
        Label l_problems = new Label("\uD835\uDDE7\uD835\uDDEE\uD835\uDE00\uD835\uDDF8:");
        l_problems.setStyle("-fx-text-fill: white");
        TextField t_problems = new TextField();
        t_problems.setPrefColumnCount(4);
        Label l_categories = new Label("\uD835\uDDD6\uD835\uDDEE\uD835\uDE01\uD835\uDDF2\uD835\uDDF4\uD835\uDDFC\uD835\uDDFF\uD835\uDDF6\uD835\uDDF2\uD835\uDE00:");
        l_categories.setStyle("-fx-text-fill: white");

        // ComboBox Categories
        ComboBox com_categories = new ComboBox();
        com_categories.setPrefSize(160, 0);
        
        // Labels and TextFields
        Label l_day = new Label("\uD835\uDDD7\uD835\uDDEE\uD835\uDE06:");
        l_day.setStyle("-fx-text-fill: white");
        TextField t_day = new TextField();
        Label l_calendar = new Label("\uD835\uDDD6\uD835\uDDEE\uD835\uDDF9\uD835\uDDF2\uD835\uDDFB\uD835\uDDF1\uD835\uDDEE\uD835\uDDFF:");
        l_calendar.setStyle("-fx-text-fill: white");
        Label l_delete = new Label("Task ID:");
        l_delete.setStyle("-fx-text-fill: white");
        TextField t_delete = new TextField();
        t_delete.setPrefColumnCount(2);

        // Table with user info
        TableView<UserData> tableView = new TableView<>();
        TableColumn id_column = new TableColumn("ID");
        id_column.setCellValueFactory(new PropertyValueFactory<>("task_id"));
        TableColumn task_column = new TableColumn("Task");
        task_column.setCellValueFactory(new PropertyValueFactory<>("task"));
        TableColumn day_column = new TableColumn("Day");
        day_column.setCellValueFactory(new PropertyValueFactory<>("day"));
        TableColumn date_column = new TableColumn("Date");
        date_column.setCellValueFactory(new PropertyValueFactory<>("date"));
        TableColumn categories_column = new TableColumn("Categories");
        categories_column.setCellValueFactory(new PropertyValueFactory<>("categories"));
        TableColumn status_column = new TableColumn("Status");
        status_column.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Table parameters
        tableView.setPrefWidth(450);
        tableView.setPrefHeight(500);
        tableView.setItems(database.complete_table(""));
        tableView.getColumns().addAll(id_column, task_column, day_column, date_column, categories_column, status_column);

        // Number of rows
        Label tableRows1 = new Label("\uD835\uDDE5\uD835\uDDE2\uD835\uDDEA\uD835\uDDE6:");
        tableRows1.setStyle("-fx-text-fill: white");
        Label tableRows2 = new Label();
        tableRows2.setStyle("-fx-text-fill: white");
        tableRows2.setText(tableView.getItems().size() + "");

        // Task status "Done" or "Not done"
        Button task_status = new Button("Done");
        String txtfornot = "Done";
        String txtfornot1 = "Not done";
        task_status.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println(tableView.getSelectionModel().getSelectedItem().getStatus());
                if (tableView.getSelectionModel().getSelectedItem().getStatus().equals("Not done")) {
                    task_status.setText("Done");
                    task_status.setStyle("-fx-background-color: #7bde76");
                    database.uptadeDone(txtfornot, tableView.getSelectionModel().getSelectedItem().getTask_id());
                    tableView.setItems(database.complete_table(""));

                } else {
                    task_status.setText("Not done");
                    task_status.setStyle("-fx-background-color: #d66c60");
                    database.updateNotDone(txtfornot1, tableView.getSelectionModel().getSelectedItem().getTask_id());
                    tableView.setItems(database.complete_table(""));
                }

            }
        });

        // Calendar (Date for tasks)
        DatePicker calendar = new DatePicker();
        Label l_search = new Label("\uD835\uDDE6\uD835\uDDF2\uD835\uDDEE\uD835\uDDFF\uD835\uDDF0\uD835\uDDF5:");
        calendar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                database.connection();
            }
        });

        // Label and TextField for Search
        l_search.setStyle("-fx-text-fill: white");
        TextField t_search = new TextField();
        Label statusfilter = new Label("\uD835\uDDE6\uD835\uDE01\uD835\uDDEE\uD835\uDE01\uD835\uDE02\uD835\uDE00 \uD835\uDDD9\uD835\uDDF6\uD835\uDDF9\uD835\uDE01\uD835\uDDF2\uD835\uDDFF:");
        statusfilter.setStyle("-fx-text-fill: white");

        t_search.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                try {
                    tableView.setItems(database.search_list("SELECT * FROM homepage_data WHERE userid=" + HomePage.id_p +
                            " and task like '" + newValue + "%'"));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        // RadioButtons (Filters)
        ToggleGroup radiobuttons = new ToggleGroup();
        RadioButton all = new RadioButton("\uD835\uDDD4\uD835\uDDF9\uD835\uDDF9");
        all.setStyle("-fx-text-fill: #6495ED");
        RadioButton resolved = new RadioButton("\uD835\uDDE5\uD835\uDDF2\uD835\uDE00\uD835\uDDFC\uD835\uDDF9\uD835\uDE03\uD835\uDDF2\uD835\uDDF1");
        resolved.setStyle("-fx-text-fill: #6495ED");
        RadioButton unresolved = new RadioButton("\uD835\uDDE8\uD835\uDDFB\uD835\uDDFF\uD835\uDDF2\uD835\uDE00\uD835\uDDFC\uD835\uDDF9\uD835\uDE03\uD835\uDDF2\uD835\uDDF1");
        unresolved.setStyle("-fx-text-fill: #6495ED");
        all.setToggleGroup(radiobuttons);
        resolved.setToggleGroup(radiobuttons);
        unresolved.setToggleGroup(radiobuttons);

        radiobuttons.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (all.isSelected()) {
                    System.out.println("All");
                } else if (resolved.isSelected()) {
                    System.out.println("Resolved");
                } else if (unresolved.isSelected()) {
                    System.out.println("Unresolved");
                }
            }
        });

        // All tasks
        all.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    tableView.setItems(database.filter_all());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        // Resolved tasks
        resolved.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    tableView.setItems(database.filter_resolved("Done"));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        // Unresolved tasks
        unresolved.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    tableView.setItems(database.filter_unresolved("Not done"));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        // Add categories
        Alert alert3 = new Alert(Alert.AlertType.NONE);
        Button plus = new Button("+");
        plus.setStyle("-fx-background-color: #ADFF2F");
        plus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                database.connection();
                TextInputDialog dialog_pm = new TextInputDialog();
                dialog_pm.setTitle("Add Categories");
                dialog_pm.setHeaderText(null);
                dialog_pm.setContentText("Categories:");
                Optional<String> result = dialog_pm.showAndWait();
                if (result.isPresent()) {
                    com_categories.getItems().add(result.get());
                    alert3.setAlertType(Alert.AlertType.CONFIRMATION);
                    alert3.setTitle("Notification");
                    alert3.setHeaderText(null);
                    alert3.setContentText("Category added!");
                    alert3.show();
                }
            }
        });

        // Remove categories
        Button minus = new Button("-");
        minus.setStyle("-fx-background-color: red");
        minus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                database.connection();
                com_categories.getItems().remove(com_categories.getValue());
                alert3.setAlertType(Alert.AlertType.CONFIRMATION);
                alert3.setTitle("Notification");
                alert3.setHeaderText(null);
                alert3.setContentText("Category removed!");
                alert3.show();
            }
        });

        // Create new task
        Alert alert2 = new Alert(Alert.AlertType.NONE);
        Button new_task = new Button("\uD835\uDC0D\uD835\uDC1E\uD835\uDC30 \uD835\uDC2D\uD835\uDC1A\uD835\uDC2C\uD835\uDC24");
        new_task.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                database.connection();
                database.create_task(t_problems.getText(), t_day.getText(), String.valueOf(calendar.getValue()),
                        String.valueOf(com_categories.getValue()), txtfornot1, id_p);
                tableRows2.setText(tableView.getItems().size() + "");
                alert2.setAlertType(Alert.AlertType.CONFIRMATION);
                alert2.setTitle("Notification");
                alert2.setHeaderText(null);
                alert2.setContentText("Your task has been created!");
                alert2.show();
            }
        });

        // Save changes and Refresh table
        Alert alert1 = new Alert(Alert.AlertType.NONE);
        Button save_changes = new Button("\uD835\uDC12\uD835\uDC1A\uD835\uDC2F\uD835\uDC1E \uD835\uDC02\uD835\uDC21\uD835\uDC1A\uD835\uDC27\uD835\uDC20\uD835\uDC1E\uD835\uDC2C");
        save_changes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                database.connection();
                tableView.setItems(database.complete_table(""));
                tableRows2.setText(tableView.getItems().size() + "");
                alert1.setAlertType(Alert.AlertType.INFORMATION);
                alert1.setTitle("Notification");
                alert1.setHeaderText(null);
                alert1.setContentText("Changes hac been save!");
                alert1.show();
            }
        });

        // CheckBox for delete all tasks
        CheckBox all_tasks = new CheckBox("All tasks");
        all_tasks.setStyle("-fx-text-fill: white");

        // Button for Delete tasks
        Alert alert = new Alert(Alert.AlertType.NONE);
        Button delete_task = new Button("\uD835\uDC03\uD835\uDC1E\uD835\uDC25\uD835\uDC1E\uD835\uDC2D\uD835\uDC1E");
        delete_task.setStyle("-fx-background-color: red");
        delete_task.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                database.connection();
                if (all_tasks.isSelected()) {
                    database.delete_task();
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setTitle("DATA DELETED");
                    alert.setHeaderText(null);
                    alert.setContentText("Your task has been deleted!");
                    alert.show();
                } else {
                    database.delete_task(Integer.parseInt(t_delete.getText()));
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setTitle("DATA DELETED");
                    alert.setHeaderText(null);
                    alert.setContentText("Your task has been deleted!");
                    alert.show();
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
        Button styleDay = new Button("Light");
        styleDay.setStyle("-fx-background-color: #B0C4DE");         // Light
        styleDay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gridPane.setStyle("-fx-background-color: #B0C4DE");
            }
        });

        Button styleNight = new Button("Dark");                 // Dark
        styleNight.setStyle("-fx-background-color: #969696");
        styleNight.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gridPane.setStyle("-fx-background-color: #454545");
            }
        });


        // ADD ELEMENTS (GridPane)
        gridPane.add(styleDay, 8, 4);           // Theme Light
        gridPane.add(styleNight, 9, 4);         // Theme Dark
        gridPane.add(l_problems, 0, 0);         // Label Tasks
        gridPane.add(t_problems, 1, 0,3,1);         // TextField Tasks
        gridPane.add(l_categories, 0, 1);       // Label Categories
        gridPane.add(com_categories, 1, 1,3,1);     // TextField Categories
        gridPane.add(l_day, 4, 0);              // Label Day (Time for complete task)
        gridPane.add(t_day, 5, 0);              // TextField Day (Time for complete task)
        gridPane.add(l_calendar, 6, 0);         // Label Calendar
        gridPane.add(calendar, 7, 0, 3, 1);           // ComboBox calendar
        gridPane.add(plus, 4, 1);               // Button add categories
        gridPane.add(minus, 5, 1);              // Button remove categories
        gridPane.add(l_search, 6, 1);           // Label search
        gridPane.add(t_search, 7, 1, 3, 1);           // TextField search
        gridPane.add(statusfilter, 0, 2);       // Label status
        gridPane.add(all, 1, 2);                // RadioButton All
        gridPane.add(resolved, 2, 2);           // RadioButton Resolved
        gridPane.add(unresolved, 3, 2,2,1);         // RadioButton Unresolved
        gridPane.add(task_status, 7, 4);        // Button Task status (Done, Not done)
        gridPane.add(new_task, 0, 4);           // Button new task
        gridPane.add(save_changes, 1, 4,2,1);       // Button save changes
        gridPane.add(tableView, 0, 5,10,1);          // Table with user tasks and so on
        gridPane.add(delete_task, 10, 3);        // Button delete task
        gridPane.add(l_delete, 6, 3);           // Label delete
        gridPane.add(t_delete, 7, 3, 3, 1);           // TextField delete
        gridPane.add(all_tasks, 7, 2);          // CheckBox all tasks
        gridPane.add(tableRows1, 0, 6);         // Number of rows
        gridPane.add(tableRows2, 1, 6);         // Number of rows


        //#################################################################### GridPane parameters ---
        gridPane.setStyle("-fx-background-color: #b3b3b3");
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        Scene scene = new Scene(gridPane, 200, 300);
        stage.setScene(scene);
        stage.show();
        //####################################################################
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    MySQLFX2 database = new MySQLFX2();

    // Create window
    Stage stage = new Stage();
    static int id_p = 0;

    public void show_window(int user_id) {
        try {
            id_p = user_id;
            System.out.println(id_p);
            start(stage);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void stop() {
        database.closeconnection();
        System.out.println("В теле метода stop()");
    }
}

class MySQLFX2 {
    Connection con = null;
    ResultSet rset = null;

    // Open connection
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

    // Method fill table with user data and create SQL Command for "Search"
    ObservableList<UserData> complete_table(String word) {
        final ObservableList<UserData> data = FXCollections.observableArrayList();

        try {
            Statement stmt = con.createStatement();
            String zapros = "SELECT task_id,task,day,date,categories,status FROM problembook.homepage_data WHERE userid=" + HomePage.id_p +
                    " and task like '" + word + "%'";
            ResultSet rset = stmt.executeQuery(zapros);
            while (rset.next()) {
                data.add(new UserData(rset.getString("task_id"), rset.getString("task"), rset.getString("day"),
                        rset.getString("date"), rset.getString("categories"), rset.getString("status")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return data;
    }

    // int method for create new Task
    int create_task(String task, String day, String date, String categories, String status, int userid) {
        try {
            String sqlcommand = "INSERT INTO homepage_data(task,day, date,  categories, status, userid) values(?,?,?,?,?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(sqlcommand, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, String.valueOf(task));
            preparedStatement.setString(2, String.valueOf(day));
            preparedStatement.setString(3, String.valueOf(date));
            preparedStatement.setString(4, categories);
            preparedStatement.setString(5, status);
            preparedStatement.setString(6, String.valueOf(userid));

            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected == 1) {
                int user_id = 0;
                rset = preparedStatement.getGeneratedKeys();
                if (rset.next()) {
                    user_id = rset.getInt(1);
                    System.out.println(user_id);
                    return user_id;
                }
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return 0;
    }

    // void method for delete task with task ID
    void delete_task(int task_id) {
        String sqlcommand = "DELETE FROM homepage_data WHERE task_id=?";
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sqlcommand, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, task_id);
            stmt.executeUpdate();//1
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // void method for delete all tasks (CheckBox select all tasks)
    void delete_task() {
        String sqlcommand = "DELETE FROM homepage_data WHERE userid= " + HomePage.id_p;
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement(sqlcommand, Statement.RETURN_GENERATED_KEYS);
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Update task status "Done"
    void uptadeDone(String done, String id) {
        String url = "jdbc:mysql://localhost:3306/problembook?serverTimezone=UTC";
        ResultSet rset;
        Connection con = null;
        String user = "root";
        String password = "123456";
        try {
            con = DriverManager.getConnection(url, user, password);
            String sqlcommand = "UPDATE problembook.homepage_data SET status=? WHERE task_id=?";
            PreparedStatement stmt = con.prepareStatement(sqlcommand, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, done);
            stmt.setString(2, id);
            int rowsReturned = stmt.executeUpdate();//1
            System.out.println(String.format(("Rows affected %d"), rowsReturned));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Update task status "Not done"
    void updateNotDone(String notdone, String id) {
        Connection con = null;
        ResultSet rset = null;
        int id_user = 0;

        String url = "jdbc:mysql://localhost:3306/problembook?serverTimezone=UTC";
        String username = "root";
        String password = "123456";

        try {
            con = DriverManager.getConnection(url, username, password);
            String sqlcommand = "UPDATE problembook.homepage_data SET status=? where task_id=?";
            PreparedStatement stmt = con.prepareStatement(sqlcommand, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, notdone);
            stmt.setString(2, id);
            int rowsReturned = stmt.executeUpdate();//1
            System.out.println(String.format(("Rows affected %d"), rowsReturned));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Filter all (method for RadioButtons)
    ObservableList<UserData> filter_all() throws SQLException {
        final ObservableList<UserData> data = FXCollections.observableArrayList();
        Statement statement = con.createStatement();
        String sqlcommand = "SELECT * FROM homepage_data WHERE userid=" + HomePage.id_p;
        rset = statement.executeQuery(sqlcommand);
        while (rset.next()) {
            data.add(new UserData(rset.getString("task_id"), rset.getString("task"), rset.getString("day"),
                    rset.getString("date"), rset.getString("categories"), rset.getString("status")));
        }
        return data;
    }

    // Filter resolved (method for RadioButtons)
    ObservableList<UserData> filter_resolved(String resolved) throws SQLException {
        final ObservableList<UserData> data = FXCollections.observableArrayList();
        Statement statement = con.createStatement();
        String sqlcommand = "SELECT * FROM problembook.homepage_data WHERE userid=" + HomePage.id_p + " and status='" + resolved + "'";
        rset = statement.executeQuery(sqlcommand);
        while (rset.next()) {
            data.add(new UserData(rset.getString("task_id"), rset.getString("task"), rset.getString("day"),
                    rset.getString("date"), rset.getString("categories"), rset.getString("status")));
        }
        return data;
    }

    // Filter unresolved (method for RadioButtons)
    ObservableList<UserData> filter_unresolved(String unresolved) throws SQLException {
        final ObservableList<UserData> data = FXCollections.observableArrayList();
        Statement statement = con.createStatement();
        String sqlcommand = "SELECT * FROM problembook.homepage_data WHERE userid=" + HomePage.id_p + " and status='" + unresolved + "'";
        rset = statement.executeQuery(sqlcommand);
        while (rset.next()) {
            data.add(new UserData(rset.getString("task_id"), rset.getString("task"), rset.getString("day"),
                    rset.getString("date"), rset.getString("categories"), rset.getString("status")));
        }
        return data;
    }

    // Close connection

    ObservableList<UserData> search_list(String sqlcommand) throws SQLException {
        final ObservableList<UserData> data = FXCollections.observableArrayList();
        Statement statement = con.createStatement();
        String sqlcommand1 = sqlcommand;
        rset = statement.executeQuery(sqlcommand);
        while (rset.next()) {
            data.add(new UserData(rset.getString("task_id"), rset.getString("task"), rset.getString("day"),
                    rset.getString("date"), rset.getString("categories"), rset.getString("status")));
        }
        return data;
    }
    void closeconnection() {
        try {
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}