package com.kodilla.ticTacToe;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TicTacToe extends Application {

    private Image imageback = new Image("file:src/main/resources/background.jpg");
    private Image imgCircle = new Image("file:src/main/resources/circle.png");
    private Image imgCross = new Image("file:src/main/resources/cross.png");

    private FlowPane buttons = new FlowPane(Orientation.HORIZONTAL);
    private List<Button> buttonList;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(imageback, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        grid.setHgap(5.5);
        grid.setVgap(5.5);
        grid.setBackground(background);

        Scene scene = new Scene(grid, 1600, 900, Color.BLACK);

        primaryStage.setTitle("TicTacToe");
        primaryStage.setScene(scene);
        primaryStage.show();

        Button button1 = new Button("00", 0, 0);
        Button button2 = new Button("01", 0, 1);
        Button button3 = new Button("02", 0, 2);
        Button button4 = new Button("10", 1, 0);
        Button button5 = new Button("11", 1, 1);
        Button button6 = new Button("12", 1, 2);
        Button button7 = new Button("20", 2, 0);
        Button button8 = new Button("21", 2, 1);
        Button button9 = new Button("22", 2, 2);

        List<Button> buttons = new ArrayList<>();
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);
        buttons.add(button5);
        buttons.add(button6);
        buttons.add(button7);
        buttons.add(button8);
        buttons.add(button9);

        grid.add(button1, 0, 0);
        grid.add(button2, 1, 0);
        grid.add(button3, 2, 0);
        grid.add(button4, 0, 1);
        grid.add(button5, 1, 1);
        grid.add(button6, 2, 1);
        grid.add(button7, 0, 2);
        grid.add(button8, 1, 2);
        grid.add(button9, 2, 2);

       for (Button button : buttons) {
           button.setPrefSize(150, 150);
           button.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> handleUserClick(button));
       };

       this.buttonList = buttons;
    }

    List<Button> userSelection = new ArrayList<>();
    List<Button> computerSelection = new ArrayList<>();

    public void handleUserClick(Button button) {

        if (button.isEmpty()) {
            ImageView cross = new ImageView(imgCross);
            cross.setFitWidth(100);
            cross.setFitHeight(100);
            button.setGraphic(cross);
            button.setState(State.CROSS);
            userSelection.add(button);
            System.out.println("user tablica: " + userSelection.size());

            checkTheResult();

            //sprawdzasz czy jest koniec gry, wygrana lub remis - 2 wymiarowa tablica buttonow

            //ruch komputera
            handleComputerClick();
            //sprawdzasz czy jest koniec gry, wygrana lub remis
        }
    }

    public void handleComputerClick() {
        // set computer move if 2 in row clicked
        if (userSelection.size() <= 4) {
            final List<Button> availableButtons = this.buttonList.stream()
                    .filter(Button::isEmpty)
                    .collect(Collectors.toList());

            Collections.shuffle(availableButtons);

            Button button = availableButtons.get(0);
            ImageView circle = new ImageView(imgCircle);
            circle.setFitWidth(100);
            circle.setFitHeight(100);
            button.setGraphic(circle);
            button.setState(State.CIRCLE);
            computerSelection.add(button);
            System.out.println("komputer tablica: " + userSelection.size());
        }
    }

    public void checkTheResult() {

    }

}
