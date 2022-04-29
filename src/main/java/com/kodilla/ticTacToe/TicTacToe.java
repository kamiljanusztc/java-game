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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class TicTacToe extends Application {

    private Image imageback = new Image("file:src/main/resources/background.jpg");
    private Image circle = new Image("file:src/main/resources/circle.png");
    private Image cross = new Image("file:src/main/resources/cross.png");
    private FlowPane buttons = new FlowPane(Orientation.HORIZONTAL);

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

//        struktura rzedow i przekatnych
//        TBD

        Button button1 = new Button("00");
        Button button2 = new Button("01");
        Button button3 = new Button("02");
        Button button4 = new Button("10");
        Button button5 = new Button("11");
        Button button6 = new Button("12");
        Button button7 = new Button("20");
        Button button8 = new Button("21");
        Button button9 = new Button("22");

        grid.add(button1, 0, 0);
        grid.add(button2, 1, 0);
        grid.add(button3, 2, 0);
        grid.add(button4, 0, 1);
        grid.add(button5, 1, 1);
        grid.add(button6, 2, 1);
        grid.add(button7, 0, 2);
        grid.add(button8, 1, 2);
        grid.add(button9, 2, 2);

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

        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Button clickedButton = new Button(String.valueOf(e.getSource()));
                System.out.println("SPRAWDZENIE" + clickedButton);
                clickedButton.setText("NOWY");
                System.out.println("po zmianie" + clickedButton);
//                button1.setText("X");
            }
        };

       for (Button button : buttons) {
           button.setPrefSize(150, 150);
           button.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
       }

//        zbadanie, ktory button kliknal - dodac on click javafx


//        user click i computer click function

//        dodanie krzyzyka lub kolka do buttona

//        randomowe dodanie ruchu przez kompa
//        zbadanie czy wygral czy przegral - 2 funkcje



//        ImageView circleImg = new ImageView(circle);
//        ImageView crossImg = new ImageView(cross);
//        buttons.getChildren().add(circleImg);
//        buttons.getChildren().add(crossImg);
//        circleImg.setFitHeight(100);
//        circleImg.setFitWidth(100);
//        crossImg.setFitHeight(100);
//        crossImg.setFitWidth(100);

//        grid.add(buttons, 0, 0, 3, 3);




    }
}
