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
        setStyle("-fx-background-color: #ffffff; -fx-background-radius: 15px; -fx-text-fill: transparent");
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public boolean isEmpty() {
        return this.state == State.EMPTY;
    }
}
