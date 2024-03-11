import javax.management.InvalidAttributeValueException;
import java.util.Arrays;
import java.util.Random;

public class Game {
    int gameXArea;
    int gameYArea;
    int[][] snakeCoordinates;

    int[] appleCoordinates;

    public Game(int gameXArea, int gameYArea) {
        this.gameXArea = gameXArea / 2;
        this.gameYArea = gameYArea / 2;

        snakeCoordinates = new int[][] {{0, 0}, {0, 0}, {0, 0}};
    }

    public int[][] getSnake () {
        return snakeCoordinates;
    }

    public void growSnake() {
        final int N = snakeCoordinates.length;
        int[][] newSnakeCoordinates = Arrays.copyOf(snakeCoordinates, N + 1);
        newSnakeCoordinates[N] = Arrays.copyOf(snakeCoordinates[N - 1], 2);
        snakeCoordinates = newSnakeCoordinates;
    }

    public void updateSnakePosition(char direction) throws GameOverException {
        for (int i = snakeCoordinates.length - 1; i >= 0; i--) {
            if (i > 0) {
                snakeCoordinates[i][0] = snakeCoordinates[i - 1][0];
                snakeCoordinates[i][1] = snakeCoordinates[i - 1][1];
                continue;
            }

            switch (direction) {
                case 'T':
                    snakeCoordinates[i][1]--;
                    break;
                case 'D':
                    snakeCoordinates[i][1]++;
                    break;
                case 'R':
                    snakeCoordinates[i][0]++;
                    break;
                case 'L':
                    snakeCoordinates[i][0]--;
                    break;
            }
        }

        if (direction !='\u0000') {
            checkSnakeCondition();
        }

    }

    public void checkSnakeCondition() throws GameOverException {
        int[][] snakeBody = Arrays.copyOfRange(snakeCoordinates, 1, snakeCoordinates.length);

        for (int[] i : snakeBody) {
            if (snakeCoordinates[0][0] == i[0] && snakeCoordinates[0][1] == i[1]) {
                throw new GameOverException();
            }

            if (snakeCoordinates[0][0] > gameXArea  || snakeCoordinates[0][0] < gameXArea * -1) {
                throw new GameOverException();
            }

            if (snakeCoordinates[0][1] > gameYArea || snakeCoordinates[0][1] < gameYArea * -1) {
                throw new GameOverException();
            }
        }

    }

    private int[] generateAppleCoordinates() {
        int minX = gameXArea * -1;
        int minY = gameYArea * -1;
        int maxX = gameXArea - 1;
        int maxY = gameYArea - 1;
        Random random = new Random();

        int x, y;

        do {
            x = random.nextInt((maxX - minX) + 1) + minX;
            y = random.nextInt((maxY - minY) + 1) + minY;
        } while (isCoordinateExcluded(x, y));

        return new int[] {x, y};
    }

    private boolean isCoordinateExcluded(int x, int y) {
        for (int[] coordinate : snakeCoordinates) {
            if (coordinate[0] == x && coordinate[1] == y) {
                return true;
            }
        }
        return false;
    }

    public void generateApple () {
        appleCoordinates = generateAppleCoordinates();
    }

    public int[] getApple() {
        return appleCoordinates;
    }

    public boolean headInAppleCoordinates() {
        return appleCoordinates[0] == snakeCoordinates[0][0] && appleCoordinates[1] == snakeCoordinates[0][1];
    }

    public boolean gameWinn() {
        return snakeCoordinates.length == (gameXArea * gameYArea) - 1;
    }

    public int getTimeDifficulty() {
        int gameArea = gameXArea * gameYArea;
        int snakeBlocks = getSnake().length;

        double density = (double) snakeBlocks / gameArea;
        double multiplier = 1 - density;
        int difficultyTime = (int) (300 * multiplier);

        return Math.max(difficultyTime, 50);
    }

}
