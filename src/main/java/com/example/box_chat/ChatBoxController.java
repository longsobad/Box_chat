package com.example.box_chat;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import java.util.Objects;
import java.util.Random;
public class ChatBoxController {
    @FXML
    public VBox layout = new VBox();
    public Label outside = new Label();
    public VBox chatPane = new VBox();
    public TextField messageField = new TextField();
    public Button sendButton = new Button();
    public ScrollPane scrollPane = new ScrollPane();
    private final double imageHeight = 30;
    private final double imageWidth = 35;

    private final String yourAvatarPath = "/img/profile.png";
    private final String friendAvatarPath = "/img/young_man.png";
    private final String outgoingBubbleColor = "f9f9f9";
    private final String incomingBubbleColor = "9da7a7";
    private final String fontName = "Ebrima";
    private final int fontSize = 12;
    private final double defaultBubbleWidth = 200;
    private final double maxBubbleWidth = 394;
    private final double defaultbubbleHeight = 50;
    public void sendAction(MouseEvent event) {
        ImageView you = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/profile.png"))));
        ImageView friend = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/young_man.png"))));
        you.setFitHeight(imageHeight);
        you.setFitWidth(imageWidth);
        friend.setFitHeight(imageHeight);
        friend.setFitWidth(imageWidth);
        sendMessage(messageField, you, friend);
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.75));
        pauseTransition.setOnFinished(Event -> {
            scrollPane.setVvalue(1.0);
        });
        pauseTransition.play();
    }

    public void sendActionKey(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            ImageView you = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(yourAvatarPath))));
            ImageView friend = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(friendAvatarPath))));
            you.setFitHeight(imageHeight);
            you.setFitWidth(imageWidth);
            friend.setFitHeight(imageHeight);
            friend.setFitWidth(imageWidth);
            sendMessage(messageField, you, friend);
            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.75));
            pauseTransition.setOnFinished(Event -> {
                scrollPane.setVvalue(1.0);
            });
            pauseTransition.play();
        }
    }

    private String handleChat(String message) {
        message = message.toLowerCase();
        Random random = new Random();

        String[] defaultResponses = {
                "Haha, what are you talking about?", "You're funny!", "I have no idea what you just said."
        };
        String[] names = {"I'm Khang.", "Khang here!", "It's me, Khang."};
        String[] greetings = {"Hey!", "Hi!", "Hello!", "What's up?"};
        String[] responses = {"I'm good, thanks!", "Doing well, how about you?", "Great, and you?"};
        String[] farewells = {"See you later!", "Goodbye!", "Take care!"};
        String[] jokes = {
                "Why don't scientists trust atoms? Because they make up everything!",
                "Did you hear about the mathematician who's afraid of negative numbers? He'll stop at nothing to avoid them.",
                "I told my wife she should embrace her mistakes. She gave me a hug."
        };
        String[] musicResponses = {"I enjoy listening to rock music.", "Classical music helps me relax.", "Do you have a favorite genre?"};
        String[] foodResponses = {"I love pizza!", "Sushi is my favorite.", "I'm craving chocolate."};
        String[] movieResponses = {"I recently watched a great comedy.", "I'm into sci-fi movies.", "Any movie recommendations?"};

        if (message.contains("hello") || message.contains("hi")) {
            return greetings[random.nextInt(greetings.length)];
        } else if (message.contains("name")) {
            return names[random.nextInt(names.length)];
        } else if (message.contains("how are you")) {
            return responses[random.nextInt(responses.length)];
        } else if (message.contains("weather")) {
            return "I'm sorry, I don't have access to real-time weather information.";
        } else if (message.contains("bye")) {
            return farewells[random.nextInt(farewells.length)];
        } else if (message.contains("joke")) {
            return jokes[random.nextInt(jokes.length)];
        } else if (message.contains("help")) {
            return "You can ask about my name, how I'm doing, the weather, or just say hello!";
        } else if (message.contains("food")) {
            return foodResponses[random.nextInt(foodResponses.length)];
        } else if (message.contains("music")) {
            return musicResponses[random.nextInt(musicResponses.length)];
        } else if (message.contains("movie")) {
            return movieResponses[random.nextInt(movieResponses.length)];
        }

        return defaultResponses[random.nextInt(defaultResponses.length)];
    }



    private void sendMessage(TextField messageField, ImageView yourAvatar, ImageView friendAvatar) {
        String message = messageField.getText();
        if (!message.isEmpty()) {
            String friendChat = handleChat(message);
            Label yourLabel = createLabel("   " + message + "   ", outgoingBubbleColor);
            yourLabel.getStyleClass().add("outcoming-bubble");
            HBox yourChatHBox = createChat(yourAvatar, yourLabel);
            Label friendLabel = createLabel("   " + friendChat + "   ", incomingBubbleColor);
            friendLabel.getStyleClass().add("incoming-bubble");
            HBox friendHBox = createChatAuto(friendAvatar, friendLabel);
            layout.getChildren().addAll(yourChatHBox, friendHBox);
            chatPane.setPrefHeight(chatPane.getHeight() + yourChatHBox.getHeight() + friendHBox.getHeight());
            messageField.clear();
            chatPane.layout();
            outside.setText("you: " + message);
            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(1));
            pauseTransition.setOnFinished(event -> {
                outside.setText(friendChat);
            });
            pauseTransition.play();
        }
    }

    private Label createLabel(String text, String hexColor) {
        Label label = new Label(text);
        Color rgbColor = Color.web(hexColor);
        // Lấy giá trị RGB
        double red = rgbColor.getRed() * 255;
        double green = rgbColor.getGreen() * 255;
        double blue = rgbColor.getBlue() * 255;
        Color textColor = Color.rgb((int) red, (int) green, (int) blue);
        label.setTextFill(textColor);
        label.setFont(new Font(fontName, fontSize));
        label.setWrapText(true);
        label.setPrefHeight(Region.USE_COMPUTED_SIZE);
        label.setMaxWidth(Region.USE_COMPUTED_SIZE);
        label.setMinWidth(Region.USE_PREF_SIZE);
        label.setMinHeight(Region.USE_PREF_SIZE);
        return label;
    }



    private HBox createChat( ImageView avatar, Label chat) {
        HBox hBox = new HBox();
        hBox.getChildren().addAll(chat, avatar);
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setHgrow(chat, Priority.ALWAYS);
        hBox.setSpacing(10);
        hBox.setPrefHeight(defaultbubbleHeight);
        hBox.setPrefWidth(defaultBubbleWidth);
        hBox.setMaxWidth(maxBubbleWidth);
        hBox.setMaxHeight(defaultbubbleHeight);
        hBox.setMinWidth(defaultBubbleWidth);
        hBox.setMinHeight(defaultbubbleHeight);
        HBox.setMargin(hBox, Insets.EMPTY);
        return hBox;
    }

    private HBox createChatAuto(ImageView avatar, Label chat) {
        HBox hBox = new HBox();
        hBox.getChildren().addAll(avatar, chat);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setHgrow(chat, Priority.ALWAYS);
        hBox.setSpacing(10);
        hBox.setVisible(false);
        hBox.setPrefHeight(defaultbubbleHeight);
        hBox.setPrefWidth(defaultBubbleWidth);
        hBox.setMaxWidth(maxBubbleWidth);
        hBox.setMaxHeight(defaultbubbleHeight);
        hBox.setMinWidth(defaultBubbleWidth);
        hBox.setMinHeight(defaultbubbleHeight);
        HBox.setMargin(hBox, Insets.EMPTY);
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> {
            hBox.setVisible(true);
            hBox.setPrefHeight(hBox.getHeight());
            chatPane.layout();
            chatPane.setPrefHeight(chatPane.getHeight() + hBox.getHeight());
        });
        pause.play();
        return hBox;
    }

}