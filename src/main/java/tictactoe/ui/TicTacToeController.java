package tictactoe.ui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import tictactoe.model.TicTacToeState;

public class TicTacToeController {
    @FXML
    private GridPane board;
    @FXML
    private Button startButton;
    @FXML
    private Button finishButton;

    private BooleanProperty inGame;

    private ObjectProperty<TicTacToeState> model;

    @FXML
    private void initialize() {
        model = new SimpleObjectProperty<>(
                new TicTacToeState()
        );

        model.addListener((event, old, actual) -> {
            drawGrid();
        });

        inGame.addListener((event, old, actual) -> {
            startButton.setDisable(actual);
            finishButton.setDisable(!actual);
        });

        finishButton.setDisable(true);
        drawGrid();
    }

    private void drawGrid() {

    }

    private StackPane createSquare(
            TicTacToeState.Value value) {

        final var square = new StackPane();
        square.setPrefWidth(100);
        square.setPrefHeight(100);

        if (inGame.get()) {
            square.setOnMouseClicked(this::handleMouseClick);
        }

        final var circle = new Circle(100);
        circle.fillProperty().set(switch (value) {
            case BLUE -> Color.BLUE;
            case RED -> Color.RED;
            case EMPTY -> Color.TRANSPARENT;
        });

        square.getChildren().add(circle);
        return square;
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {

    }

    @FXML
    private void onStartGame() {
    }

    @FXML
    private void onFinishGame() {
    }
}
