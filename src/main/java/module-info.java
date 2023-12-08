module com.example.box_chat {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.box_chat to javafx.fxml;
    exports com.example.box_chat;
}