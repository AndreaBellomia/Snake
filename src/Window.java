import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


class SnakeBlock extends JPanel {

    public void paint(Graphics g) {
        g.drawOval(-10, -10, 20, 20);
        g.setColor(Color.RED);
        g.fillOval(-10, -10, 20, 20);
    }
}


public class Window extends JFrame {
    private static class PointReset {
        int x;
        int y;

        public PointReset(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    char direction;

    int gameAreX;
    int gameAreY;

    JLabel[][] gridLabels;

    ArrayList<PointReset> resetCoordinates = new ArrayList<PointReset>();

    public Window(int gameAreX, int gameAreY) {
        super("Snake game");
        this.gameAreX = gameAreX;
        this.gameAreY = gameAreY;
        gridLabels = new JLabel[this.gameAreY][this.gameAreX];

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000);
        addKeyListener(getKeyboardKeyPress());
        setResizable(true);

        Container panel = getContentPane();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.weighty = 1.0;
        constraints.weightx = 1.0;
        constraints.anchor = GridBagConstraints.CENTER;

        for (int c = 0; c < this.gameAreY; c++) {
            for (int r = 0; r < this.gameAreX; r++) {
                constraints.gridy = c;
                constraints.gridx = r;
                gridLabels[c][r] = new JLabel(" ");
                panel.add(gridLabels[c][r], constraints);
            }
        }

        setVisible(true);
    }


    public void drawSnake(Game game) {
        final int constGameX = gameAreX / 2;
        final int constGameY = gameAreY / 2;

        for (PointReset p : resetCoordinates) {
            gridLabels[p.y][p.x].setText(" ");
        }

        resetCoordinates.clear();

        int[] appleCoordinates = game.getApple();
        int appleXCord = appleCoordinates[0] + constGameX;
        int appleYCord = appleCoordinates[1] + constGameY;
        try {
            gridLabels[appleYCord][appleXCord].setText("A");
            resetCoordinates.add(new PointReset(appleXCord, appleYCord));
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Apple out of screen: x=" + appleXCord + ", y=" + appleXCord);
        }

        int[][] snakeCoordinates = game.getSnake();
        for (int r = 0; r < snakeCoordinates.length; r++) {
            int snakeX = snakeCoordinates[r][0] + constGameX;
            int snakeY = snakeCoordinates[r][1] + constGameY;

            String snakeSymbol = (r == 0) ? "X" : ((r == snakeCoordinates.length - 1) ? "D" : "O");

            try {
                gridLabels[snakeY][snakeX].setText(snakeSymbol);
                resetCoordinates.add(new PointReset(snakeX, snakeY));
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Snake out of the screen");
            }

        }

        revalidate();
        repaint();
    }

    public char getDirection() {
        return direction;
    }

    private KeyAdapter getKeyboardKeyPress() {
        return new KeyAdapter() {
            public void keyPressed(KeyEvent event) {
                int keyCode = event.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_UP:
                        direction = 'T';
                        break;
                    case KeyEvent.VK_DOWN:
                        direction = 'D';
                        break;
                    case KeyEvent.VK_RIGHT:
                        direction = 'R';
                        break;
                    case KeyEvent.VK_LEFT:
                        direction = 'L';
                        break;
                }
            }
        };
    }

    public void drawGameLoose () {
        Container panel = getContentPane();
        panel.removeAll();

        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        JLabel label = new JLabel("You loose");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setAlignmentY(Component.CENTER_ALIGNMENT);
        panel.add(label);
        revalidate();
        repaint();
    }

    public void drawGameWinn () {
        Container panel = getContentPane();
        panel.removeAll();

        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        JLabel label = new JLabel("You Winn!");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setAlignmentY(Component.CENTER_ALIGNMENT);
        panel.add(label);
        revalidate();
        repaint();
    }
}