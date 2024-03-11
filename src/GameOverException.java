public class GameOverException extends Exception {
    public GameOverException() {
        super("Game Over");
    }

    public GameOverException(String message) {
        super(message);
    }
}
