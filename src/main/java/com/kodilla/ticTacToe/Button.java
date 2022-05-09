package com.kodilla.ticTacToe;

public class Button extends javafx.scene.control.Button {

    private int x;
    private int y;
    private State state;

    public Button(String name, int x, int y){
        super(name);
        this.x = x;
        this.y = y;
        this.state = State.EMPTY;
        setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15px; -fx-text-fill: red");
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public State getState() {
        return state;
    }

    public boolean isEmpty() {
        return this.state == State.EMPTY;
    }

    public boolean isCross() {
        return this.state == State.CROSS;
    }

    public boolean isCircle() {
        return this.state == State.CIRCLE;
    }
}
