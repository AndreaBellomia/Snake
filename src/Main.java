import java.util.Arrays;

public class Main extends Thread {
    public static void main(String[] args) {
        int gameXArea = 10;
        int gameYArea = 10;

        if ( gameXArea % 2 == 0  || gameYArea % 2 == 0 ) {
            System.out.println("Game area must be a odd number");
            gameXArea++;
            gameYArea++;

            System.out.println("New game area must be: x,y " + gameXArea + "-" + gameYArea);
        }

        boolean gameLoose = true;

        Window window = new Window(gameXArea, gameYArea);

        Game game = new Game(gameXArea, gameYArea);

        game.generateApple();

        try {
            while (true) {

                char snakeDirection = window.getDirection();

                if (game.headInAppleCoordinates()) {
                    game.growSnake();
                    game.generateApple();
                }

                try {
                    game.updateSnakePosition(snakeDirection);
                } catch (GameOverException e) {
                    System.out.println("Loose");
                    window.drawGameLoose();
                    break;
                }

                if (game.gameWinn()) {
                    System.out.println("Winn");
                    window.drawGameWinn();
                    break;
                }

                window.drawSnake(game);

                Thread.sleep(game.getTimeDifficulty());

            }
        } catch (InterruptedException e) {
            System.out.println("While interrupted");
        }




    }



}