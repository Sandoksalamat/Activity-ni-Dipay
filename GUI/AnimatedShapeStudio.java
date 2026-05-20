import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AnimatedShapeStudio extends JFrame {

    private ShapeCanvas canvas;
    private JPanel controlPanel;
    private JRadioButton circleRadio;
    private JRadioButton rectRadio;
    private JRadioButton ovalRadio;
    private ButtonGroup shapeGroup;
    private JComboBox<String> colorComboBox;
    private JCheckBox blinkCheckBox;
    private JButton startButton;
    private JButton stopButton;
    private JButton clearButton;
    private JLabel statusLabel;

    private String selectedShape = "Circle";
    private Color selectedColor = Color.BLUE;

    private final int MAX_SHAPES = 500;
    private int[] shapeX = new int[MAX_SHAPES];
    private int[] shapeY = new int[MAX_SHAPES];
    private String[] shapeType = new String[MAX_SHAPES];
    private Color[] shapeColor = new Color[MAX_SHAPES];
    private int shapeCount = 0;

    private boolean previewVisible = true;
    private Timer timer;

    public AnimatedShapeStudio() {

        setTitle("Animated Shape Studio");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));

        statusLabel = new JLabel("Shape: Circle | Color: Blue | Animation: Stopped");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 10, 15));

        buildControls();
        canvas = new ShapeCanvas();

        add(controlPanel, BorderLayout.WEST);
        add(canvas, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        timer = new Timer(450, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (blinkCheckBox.isSelected()) {
                    previewVisible = !previewVisible;
                    canvas.repaint();
                }
            }
        });
    }

    private void buildControls() {

        controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        controlPanel.setPreferredSize(new Dimension(220, 600));

        JPanel shapeSection = new JPanel(new GridLayout(4, 1, 5, 5));
        shapeSection.setBorder(BorderFactory.createTitledBorder("Shape"));
        
        circleRadio = new JRadioButton("Circle", true);
        rectRadio = new JRadioButton("Rectangle");
        ovalRadio = new JRadioButton("Oval");
        
        shapeGroup = new ButtonGroup();
        shapeGroup.add(circleRadio);
        shapeGroup.add(rectRadio);
        shapeGroup.add(ovalRadio);

        ActionListener shapeListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (circleRadio.isSelected()) selectedShape = "Circle";
                else if (rectRadio.isSelected()) selectedShape = "Rectangle";
                else if (ovalRadio.isSelected()) selectedShape = "Oval";
                updateStatusLabelText();
                canvas.repaint();
            }
        };

        circleRadio.addActionListener(shapeListener);
        rectRadio.addActionListener(shapeListener);
        ovalRadio.addActionListener(shapeListener);

        shapeSection.add(circleRadio);
        shapeSection.add(rectRadio);
        shapeSection.add(ovalRadio);

        JPanel colorSection = new JPanel(new FlowLayout(FlowLayout.LEFT));
        colorSection.setBorder(BorderFactory.createTitledBorder("Color"));
        
        String[] colors = {"Blue", "Red", "Green", "Orange"};
        colorComboBox = new JComboBox<>(colors);
        colorComboBox.setPreferredSize(new Dimension(160, 30));
        
        colorComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choice = (String) colorComboBox.getSelectedItem();
                switch (choice) {
                    case "Blue": selectedColor = Color.BLUE; break;
                    case "Red": selectedColor = Color.RED; break;
                    case "Green": selectedColor = new Color(0, 128, 128); break; 
                    case "Orange": selectedColor = Color.ORANGE; break;
                }
                updateStatusLabelText();
                canvas.repaint();
            }
        });
        colorSection.add(colorComboBox);

        JPanel animSection = new JPanel(new GridLayout(4, 1, 8, 8));
        animSection.setBorder(BorderFactory.createTitledBorder("Animation"));

        blinkCheckBox = new JCheckBox("Blink Preview", true);
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        clearButton = new JButton("Clear");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.start();
                updateStatusLabelText();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                previewVisible = true;
                updateStatusLabelText();
                canvas.repaint();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shapeCount = 0;
                updateStatusLabelText();
                canvas.repaint();
            }
        });

        animSection.add(blinkCheckBox);
        animSection.add(startButton);
        animSection.add(stopButton);
        animSection.add(clearButton);

        controlPanel.add(shapeSection);
        controlPanel.add(Box.createVerticalStrut(15));
        controlPanel.add(colorSection);
        controlPanel.add(Box.createVerticalStrut(15));
        controlPanel.add(animSection);
        controlPanel.add(Box.createVerticalGlue());
    }

    private void updateStatusLabelText() {
        String animStatus = timer.isRunning() ? "Running" : "Stopped";
        statusLabel.setText("Shape: " + selectedShape + " | Color: " + 
                colorComboBox.getSelectedItem() + " | Animation: " + animStatus);
    }

    private class ShapeCanvas extends JPanel {
        
        public ShapeCanvas() {
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(new Color(200, 210, 220), 2, true));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (shapeCount < MAX_SHAPES) {
                        shapeX[shapeCount] = e.getX();
                        shapeY[shapeCount] = e.getY();
                        shapeType[shapeCount] = selectedShape;
                        shapeColor[shapeCount] = selectedColor;
                        shapeCount++;
                        
                        repaint();
                    }
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(Color.DARK_GRAY);
            g2.setFont(new Font("SansSerif", Font.BOLD, 14));
            g2.drawString("Canvas: click to place the selected shape", 20, 30);
            
            g2.setFont(new Font("SansSerif", Font.PLAIN, 12));
            g2.drawString("Mouse click -> x,y -> repaint()", 20, getHeight() - 20);

            for (int i = 0; i < shapeCount; i++) {
                g2.setColor(shapeColor[i]);
                drawTargetShape(g2, shapeType[i], shapeX[i], shapeY[i], false);
            }

            if (previewVisible) {
                g2.setColor(Color.LIGHT_GRAY);
                g2.setStroke(new BasicStroke(3.0f));
                
                int previewX = getWidth() / 2;
                int previewY = getHeight() - 120;
                
                drawTargetShape(g2, selectedShape, previewX, previewY, true);
                
                g2.setColor(Color.GRAY);
                g2.drawString("blinking preview", previewX - 40, previewY + 55);
            }
        }

        private void drawTargetShape(Graphics2D g2, String type, int x, int y, boolean isPreview) {
            int size = 80;
            int rectWidth = 110;
            int rectHeight = 70;
            
            int centeredX = x - (type.equals("Rectangle") ? rectWidth / 2 : size / 2);
            int centeredY = y - (type.equals("Rectangle") ? rectHeight / 2 : size / 2);

            switch (type) {
                case "Circle":
                    if (isPreview) g2.drawOval(centeredX, centeredY, size, size);
                    else g2.fillOval(centeredX, centeredY, size, size);
                    break;
                case "Rectangle":
                    if (isPreview) g2.drawRect(centeredX, centeredY, rectWidth, rectHeight);
                    else g2.fillRect(centeredX, centeredY, rectWidth, rectHeight);
                    break;
                case "Oval":
                    if (isPreview) g2.drawOval(centeredX, centeredY, rectWidth, rectHeight);
                    else g2.fillOval(centeredX, centeredY, rectWidth, rectHeight);
                    break;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AnimatedShapeStudio().setVisible(true);
            }
        });
    }
}