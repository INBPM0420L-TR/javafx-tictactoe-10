package tictactoe.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.Arrays;
import java.util.Optional;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor
public class TicTacToeState {
    public static int SIZE = 3;
    public static int LENGTH = 3;

    Value[][] board;
    Value nextPlayer;

    public TicTacToeState() {
        this.board = new Value[SIZE][SIZE];
        this.nextPlayer = Value.BLUE;
        for (int i = 0; i < SIZE; i++) {
            Arrays.fill(this.board[i], Value.EMPTY);
        }
    }

    public boolean canMove(
            final int row,
            final int col) {
        try {
            if (getWinner().isPresent() || board[row][col] != Value.EMPTY) {
                return false;
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {
            return false;
        }
        return true;
    }

    public TicTacToeState doMove(
            final int row,
            final int col) {

        final var newBoard = new Value[SIZE][];
        for (int i = 0; i < SIZE; i++) {
            newBoard[i] = Arrays.copyOf(board[i], SIZE);
        }
        newBoard[row][col] = nextPlayer;

        return this.toBuilder()
                .nextPlayer(switch (nextPlayer) {
                    case RED -> Value.BLUE;
                    case BLUE -> Value.RED;
                    default -> throw new IllegalStateException();
                })
                .board(newBoard)
                .build();
    }

    public Optional<Value> getWinner() {
        boolean hasEmpty = false;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == Value.EMPTY) {
                    hasEmpty = true;
                    continue;
                }

                try {
                    if (board[row][col] == board[row][col + 1]
                            && board[row][col] == board[row][col + 2]) {
                        return Optional.of(board[row][col]);
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }

                try {
                    if (board[row][col] == board[row + 1][col]
                            && board[row][col] == board[row + 2][col]) {
                        return Optional.of(board[row][col]);
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }

                try {
                    if (board[row][col] == board[row + 1][col + 1]
                            && board[row][col] == board[row + 2][col + 2]) {
                        return Optional.of(board[row][col]);
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }

                try {
                    if (board[row][col] == board[row - 1][col + 1]
                            && board[row][col] == board[row - 2][col + 2]) {
                        return Optional.of(board[row][col]);
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
            }
        }

        return hasEmpty ? Optional.empty() : Optional.of(Value.EMPTY);
    }

    public enum Value {
        EMPTY, RED, BLUE
    }
}
