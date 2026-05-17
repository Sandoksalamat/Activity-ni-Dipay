import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FallingStar {
    static int starX = 0;
    static int starY = 0;
    static int basketX = 400;
    static int score = 0;
    static int lives = 3;
    static int fallSpeed = 3;
    static boolean running = false;

    static JLabel scoreLabel;
    static JLabel livesLabel;
    static JLabel runningLabel;

    static class GameCanvas extends JPanel {
        GameCanvas() {
            setBackground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Star Drawing
            g.setColor(Color.YELLOW);
            int[] starXPoints = {
                starX, starX + 10, starX + 30, starX + 15,
                starX + 20, starX, starX - 20, starX - 15,
                starX - 30, starX - 10
            };
            int[] starYPoints = {
                starY, starY + 20, starY + 20, starY + 35,
                starY + 55, starY + 42, starY + 55,
                starY + 35, starY + 20, starY + 20
            };
            g.fillPolygon(starXPoints, starYPoints, 10);

            // Yung Basket
            g.setColor(new Color(70, 130, 180));
            int[] basketXPoints = {basketX - 40, basketX + 40, basketX + 30, basketX - 30};
            int[] basketYPoints = {getHeight() - 40, getHeight() - 40, getHeight() - 20, getHeight() - 20};
            g.fillPolygon(basketXPoints, basketYPoints, 4);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Catch the Star");
        frame.setSize(1080, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        // Panel Defining
        JPanel cardPanel = new JPanel();
        CardLayout cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        // Main Menu
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

        JButton startGame = new JButton("Play");
        JButton exitGame = new JButton("Exit");

        Dimension btnsize = new Dimension(150, 100);
        startGame.setPreferredSize(btnsize);
        startGame.setMaximumSize(btnsize);
        exitGame.setPreferredSize(btnsize);
        exitGame.setMaximumSize(btnsize);

        startGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitGame.setAlignmentX(Component.CENTER_ALIGNMENT);

        menuPanel.add(Box.createVerticalGlue());
        menuPanel.add(startGame);
        menuPanel.add(Box.createVerticalStrut(10));
        menuPanel.add(exitGame);
        menuPanel.add(Box.createVerticalGlue());

        // In Game
        JPanel gamePanel = new JPanel(new BorderLayout());

        // Status Bar
        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 5));
        statusBar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        scoreLabel   = new JLabel("Score: " + score);
        livesLabel   = new JLabel("Lives: " + lives);
        runningLabel = new JLabel("Playing: " + running);
        statusBar.add(scoreLabel);
        statusBar.add(livesLabel);
        statusBar.add(runningLabel);

        // Game Canvas
        GameCanvas gameCanvas = new GameCanvas();
        gameCanvas.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 5, true),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Control Panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setPreferredSize(new Dimension(180, 0));
        controlPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
            BorderFactory.createEmptyBorder(10, 5, 10, 5)
        ));

        // Control Buttons
        JLabel controlLabel = new JLabel("Controls:");
        JButton btnStart  = new JButton("Start");
        JButton btnPause  = new JButton("Pause");
        JButton btnReset  = new JButton("Reset");

        Dimension ctrlBtnSize = new Dimension(100, 50);
        btnStart.setPreferredSize(ctrlBtnSize);
        btnStart.setMaximumSize(ctrlBtnSize);
        btnPause.setPreferredSize(ctrlBtnSize);
        btnPause.setMaximumSize(ctrlBtnSize);
        btnReset.setPreferredSize(ctrlBtnSize);
        btnReset.setMaximumSize(ctrlBtnSize);

        btnStart.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnPause.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnReset.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Start/Pause
        JPanel topBtnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        JButton btnStartSmall = new JButton("Start");
        JButton btnPauseSmall = new JButton("Pause");
        Dimension smallBtn = new Dimension(75, 35);
        btnStartSmall.setPreferredSize(smallBtn);
        btnPauseSmall.setPreferredSize(smallBtn);
        topBtnRow.add(btnStartSmall);
        topBtnRow.add(btnPauseSmall);
        topBtnRow.setMaximumSize(new Dimension(160, 40));
        topBtnRow.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Reset
        JPanel resetRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        JButton btnResetFull = new JButton("Reset");
        btnResetFull.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnResetFull.setPreferredSize(new Dimension(155, 35));
        btnResetFull.setMaximumSize(new Dimension(155, 35));

        // Difficulty 
        JLabel diffLabel = new JLabel("Difficulty:");
        JRadioButton diffEasy = new JRadioButton("Easy");
        JRadioButton diffMid  = new JRadioButton("Medium");
        JRadioButton diffHard = new JRadioButton("Hard");

        ButtonGroup diffGroup = new ButtonGroup();
        diffGroup.add(diffEasy);
        diffGroup.add(diffMid);
        diffGroup.add(diffHard);
        diffEasy.setSelected(true);

        diffEasy.addActionListener(e -> { fallSpeed = 3; lives = 3; livesLabel.setText("Lives: " + lives); });
        diffMid.addActionListener(e ->  { fallSpeed = 5; lives = 2; livesLabel.setText("Lives: " + lives); });
        diffHard.addActionListener(e -> { fallSpeed = 7; lives = 1; livesLabel.setText("Lives: " + lives); });

        // yung nasa baba
        JLabel hintKey   = new JLabel("Keyboard: ← →");
        JLabel hintSpace = new JLabel("Space: Pause/Resume");
        hintKey.setFont(new Font("Arial", Font.PLAIN, 11));
        hintSpace.setFont(new Font("Arial", Font.PLAIN, 11));

        // Button Events
        btnStartSmall.addActionListener(e -> {
            running = true;
            runningLabel.setText("Playing: " + running);
            frame.requestFocusInWindow();
        });

        btnPauseSmall.addActionListener(e -> {
            running = !running;
            runningLabel.setText("Playing: " + running);
            frame.requestFocusInWindow();
        });

        btnResetFull.addActionListener(e -> {
            running = false;
            score = 0;
            if (diffEasy.isSelected())      { lives = 3; fallSpeed = 3; }
            else if (diffMid.isSelected())  { lives = 2; fallSpeed = 5; }
            else if (diffHard.isSelected()) { lives = 1; fallSpeed = 7; }
            scoreLabel.setText("Score: " + score);
            livesLabel.setText("Lives: " + lives);
            runningLabel.setText("Playing: " + running);
            resetStar(gameCanvas);
            gameCanvas.repaint();
            frame.requestFocusInWindow();
        });

        controlPanel.add(Box.createVerticalStrut(5));
        controlPanel.add(controlLabel);
        controlPanel.add(Box.createVerticalStrut(8));
        controlPanel.add(topBtnRow);
        controlPanel.add(Box.createVerticalStrut(5));
        controlPanel.add(btnResetFull);
        controlPanel.add(Box.createVerticalStrut(20));
        controlPanel.add(diffLabel);
        controlPanel.add(Box.createVerticalStrut(5));
        controlPanel.add(diffEasy);
        controlPanel.add(diffMid);
        controlPanel.add(diffHard);
        controlPanel.add(Box.createVerticalGlue());
        controlPanel.add(hintKey);
        controlPanel.add(Box.createVerticalStrut(3));
        controlPanel.add(hintSpace);

        //Control Panel Alignment
        controlLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        diffLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        hintKey.setAlignmentX(Component.CENTER_ALIGNMENT);
        hintSpace.setAlignmentX(Component.CENTER_ALIGNMENT);

        diffEasy.setAlignmentX(Component.CENTER_ALIGNMENT);
        diffMid.setAlignmentX(Component.CENTER_ALIGNMENT);
        diffHard.setAlignmentX(Component.CENTER_ALIGNMENT);

        gamePanel.add(statusBar,    BorderLayout.NORTH);
        gamePanel.add(gameCanvas,   BorderLayout.CENTER);
        gamePanel.add(controlPanel, BorderLayout.EAST);

        cardPanel.add(menuPanel, "menu");
        cardPanel.add(gamePanel, "game");

        //Game Timer
        Timer gameTimer = new Timer(16, e -> {
            if (!running) return;

            starY += fallSpeed;

            int canvasHeight = gameCanvas.getHeight();
            boolean withinX = starX > basketX - 50 && starX < basketX + 50;
            boolean atBasket = starY + 55 >= canvasHeight - 40;

            if (withinX && atBasket) {
                score++;
                scoreLabel.setText("Score: " + score);
                resetStar(gameCanvas);
            } else if (starY > canvasHeight) {
                lives--;
                livesLabel.setText("Lives: " + lives);
                resetStar(gameCanvas);

                if (lives <= 0) {
                    running = false;
                    runningLabel.setText("Playing: " + running);
                    JOptionPane.showMessageDialog(frame,
                        "Game Over! Score: " + score,
                        "Game Over",
                        JOptionPane.INFORMATION_MESSAGE);
                    score = 0;
                    scoreLabel.setText("Score: " + score);
                    cardLayout.show(cardPanel, "menu");
                }
            }

            gameCanvas.repaint();
        });

        gameTimer.start();

        startGame.addActionListener(e -> {
            score = 0;
            lives = 3;
            fallSpeed = 3;
            running = false;
            scoreLabel.setText("Score: " + score);
            livesLabel.setText("Lives: " + lives);
            runningLabel.setText("Playing: " + running);
            diffEasy.setSelected(true);
            resetStar(null);
            cardLayout.show(cardPanel, "game");
            frame.requestFocusInWindow();
        });

        exitGame.addActionListener(e -> System.exit(0));

        // Key Listeners
        frame.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_SPACE:
                        running = !running;
                        runningLabel.setText("Playing: " + running);
                        break;
                    case KeyEvent.VK_LEFT:
                        basketX -= 20;
                        break;
                    case KeyEvent.VK_RIGHT:
                        basketX += 20;
                        break;
                    default:
                        break;
                }
                gameCanvas.repaint();
            }
            public void keyReleased(KeyEvent e) {}
            public void keyTyped(KeyEvent e) {}
        });

        frame.setFocusable(true);
        frame.requestFocusInWindow();

        cardLayout.show(cardPanel, "menu");
        frame.add(cardPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    static void resetStar(GameCanvas canvas) {
        starY = 0;
        if (canvas != null) {
            starX = 30 + (int)(Math.random() * (canvas.getWidth() - 60));
        } else {
            starX = 400;
        }
    }
}