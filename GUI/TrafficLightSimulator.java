import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrafficLightSimulator extends JFrame {

    private String currentState;
    private int countdown;
    private boolean automaticMode;
    private int delayMs;
    private Timer timer;

    private SignalPanel signalPanel;
    private JRadioButton automaticRadio;
    private JRadioButton manualRadio;
    private JComboBox<String> speedComboBox;
    private JButton startButton;
    private JButton stopButton;
    private JButton nextStateButton;
    private JButton resetButton;
    private JLabel currentLightLabel;
    private JLabel countdownLabel;

    public TrafficLightSimulator() {
        resetToDefaults();

        setTitle("Traffic Light and Pedestrian Signal Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 450);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout(10, 10));

        signalPanel = new SignalPanel();
        add(signalPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));
        controlPanel.setPreferredSize(new Dimension(250, 400));

        JPanel modePanel = new JPanel(new GridLayout(2, 1));
        modePanel.setBorder(BorderFactory.createTitledBorder("Mode"));
        automaticRadio = new JRadioButton("Automatic", automaticMode);
        manualRadio = new JRadioButton("Manual", !automaticMode);
        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(automaticRadio);
        modeGroup.add(manualRadio);
        modePanel.add(automaticRadio);
        modePanel.add(manualRadio);

        JPanel speedPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        speedPanel.setBorder(BorderFactory.createTitledBorder("Speed"));
        String[] speeds = {"500 ms", "1000 ms", "1500 ms"};
        speedComboBox = new JComboBox<>(speeds);
        speedComboBox.setSelectedIndex(1);
        speedPanel.add(speedComboBox);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        nextStateButton = new JButton("Next State");
        resetButton = new JButton("Reset");
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(nextStateButton);
        buttonPanel.add(resetButton);

        JPanel statusPanel = new JPanel(new GridLayout(2, 1));
        statusPanel.setBorder(BorderFactory.createTitledBorder("Status"));
        currentLightLabel = new JLabel("Current light: " + currentState);
        countdownLabel = new JLabel("Countdown: 0" + countdown + " seconds");
        statusPanel.add(currentLightLabel);
        statusPanel.add(countdownLabel);

        controlPanel.add(modePanel);
        controlPanel.add(Box.createVerticalStrut(10));
        controlPanel.add(speedPanel);
        controlPanel.add(Box.createVerticalStrut(10));
        controlPanel.add(buttonPanel);
        controlPanel.add(Box.createVerticalStrut(10));
        controlPanel.add(statusPanel);

        add(controlPanel, BorderLayout.EAST);

        setupTimer();
        setupEventHandlers();
        updateButtonStates();
    }

    private void resetToDefaults() {
        currentState = "RED";
        countdown = 8;
        automaticMode = true;
        delayMs = 1000;
        if (timer != null) {
            timer.stop();
        }
    }

    private void setupTimer() {
        timer = new Timer(delayMs, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countdown--;
                if (countdown <= 0) {
                    advanceState();
                }
                updateStatusDisplay();
            }
        });
    }

    private void advanceState() {
        switch (currentState) {
            case "RED":
                currentState = "GREEN";
                countdown = 10;
                break;
            case "GREEN":
                currentState = "YELLOW";
                countdown = 3;
                break;
            case "YELLOW":
                currentState = "RED";
                countdown = 8;
                break;
        }
    }

    private void updateStatusDisplay() {
        currentLightLabel.setText("Current light: " + currentState);
        countdownLabel.setText(String.format("Countdown: %02d seconds", countdown));
        signalPanel.repaint();
    }

    private void updateButtonStates() {
        if (automaticMode) {
            startButton.setEnabled(!timer.isRunning());
            stopButton.setEnabled(timer.isRunning());
            nextStateButton.setEnabled(false);
        } else {
            timer.stop();
            startButton.setEnabled(false);
            stopButton.setEnabled(false);
            nextStateButton.setEnabled(true);
        }
    }

    private void setupEventHandlers() {
        startButton.addActionListener(e -> {
            timer.start();
            updateButtonStates();
        });

        stopButton.addActionListener(e -> {
            timer.stop();
            updateButtonStates();
        });

        nextStateButton.addActionListener(e -> {
            advanceState();
            updateStatusDisplay();
        });

        resetButton.addActionListener(e -> {
            resetToDefaults();
            speedComboBox.setSelectedIndex(1);
            automaticRadio.setSelected(true);
            updateStatusDisplay();
            updateButtonStates();
        });

        ActionListener modeListener = e -> {
            automaticMode = automaticRadio.isSelected();
            updateButtonStates();
        };
        automaticRadio.addActionListener(modeListener);
        manualRadio.addActionListener(modeListener);

        speedComboBox.addActionListener(e -> {
            String selected = (String) speedComboBox.getSelectedItem();
            if (selected != null) {
                delayMs = Integer.parseInt(selected.replaceAll("[^0-9]", ""));
                timer.setDelay(delayMs);
            }
        });
    }

    private class SignalPanel extends JPanel {
        
        public SignalPanel() {
            setBackground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.setColor(new Color(40, 40, 40));
            g2d.fillRect(30, 40, 90, 260);

            g2d.setColor(currentState.equals("RED") ? Color.RED : new Color(80, 0, 0));
            g2d.fillOval(45, 55, 60, 60);

            g2d.setColor(currentState.equals("YELLOW") ? Color.YELLOW : new Color(80, 80, 0));
            g2d.fillOval(45, 140, 60, 60);

            g2d.setColor(currentState.equals("GREEN") ? Color.GREEN : new Color(0, 80, 0));
            g2d.fillOval(45, 225, 60, 60);

            g2d.setColor(new Color(240, 242, 245));
            g2d.fillRect(150, 40, 260, 160);
            
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            g2d.setColor(Color.DARK_GRAY);
            g2d.drawString("Pedestrian Signal", 170, 70);

            g2d.setColor(Color.BLACK);
            g2d.fillRect(170, 90, 220, 80);

            String pedText;
            Color pedColor;
            if (currentState.equals("RED")) {
                pedText = "WALK";
                pedColor = Color.GREEN;
            } else {
                pedText = "WAIT";
                pedColor = Color.RED;
            }

            g2d.setFont(new Font("Arial", Font.BOLD, 32));
            g2d.setColor(pedColor);
            g2d.drawString(pedText, 190, 142);

            g2d.setFont(new Font("Consolas", Font.BOLD, 36));
            g2d.setColor(pedColor);
            String countStr = String.format("%02d", countdown);
            g2d.drawString(countStr, 310, 145);

            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            g2d.setColor(Color.GRAY);
            g2d.drawString("Automatic cycle: RED -> GREEN -> YELLOW", 150, 250);
            g2d.drawString("Timer updates state and countdown", 150, 270);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TrafficLightSimulator().setVisible(true);
        });
    }
}