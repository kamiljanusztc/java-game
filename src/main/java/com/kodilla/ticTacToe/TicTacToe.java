package com.kodilla.ticTacToe;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
    private Button[][] board;
    private boolean[][] userMovesArray = new boolean[3][3];

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

        board = new Button[3][3];
        board[0][0] = button1;
        board[0][1] = button2;
        board[0][2] = button3;
        board[1][0] = button4;
        board[1][1] = button5;
        board[1][2] = button6;
        board[2][0] = button7;
        board[2][1] = button8;
        board[2][2] = button9;

        grid.add(button1, 0, 0);
        grid.add(button2, 1, 0);
        grid.add(button3, 2, 0);
        grid.add(button4, 0, 1);
        grid.add(button5, 1, 1);
        grid.add(button6, 2, 1);
        grid.add(button7, 0, 2);
        grid.add(button8, 1, 2);
        grid.add(button9, 2, 2);

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                final Button button = board[i][j];
                button.setPrefSize(150, 150);
                button.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> handleUserClick(button));
            }
        }
    }

    List<Button> userSelection = new ArrayList<>();

    public void handleUserClick(Button button) {

        if (button.isEmpty()) {
            ImageView cross = new ImageView(imgCross);
            cross.setFitWidth(100);
            cross.setFitHeight(100);
            button.setGraphic(cross);
            button.setState(State.CROSS);
            userSelection.add(button);
            System.out.println("user tablica: " + userSelection.size());

            handleComputerClick();
            checkTheResult();
        }
    }

    public void handleComputerClick() {
        boolean moveMade = makeSmartMove();

        if (moveMade) {
            return;
        }

        final List<Button> availableButtons = new ArrayList<>();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].isEmpty()) {
                    availableButtons.add(board[i][j]);
                }
            }
        }

        Collections.shuffle(availableButtons);

        ImageView circle = new ImageView(imgCircle);
        circle.setFitWidth(100);
        circle.setFitHeight(100);

        Button button = availableButtons.get(0);
        button.setGraphic(circle);
        button.setState(State.CIRCLE);
    }

    public void checkTheResult() {

        userSelection.stream().forEach(button -> {
            userMovesArray[button.getX()][button.getY()] = true;
        });

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (
                userMovesArray[0][0] && userMovesArray[0][1] && userMovesArray[0][2] ||
                        userMovesArray[1][0] && userMovesArray[1][1] && userMovesArray[1][2] ||
                        userMovesArray[2][0] && userMovesArray[2][1] && userMovesArray[2][2] ||
                        userMovesArray[0][0] && userMovesArray[1][0] && userMovesArray[2][0] ||
                        userMovesArray[0][1] && userMovesArray[1][1] && userMovesArray[2][1] ||
                        userMovesArray[0][2] && userMovesArray[1][2] && userMovesArray[2][2] ||
                        userMovesArray[0][0] && userMovesArray[1][1] && userMovesArray[2][2] ||
                        userMovesArray[2][0] && userMovesArray[1][1] && userMovesArray[0][2]
        ) {
            System.out.println("WINNER");
            alert.setTitle("Tic Tac Toe");
            alert.setHeaderText("Congrats!");
            alert.setContentText("You win!");
            alert.showAndWait();
        }

//        for (int i = 0; i <movesArray.length; i++) {
//            for (int j = 0; j <movesArray[i].length; j++) {
//                System.out.println("movesArray: " + movesArray);
//            }
//        }
    }

    public boolean makeSmartMove() {
        ImageView circle = new ImageView(imgCircle);
        circle.setFitWidth(100);
        circle.setFitHeight(100);

//        COMPUTER SMART MOVE

//        first row
        if (board[0][0].isCross() && board[0][1].isCross() && board[0][2].isEmpty() ) {
            board[0][2].setState(State.CIRCLE);
            board[0][2].setGraphic(circle);
            return true;
        }
        if (board[0][0].isCross() && board[0][2].isCross() && board[0][1].isEmpty() ) {
            board[0][1].setState(State.CIRCLE);
            board[0][1].setGraphic(circle);
            return true;
        }
        if (board[0][1].isCross() && board[0][2].isCross() && board[0][0].isEmpty() ) {
            board[0][0].setState(State.CIRCLE);
            board[0][0].setGraphic(circle);
            return true;
        }
//        second row
        if (board[1][0].isCross() && board[1][1].isCross() && board[1][2].isEmpty() ) {
            board[1][2].setState(State.CIRCLE);
            board[1][2].setGraphic(circle);
            return true;
        }
        if (board[1][0].isCross() && board[1][2].isCross() && board[1][1].isEmpty() ) {
            board[1][1].setState(State.CIRCLE);
            board[1][1].setGraphic(circle);
            return true;
        }
        if (board[1][1].isCross() && board[1][2].isCross() && board[1][0].isEmpty() ) {
            board[1][0].setState(State.CIRCLE);
            board[1][0].setGraphic(circle);
            return true;
        }
//        third row
        if (board[2][0].isCross() && board[2][1].isCross() && board[2][2].isEmpty() ) {
            board[2][2].setState(State.CIRCLE);
            board[2][2].setGraphic(circle);
            return true;
        }
        if (board[2][0].isCross() && board[2][2].isCross() && board[2][1].isEmpty() ) {
            board[2][1].setState(State.CIRCLE);
            board[2][1].setGraphic(circle);
            return true;
        }
        if (board[2][1].isCross() && board[2][2].isCross() && board[2][0].isEmpty() ) {
            board[2][0].setState(State.CIRCLE);
            board[2][0].setGraphic(circle);
            return true;
        }
//        first column
        if (board[0][0].isCross() && board[1][0].isCross() && board[2][0].isEmpty() ) {
            board[2][0].setState(State.CIRCLE);
            board[2][0].setGraphic(circle);
            return true;
        }
        if (board[0][0].isCross() && board[2][0].isCross() && board[1][0].isEmpty() ) {
            board[1][0].setState(State.CIRCLE);
            board[1][0].setGraphic(circle);
            return true;
        }
        if (board[1][0].isCross() && board[2][0].isCross() && board[0][0].isEmpty() ) {
            board[0][0].setState(State.CIRCLE);
            board[0][0].setGraphic(circle);
            return true;
        }
//        second column
        if (board[0][1].isCross() && board[1][1].isCross() && board[2][1].isEmpty() ) {
            board[2][1].setState(State.CIRCLE);
            board[2][1].setGraphic(circle);
            return true;
        }
        if (board[0][1].isCross() && board[2][1].isCross() && board[1][1].isEmpty() ) {
            board[1][1].setState(State.CIRCLE);
            board[1][1].setGraphic(circle);
            return true;
        }if (board[1][1].isCross() && board[2][1].isCross() && board[0][1].isEmpty() ) {
            board[0][1].setState(State.CIRCLE);
            board[0][1].setGraphic(circle);
            return true;
        }
//        third column
        if (board[0][2].isCross() && board[1][2].isCross() && board[2][2].isEmpty() ) {
            board[2][2].setState(State.CIRCLE);
            board[2][2].setGraphic(circle);
            return true;
        }
        if (board[0][2].isCross() && board[2][2].isCross() && board[1][2].isEmpty() ) {
            board[1][2].setState(State.CIRCLE);
            board[1][2].setGraphic(circle);
            return true;
        }
        if (board[1][2].isCross() && board[2][2].isCross() && board[0][2].isEmpty() ) {
            board[0][2].setState(State.CIRCLE);
            board[0][2].setGraphic(circle);
            return true;
        }
//        diagonally
        if (board[0][0].isCross() && board[1][1].isCross() && board[2][2].isEmpty() ) {
            board[2][2].setState(State.CIRCLE);
            board[2][2].setGraphic(circle);
            return true;
        }
        if (board[0][0].isCross() && board[2][2].isCross() && board[1][1].isEmpty() ) {
            board[1][1].setState(State.CIRCLE);
            board[1][1].setGraphic(circle);
            return true;
        }
        if (board[1][1].isCross() && board[2][2].isCross() && board[0][0].isEmpty() ) {
            board[0][0].setState(State.CIRCLE);
            board[0][0].setGraphic(circle);
            return true;
        }
        if (board[2][0].isCross() && board[1][1].isCross() && board[0][2].isEmpty() ) {
            board[0][2].setState(State.CIRCLE);
            board[0][2].setGraphic(circle);
            return true;
        }
        if (board[2][0].isCross() && board[0][2].isCross() && board[1][1].isEmpty() ) {
            board[1][1].setState(State.CIRCLE);
            board[1][1].setGraphic(circle);
            return true;
        }
        if (board[1][1].isCross() && board[0][2].isCross() && board[2][0].isEmpty() ) {
            board[2][0].setState(State.CIRCLE);
            board[2][0].setGraphic(circle);
            return true;
        }

//        COMPUTER WIN

        //        first row
        if (board[0][0].isCircle() && board[0][1].isCircle() && board[0][2].isEmpty() ) {
            board[0][2].setState(State.CIRCLE);
            board[0][2].setGraphic(circle);
            return true;
        }
        if (board[0][0].isCircle() && board[0][2].isCircle() && board[0][1].isEmpty() ) {
            board[0][1].setState(State.CIRCLE);
            board[0][1].setGraphic(circle);
            return true;
        }
        if (board[0][1].isCircle() && board[0][2].isCircle() && board[0][0].isEmpty() ) {
            board[0][0].setState(State.CIRCLE);
            board[0][0].setGraphic(circle);
            return true;
        }
//        second row
        if (board[1][0].isCircle() && board[1][1].isCircle() && board[1][2].isEmpty() ) {
            board[1][2].setState(State.CIRCLE);
            board[1][2].setGraphic(circle);
            return true;
        }
        if (board[1][0].isCircle() && board[1][2].isCircle() && board[1][1].isEmpty() ) {
            board[1][1].setState(State.CIRCLE);
            board[1][1].setGraphic(circle);
            return true;
        }
        if (board[1][1].isCircle() && board[1][2].isCircle() && board[1][0].isEmpty() ) {
            board[1][0].setState(State.CIRCLE);
            board[1][0].setGraphic(circle);
            return true;
        }
//        third row
        if (board[2][0].isCircle() && board[2][1].isCircle() && board[2][2].isEmpty() ) {
            board[2][2].setState(State.CIRCLE);
            board[2][2].setGraphic(circle);
            return true;
        }
        if (board[2][0].isCircle() && board[2][2].isCircle() && board[2][1].isEmpty() ) {
            board[2][1].setState(State.CIRCLE);
            board[2][1].setGraphic(circle);
            return true;
        }
        if (board[2][1].isCircle() && board[2][2].isCircle() && board[2][0].isEmpty() ) {
            board[2][0].setState(State.CIRCLE);
            board[2][0].setGraphic(circle);
            return true;
        }
//        first column
        if (board[0][0].isCircle() && board[1][0].isCircle() && board[2][0].isEmpty() ) {
            board[2][0].setState(State.CIRCLE);
            board[2][0].setGraphic(circle);
            return true;
        }
        if (board[0][0].isCircle() && board[2][0].isCircle() && board[1][0].isEmpty() ) {
            board[1][0].setState(State.CIRCLE);
            board[1][0].setGraphic(circle);
            return true;
        }
        if (board[1][0].isCircle() && board[2][0].isCircle() && board[0][0].isEmpty() ) {
            board[0][0].setState(State.CIRCLE);
            board[0][0].setGraphic(circle);
            return true;
        }
//        second column
        if (board[0][1].isCircle() && board[1][1].isCircle() && board[2][1].isEmpty() ) {
            board[2][1].setState(State.CIRCLE);
            board[2][1].setGraphic(circle);
            return true;
        }
        if (board[0][1].isCircle() && board[2][1].isCircle() && board[1][1].isEmpty() ) {
            board[1][1].setState(State.CIRCLE);
            board[1][1].setGraphic(circle);
            return true;
        }if (board[1][1].isCircle() && board[2][1].isCircle() && board[0][1].isEmpty() ) {
            board[0][1].setState(State.CIRCLE);
            board[0][1].setGraphic(circle);
            return true;
        }
//        third column
        if (board[0][2].isCircle() && board[1][2].isCircle() && board[2][2].isEmpty() ) {
            board[2][2].setState(State.CIRCLE);
            board[2][2].setGraphic(circle);
            return true;
        }
        if (board[0][2].isCircle() && board[2][2].isCircle() && board[1][2].isEmpty() ) {
            board[1][2].setState(State.CIRCLE);
            board[1][2].setGraphic(circle);
            return true;
        }
        if (board[1][2].isCircle() && board[2][2].isCircle() && board[0][2].isEmpty() ) {
            board[0][2].setState(State.CIRCLE);
            board[0][2].setGraphic(circle);
            return true;
        }
//        diagonally
        if (board[0][0].isCircle() && board[1][1].isCircle() && board[2][2].isEmpty() ) {
            board[2][2].setState(State.CIRCLE);
            board[2][2].setGraphic(circle);
            return true;
        }
        if (board[0][0].isCircle() && board[2][2].isCircle() && board[1][1].isEmpty() ) {
            board[1][1].setState(State.CIRCLE);
            board[1][1].setGraphic(circle);
            return true;
        }
        if (board[1][1].isCircle() && board[2][2].isCircle() && board[0][0].isEmpty() ) {
            board[0][0].setState(State.CIRCLE);
            board[0][0].setGraphic(circle);
            return true;
        }
        if (board[2][0].isCircle() && board[1][1].isCircle() && board[0][2].isEmpty() ) {
            board[0][2].setState(State.CIRCLE);
            board[0][2].setGraphic(circle);
            return true;
        }
        if (board[2][0].isCircle() && board[0][2].isCircle() && board[1][1].isEmpty() ) {
            board[1][1].setState(State.CIRCLE);
            board[1][1].setGraphic(circle);
            return true;
        }
        if (board[1][1].isCircle() && board[0][2].isCircle() && board[2][0].isEmpty() ) {
            board[2][0].setState(State.CIRCLE);
            board[2][0].setGraphic(circle);
            return true;
        }

        return false;
    }
}
