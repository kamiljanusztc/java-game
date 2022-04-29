package com.kodilla.ticTacToe;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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

//        struktura rzedow i przekatnych
//        TBD

        Button button1 = new Button("00");
        Button button2 = new Button("10");
        Button button3 = new Button("20");
        Button button4 = new Button("01");
        Button button5 = new Button("11");
        Button button6 = new Button("21");
        Button button7 = new Button("02");
        Button button8 = new Button("01");
        Button button9 = new Button("02");

        button1.setPrefSize(150, 150);
        button2.setPrefSize(150, 150);
        button3.setPrefSize(150, 150);
        button4.setPrefSize(150, 150);
        button5.setPrefSize(150, 150);
        button6.setPrefSize(150, 150);
        button7.setPrefSize(150, 150);
        button8.setPrefSize(150, 150);
        button9.setPrefSize(150, 150);

        grid.add(button1, 0, 0);
        grid.add(button2, 1, 0);
        grid.add(button3, 2, 0);
        grid.add(button4, 0, 1);
        grid.add(button5, 1, 1);
        grid.add(button6, 2, 1);
        grid.add(button7, 0, 2);
        grid.add(button8, 1, 2);
        grid.add(button9, 2, 2);

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

        grid.add(buttons, 0, 0, 3, 3);

        Scene scene = new Scene(grid, 1600, 900, Color.BLACK);

        primaryStage.setTitle("TicTacToe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
