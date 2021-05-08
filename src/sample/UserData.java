package sample;

import javafx.beans.property.SimpleStringProperty;

public class UserData {
     SimpleStringProperty task_id;
     SimpleStringProperty task;
     SimpleStringProperty day;
     SimpleStringProperty date;
     SimpleStringProperty categories;
     SimpleStringProperty status;

    UserData(String task_id, String task, String day, String date, String categories, String status) {
        this.task_id = new SimpleStringProperty(task_id);
        this.task = new SimpleStringProperty(task);
        this.day = new SimpleStringProperty(day);
        this.date = new SimpleStringProperty(date);
        this.categories = new SimpleStringProperty(categories);
        this.status = new SimpleStringProperty(status);
    }
    public String getTask_id() {
        return task_id.get();
    }

    public void setTask_id(String id_p) {
        task_id.set(id_p);
    }

    public String getTask() {
        return task.get();
    }

    public void setTask(String name) {
        task.set(name);
    }

    public String getDay() {
        return day.get();
    }

    public void setDay(String surn) {
        day.set(surn);
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String username) {
        date.set(username);
    }

    public String getCategories() {
        return categories.get();
    }

    public void setCategories(String rdrdr) {
        categories.set(rdrdr);
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String parameter) {
        status.set(parameter);
    }
}