package com.kodilla.ticTacToe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TicTacToe extends Application {

    private Image imageback = new Image("file:src/main/resources/background.jpg");
    private Image imgCircle = new Image("file:src/main/resources/circle.png");
    private Image imgCross = new Image("file:src/main/resources/cross.png");

    private Button[][] board;

    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    public static void main(String[] args) {
        launch(args);
    }

    public boolean isLevelExpert = false;

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

        for (Button[] buttons : board) {
            for (final Button button : buttons) {
                button.setPrefSize(150, 150);
                button.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> handleUserClick(button));
            }
        }

        levelSelector(primaryStage);
    }

    private void levelSelector(Stage primaryStage) {

        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
        alert2.setTitle("TIC TAC TOE");
        alert2.setHeaderText("Welcome!");
        alert2.setContentText("Select the difficulty level");

        ButtonType buttonExpert = new ButtonType("Expert");
        ButtonType buttonBeginner = new ButtonType("Beginner");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert2.getButtonTypes().setAll(buttonExpert, buttonBeginner, buttonTypeCancel);

        Optional<ButtonType> result = alert2.showAndWait();
        if (result.get() == buttonExpert) {
            System.out.println("expert");
            isLevelExpert = true;
        } else if (result.get() == buttonBeginner) {
            System.out.println("beginner");
            isLevelExpert = false;
        } else {
            primaryStage.close();
        }
    }

    List<Button> userSelection = new ArrayList<>();
    List<Button> computerSelection = new ArrayList<>();

    public void handleUserClick(Button button) {

        if (button.isEmpty()) {
            drawUserSymbol(button);
            userSelection.add(button);

            if (userWin()) {
                clearBoard();
            } else if (isBoardFull()) {
                handleDraw();
                clearBoard();
            } else {
                handleComputerClick();
            }
        }
    }

    public void handleDraw() {
        System.out.println("DRAW");
        alert.setTitle("Tic Tac Toe");
        alert.setHeaderText("It's a draw!");
        alert.setContentText("Try again!");
        alert.showAndWait();
    }

    public boolean isBoardFull() {
        System.out.println("ile jest w computer selection: " + computerSelection.size());
        System.out.println("ile jest w user selection: " + userSelection.size());
        return userSelection.size() + computerSelection.size() == 9;
    }

    private void drawUserSymbol(Button button) {
        ImageView cross = new ImageView(imgCross);
        cross.setFitWidth(100);
        cross.setFitHeight(100);
        button.setGraphic(cross);
        button.setState(State.CROSS);
    }

    public void handleComputerClick() {
        if (computerWin()) {
            clearBoard();
            return;
        }

        if (isLevelExpert) {
            boolean moveMade = makeSmartMove();

            if (moveMade) {
                return;
            }
        }

        final List<Button> availableButtons = new ArrayList<>();

        for (Button[] buttons : board) {
            for (Button button : buttons) {
                if (button.isEmpty()) {
                    availableButtons.add(button);
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
        computerSelection.add(button);
    }

    public boolean userWin() {

        if (
                board[0][0].isCross() && board[0][1].isCross() && board[0][2].isCross() ||
                        board[1][0].isCross() && board[1][1].isCross() && board[1][2].isCross() ||
                        board[2][0].isCross() && board[2][1].isCross() && board[2][2].isCross() ||
                        board[0][0].isCross() && board[1][0].isCross() && board[2][0].isCross() ||
                        board[0][1].isCross() && board[1][1].isCross() && board[2][1].isCross() ||
                        board[0][2].isCross() && board[1][2].isCross() && board[2][2].isCross() ||
                        board[0][0].isCross() && board[1][1].isCross() && board[2][2].isCross() ||
                        board[2][0].isCross() && board[1][1].isCross() && board[0][2].isCross()
        ) {
            System.out.println("WINNER");
            alert.setTitle("Tic Tac Toe");
            alert.setHeaderText("Congrats!");
            alert.setContentText("You win!");
            alert.showAndWait();
            return true;
        }
        return false;
    }

    public boolean computerWin() {

        ImageView circle = new ImageView(imgCircle);
        circle.setFitWidth(100);
        circle.setFitHeight(100);

        Button computerButton;

        //        first row
        if (board[0][0].isCircle() && board[0][1].isCircle() && board[0][2].isEmpty()) {
            board[0][2].setState(State.CIRCLE);
            board[0][2].setGraphic(circle);
            computerButton = board[0][2];
            computerSelection.add(computerButton);
            System.out.println("LOSE");
            alert.setTitle("Tic Tac Toe");
            alert.setHeaderText("Computer win");
            alert.setContentText("Try again");
            alert.showAndWait();
            return true;
        }
        if (board[0][0].isCircle() && board[0][2].isCircle() && board[0][1].isEmpty()) {
            board[0][1].setState(State.CIRCLE);
            board[0][1].setGraphic(circle);
            computerButton = board[0][1];
            computerSelection.add(computerButton);
            System.out.println("LOSE");
            alert.setTitle("Tic Tac Toe");
            alert.setHeaderText("Computer win");
            alert.setContentText("Try again");
            alert.showAndWait();
            return true;
        }
        if (board[0][1].isCircle() && board[0][2].isCircle() && board[0][0].isEmpty()) {
            board[0][0].setState(State.CIRCLE);
            board[0][0].setGraphic(circle);
            computerButton = board[0][0];
            computerSelection.add(computerButton);
            System.out.println("LOSE");
            alert.setTitle("Tic Tac Toe");
            alert.setHeaderText("Computer win");
            alert.setContentText("Try again");
            alert.showAndWait();
            return true;
        }
//        second row
        if (board[1][0].isCircle() && board[1][1].isCircle() && board[1][2].isEmpty()) {
            board[1][2].setState(State.CIRCLE);
            board[1][2].setGraphic(circle);
            computerButton = board[1][2];
            computerSelection.add(computerButton);
            System.out.println("LOSE");
            alert.setTitle("Tic Tac Toe");
            alert.setHeaderText("Computer win");
            alert.setContentText("Try again");
            alert.showAndWait();
            return true;
        }
        if (board[1][0].isCircle() && board[1][2].isCircle() && board[1][1].isEmpty()) {
            board[1][1].setState(State.CIRCLE);
            board[1][1].setGraphic(circle);
            computerButton = board[1][1];
            computerSelection.add(computerButton);
            System.out.println("LOSE");
            alert.setTitle("Tic Tac Toe");
            alert.setHeaderText("Computer win");
            alert.setContentText("Try again");
            alert.showAndWait();
            return true;
        }
        if (board[1][1].isCircle() && board[1][2].isCircle() && board[1][0].isEmpty()) {
            board[1][0].setState(State.CIRCLE);
            board[1][0].setGraphic(circle);
            computerButton = board[1][0];
            computerSelection.add(computerButton);
            System.out.println("LOSE");
            alert.setTitle("Tic Tac Toe");
            alert.setHeaderText("Computer win");
            alert.setContentText("Try again");
            alert.showAndWait();
            return true;
        }
//        third row
        if (board[2][0].isCircle() && board[2][1].isCircle() && board[2][2].isEmpty()) {
            board[2][2].setState(State.CIRCLE);
            board[2][2].setGraphic(circle);
            computerButton = board[2][2];
            computerSelection.add(computerButton);
            System.out.println("LOSE");
            alert.setTitle("Tic Tac Toe");
            alert.setHeaderText("Computer win");
            alert.setContentText("Try again");
            alert.showAndWait();
            return true;
        }
        if (board[2][0].isCircle() && board[2][2].isCircle() && board[2][1].isEmpty()) {
            board[2][1].setState(State.CIRCLE);
            board[2][1].setGraphic(circle);
            computerButton = board[2][1];
            computerSelection.add(computerButton);
            System.out.println("LOSE");
            alert.setTitle("Tic Tac Toe");
            alert.setHeaderText("Computer win");
            alert.setContentText("Try again");
            alert.showAndWait();
            return true;
        }
        if (board[2][1].isCircle() && board[2][2].isCircle() && board[2][0].isEmpty()) {
            board[2][0].setState(State.CIRCLE);
            board[2][0].setGraphic(circle);
            computerButton = board[2][0];
            computerSelection.add(computerButton);
            System.out.println("LOSE");
            alert.setTitle("Tic Tac Toe");
            alert.setHeaderText("Computer win");
            alert.setContentText("Try again");
            alert.showAndWait();
            return true;
        }
//        first column
        if (board[0][0].isCircle() && board[1][0].isCircle() && board[2][0].isEmpty()) {
            board[2][0].setState(State.CIRCLE);
            board[2][0].setGraphic(circle);
            computerButton = board[2][0];
            computerSelection.add(computerButton);
            System.out.println("LOSE");
            alert.setTitle("Tic Tac Toe");
            alert.setHeaderText("Computer win");
            alert.setContentText("Try again");
            alert.showAndWait();
            return true;
        }
        if (board[0][0].isCircle() && board[2][0].isCircle() && board[1][0].isEmpty()) {
            board[1][0].setState(State.CIRCLE);
            board[1][0].setGraphic(circle);
            computerButton = board[1][0];
            computerSelection.add(computerButton);
            System.out.println("LOSE");
            alert.setTitle("Tic Tac Toe");
            alert.setHeaderText("Computer win");
            alert.setContentText("Try again");
            alert.showAndWait();
            return true;
        }
        if (board[1][0].isCircle() && board[2][0].isCircle() && board[0][0].isEmpty()) {
            board[0][0].setState(State.CIRCLE);
            board[0][0].setGraphic(circle);
            computerButton = board[0][0];
            computerSelection.add(computerButton);
            System.out.println("LOSE");
            alert.setTitle("Tic Tac Toe");
            alert.setHeaderText("Computer win");
            alert.setContentText("Try again");
            alert.showAndWait();
            return true;
        }
//        second column
        if (board[0][1].isCircle() && board[1][1].isCircle() && board[2][1].isEmpty()) {
            board[2][1].setState(State.CIRCLE);
            board[2][1].setGraphic(circle);
            computerButton = board[2][1];
            computerSelection.add(computerButton);
            System.out.println("LOSE");
            alert.setTitle("Tic Tac Toe");
            alert.setHeaderText("Computer win");
            alert.setContentText("Try again");
            alert.showAndWait();
            return true;
        }
        if (board[0][1].isCircle() && board[2][1].isCircle() && board[1][1].isEmpty()) {
            board[1][1].setState(State.CIRCLE);
            board[1][1].setGraphic(circle);
            computerButton = board[1][1];
            computerSelection.add(computerButton);
            System.out.println("LOSE");
            alert.setTitle("Tic Tac Toe");
            alert.setHeaderText("Computer win");
            alert.setContentText("Try again");
            alert.showAndWait();
            return true;
        }
        if (board[1][1].isCircle() && board[2][1].isCircle() && board[0][1].isEmpty()) {
            board[0][1].setState(State.CIRCLE);
            board[0][1].setGraphic(circle);
            computerButton = board[0][1];
            computerSelection.add(computerButton);
            System.out.println("LOSE");
            alert.setTitle("Tic Tac Toe");
            alert.setHeaderText("Computer win");
            alert.setContentText("Try again");
            alert.showAndWait();
            return true;
        }
//        third column
        if (board[0][2].isCircle() && board[1][2].isCircle() && board[2][2].isEmpty()) {
            board[2][2].setState(State.CIRCLE);
            board[2][2].setGraphic(circle);
            computerButton = board[2][2];
            computerSelection.add(computerButton);
            System.out.println("LOSE");
            alert.setTitle("Tic Tac Toe");
            alert.setHeaderText("Computer win");
            alert.setContentText("Try again");
            alert.showAndWait();
            return true;
        }
        if (board[0][2].isCircle() && board[2][2].isCircle() && board[1][2].isEmpty()) {
            board[1][2].setState(State.CIRCLE);
            board[1][2].setGraphic(circle);
            computerButton = board[1][2];
            computerSelection.add(computerButton);
            System.out.println("LOSE");
            alert.setTitle("Tic Tac Toe");
            alert.setHeaderText("Computer win");
            alert.setContentText("Try again");
            alert.showAndWait();
            return true;
        }
        if (board[1][2].isCircle() && board[2][2].isCircle() && board[0][2].isEmpty()) {
            board[0][2].setState(State.CIRCLE);
            board[0][2].setGraphic(circle);
            computerButton = board[0][2];
            computerSelection.add(computerButton);
            System.out.println("LOSE");
            alert.setTitle("Tic Tac Toe");
            alert.setHeaderText("Computer win");
            alert.setContentText("Try again");
            alert.showAndWait();
            return true;
        }
//        diagonally
        if (board[0][0].isCircle() && board[1][1].isCircle() && board[2][2].isEmpty()) {
            board[2][2].setState(State.CIRCLE);
            board[2][2].setGraphic(circle);
            computerButton = board[2][2];
            computerSelection.add(computerButton);
            System.out.println("LOSE");
            alert.setTitle("Tic Tac Toe");
            alert.setHeaderText("Computer win");
            alert.setContentText("Try again");
            alert.showAndWait();
            return true;
        }
        if (board[0][0].isCircle() && board[2][2].isCircle() && board[1][1].isEmpty()) {
            board[1][1].setState(State.CIRCLE);
            board[1][1].setGraphic(circle);
            computerButton = board[1][1];
            computerSelection.add(computerButton);
            System.out.println("LOSE");
            alert.setTitle("Tic Tac Toe");
            alert.setHeaderText("Computer win");
            alert.setContentText("Try again");
            alert.showAndWait();
            return true;
        }
        if (board[1][1].isCircle() && board[2][2].isCircle() && board[0][0].isEmpty()) {
            board[0][0].setState(State.CIRCLE);
            board[0][0].setGraphic(circle);
            computerButton = board[0][0];
            computerSelection.add(computerButton);
            System.out.println("LOSE");
            alert.setTitle("Tic Tac Toe");
            alert.setHeaderText("Computer win");
            alert.setContentText("Try again");
            alert.showAndWait();
            return true;
        }
        if (board[2][0].isCircle() && board[1][1].isCircle() && board[0][2].isEmpty()) {
            board[0][2].setState(State.CIRCLE);
            board[0][2].setGraphic(circle);
            computerButton = board[0][2];
            computerSelection.add(computerButton);
            System.out.println("LOSE");
            alert.setTitle("Tic Tac Toe");
            alert.setHeaderText("Computer win");
            alert.setContentText("Try again");
            alert.showAndWait();
            return true;
        }
        if (board[2][0].isCircle() && board[0][2].isCircle() && board[1][1].isEmpty()) {
            board[1][1].setState(State.CIRCLE);
            board[1][1].setGraphic(circle);
            computerButton = board[1][1];
            computerSelection.add(computerButton);
            System.out.println("LOSE");
            alert.setTitle("Tic Tac Toe");
            alert.setHeaderText("Computer win");
            alert.setContentText("Try again");
            alert.showAndWait();
            return true;
        }
        if (board[1][1].isCircle() && board[0][2].isCircle() && board[2][0].isEmpty()) {
            board[2][0].setState(State.CIRCLE);
            board[2][0].setGraphic(circle);
            computerButton = board[2][0];
            computerSelection.add(computerButton);
            System.out.println("LOSE");
            alert.setTitle("Tic Tac Toe");
            alert.setHeaderText("Computer win");
            alert.setContentText("Try again");
            alert.showAndWait();
            return true;
        }
        return false;
    }

    public boolean makeSmartMove() {

        ImageView circle = new ImageView(imgCircle);
        circle.setFitWidth(100);
        circle.setFitHeight(100);

        Button computerButton;

//        first row
        if (board[0][0].isCross() && board[0][1].isCross() && board[0][2].isEmpty()) {
            board[0][2].setState(State.CIRCLE);
            board[0][2].setGraphic(circle);
            computerButton = board[0][2];
            computerSelection.add(computerButton);
            return true;
        }
        if (board[0][0].isCross() && board[0][2].isCross() && board[0][1].isEmpty()) {
            board[0][1].setState(State.CIRCLE);
            board[0][1].setGraphic(circle);
            computerButton = board[0][1];
            computerSelection.add(computerButton);
            return true;
        }
        if (board[0][1].isCross() && board[0][2].isCross() && board[0][0].isEmpty()) {
            board[0][0].setState(State.CIRCLE);
            board[0][0].setGraphic(circle);
            computerButton = board[0][0];
            computerSelection.add(computerButton);
            return true;
        }
//        second row
        if (board[1][0].isCross() && board[1][1].isCross() && board[1][2].isEmpty()) {
            board[1][2].setState(State.CIRCLE);
            board[1][2].setGraphic(circle);
            computerButton = board[1][2];
            computerSelection.add(computerButton);
            return true;
        }
        if (board[1][0].isCross() && board[1][2].isCross() && board[1][1].isEmpty()) {
            board[1][1].setState(State.CIRCLE);
            board[1][1].setGraphic(circle);
            computerButton = board[1][1];
            computerSelection.add(computerButton);
            return true;
        }
        if (board[1][1].isCross() && board[1][2].isCross() && board[1][0].isEmpty()) {
            board[1][0].setState(State.CIRCLE);
            board[1][0].setGraphic(circle);
            computerButton = board[1][0];
            computerSelection.add(computerButton);
            return true;
        }
//        third row
        if (board[2][0].isCross() && board[2][1].isCross() && board[2][2].isEmpty()) {
            board[2][2].setState(State.CIRCLE);
            board[2][2].setGraphic(circle);
            computerButton = board[2][2];
            computerSelection.add(computerButton);
            return true;
        }
        if (board[2][0].isCross() && board[2][2].isCross() && board[2][1].isEmpty()) {
            board[2][1].setState(State.CIRCLE);
            board[2][1].setGraphic(circle);
            computerButton = board[2][1];
            computerSelection.add(computerButton);
            return true;
        }
        if (board[2][1].isCross() && board[2][2].isCross() && board[2][0].isEmpty()) {
            board[2][0].setState(State.CIRCLE);
            board[2][0].setGraphic(circle);
            computerButton = board[2][0];
            computerSelection.add(computerButton);
            return true;
        }
//        first column
        if (board[0][0].isCross() && board[1][0].isCross() && board[2][0].isEmpty()) {
            board[2][0].setState(State.CIRCLE);
            board[2][0].setGraphic(circle);
            computerButton = board[2][0];
            computerSelection.add(computerButton);
            return true;
        }
        if (board[0][0].isCross() && board[2][0].isCross() && board[1][0].isEmpty()) {
            board[1][0].setState(State.CIRCLE);
            board[1][0].setGraphic(circle);
            computerButton = board[1][0];
            computerSelection.add(computerButton);
            return true;
        }
        if (board[1][0].isCross() && board[2][0].isCross() && board[0][0].isEmpty()) {
            board[0][0].setState(State.CIRCLE);
            board[0][0].setGraphic(circle);
            computerButton = board[0][0];
            computerSelection.add(computerButton);
            return true;
        }
//        second column
        if (board[0][1].isCross() && board[1][1].isCross() && board[2][1].isEmpty()) {
            board[2][1].setState(State.CIRCLE);
            board[2][1].setGraphic(circle);
            computerButton = board[2][1];
            computerSelection.add(computerButton);
            return true;
        }
        if (board[0][1].isCross() && board[2][1].isCross() && board[1][1].isEmpty()) {
            board[1][1].setState(State.CIRCLE);
            board[1][1].setGraphic(circle);
            computerButton = board[1][1];
            computerSelection.add(computerButton);
            return true;
        }
        if (board[1][1].isCross() && board[2][1].isCross() && board[0][1].isEmpty()) {
            board[0][1].setState(State.CIRCLE);
            board[0][1].setGraphic(circle);
            computerButton = board[0][1];
            computerSelection.add(computerButton);
            return true;
        }
//        third column
        if (board[0][2].isCross() && board[1][2].isCross() && board[2][2].isEmpty()) {
            board[2][2].setState(State.CIRCLE);
            board[2][2].setGraphic(circle);
            computerButton = board[2][2];
            computerSelection.add(computerButton);
            return true;
        }
        if (board[0][2].isCross() && board[2][2].isCross() && board[1][2].isEmpty()) {
            board[1][2].setState(State.CIRCLE);
            board[1][2].setGraphic(circle);
            computerButton = board[1][2];
            computerSelection.add(computerButton);
            return true;
        }
        if (board[1][2].isCross() && board[2][2].isCross() && board[0][2].isEmpty()) {
            board[0][2].setState(State.CIRCLE);
            board[0][2].setGraphic(circle);
            computerButton = board[0][2];
            computerSelection.add(computerButton);
            return true;
        }
//        diagonally
        if (board[0][0].isCross() && board[1][1].isCross() && board[2][2].isEmpty()) {
            board[2][2].setState(State.CIRCLE);
            board[2][2].setGraphic(circle);
            computerButton = board[2][2];
            computerSelection.add(computerButton);
            return true;
        }
        if (board[0][0].isCross() && board[2][2].isCross() && board[1][1].isEmpty()) {
            board[1][1].setState(State.CIRCLE);
            board[1][1].setGraphic(circle);
            computerButton = board[1][1];
            computerSelection.add(computerButton);
            return true;
        }
        if (board[1][1].isCross() && board[2][2].isCross() && board[0][0].isEmpty()) {
            board[0][0].setState(State.CIRCLE);
            board[0][0].setGraphic(circle);
            computerButton = board[0][0];
            computerSelection.add(computerButton);
            return true;
        }
        if (board[2][0].isCross() && board[1][1].isCross() && board[0][2].isEmpty()) {
            board[0][2].setState(State.CIRCLE);
            board[0][2].setGraphic(circle);
            computerButton = board[0][2];
            computerSelection.add(computerButton);
            return true;
        }
        if (board[2][0].isCross() && board[0][2].isCross() && board[1][1].isEmpty()) {
            board[1][1].setState(State.CIRCLE);
            board[1][1].setGraphic(circle);
            computerButton = board[1][1];
            computerSelection.add(computerButton);
            return true;
        }
        if (board[1][1].isCross() && board[0][2].isCross() && board[2][0].isEmpty()) {
            board[2][0].setState(State.CIRCLE);
            board[2][0].setGraphic(circle);
            computerButton = board[2][0];
            computerSelection.add(computerButton);
            return true;
        }
        return false;
    }

    private void clearBoard() {

        for (Button[] buttons : board) {
            for (Button button : buttons) {
                button.setState(State.EMPTY);
                button.setGraphic(null);
                computerSelection.clear();
                userSelection.clear();
            }
        }
    }
}
