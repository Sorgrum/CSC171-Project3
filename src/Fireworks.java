import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Arc2D;

/**
 * Student Name: Marcelo Gheiler
 * Filename: Fireworks
 * Date: 10/28/15
 * TA Name: Colin Pronovost
 * Assignment: 12
 * Lab Day: Monday
 * Lab Time: 5PM
 * Lab Location: CSB 703
 * I affirm that I have not given or received any unauthorized help on this assignment, and that this work is my own
 */

public class Fireworks extends JFrame implements ActionListener, ChangeListener, ItemListener {

	private JPanel panel;
	private JPanel drawing;
	private JPanel controls;
	private JPanel speedGroup;
	private JPanel angleGroup;
    private JPanel colorGroup;
	private JPanel timeToExplodeGroup;
    private JPanel typeOfExplosionGroup;

	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem resetMenuItem;

	private JTextField speedField;
	private JLabel speedLabel;

	private JSlider angleField;
	private JLabel angleLabel;

    private JComboBox<String> colorPicker;
    private JLabel colorPickerLabel;
    private String[] colorChoices = {"White", "Red", "Blue", "Green", "Cyan", "Magenta", "Orange", "Pink", "Yellow"};

    private JComboBox<String> typeOfExplosion;
    private JLabel typeOfExplosionLabel;
    private String[] explosionChoices = {"One", "Two"};

	private JTextField timeToExplode;
	private JLabel timeToExplodeLabel;

    private JLabel calculatedTimeToTouchdown;

    /*
     * Default values for fields
     */
    private double DEFAULT_SPEEDFIELD = 25;
    private int DEFAULT_ANGLE = 45;
    private double DEFAULT_TIME_TO_EXPLODE = 2.5;

    // Initial and final velocity are both in the Y direction because the velocity in the x direction doesn't change.
    private double initialVelocity = DEFAULT_SPEEDFIELD * Math.sin(Math.toRadians(DEFAULT_ANGLE));
    private double finalVelocity;
    private double horizontalVelocity;

    private double maximumWidth;
    private double maximumHeight;
    private double GRAVITY = 9.81;
    private double angle;
    private double time;

	public Fireworks() {

		/*
		 * Set the dimensions of the window to the size of the inhabitable screen. Basically it the window will take
		 * up as much screen space as is available without covering things.
		 */
		double screenWidth = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getWidth();
		double screenHeight = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight();

		this.setSize((int) screenWidth / 2, (int) screenHeight / 2);
        this.setMinimumSize(new Dimension(610, 350));


		panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();

		/*
		 * First, create the menu
		 */
		menuBar = new JMenuBar();
		menu = new JMenu("File");
		menuBar.add(menu);

		resetMenuItem = new JMenuItem("Reset");
		resetMenuItem.addActionListener(this);
		menu.add(resetMenuItem);


		/*
		 * Group together the three controls, speed, angle, and time.
		 */
		speedGroup = new JPanel(new FlowLayout(FlowLayout.LEFT));
		angleGroup = new JPanel(new FlowLayout(FlowLayout.LEFT));
        colorGroup = new JPanel(new FlowLayout(FlowLayout.LEFT));
		timeToExplodeGroup = new JPanel(new FlowLayout(FlowLayout.LEFT));
        typeOfExplosionGroup = new JPanel(new FlowLayout(FlowLayout.LEFT));

        controls = new JPanel(new GridBagLayout());

		speedLabel = new JLabel("Launch Speed: ");
		speedField = new JTextField(Double.toString(DEFAULT_SPEEDFIELD) + " m/s", 5);
		speedField.addActionListener(this);
		speedGroup.add(speedLabel);
		speedGroup.add(speedField);


		angleField = new JSlider(SwingConstants.HORIZONTAL, 1, 89, DEFAULT_ANGLE);
		angleField.addChangeListener(this);
		angleLabel = new JLabel("Launch Angle: " + DEFAULT_ANGLE + "˚");
		angleGroup.add(angleLabel);
		angleGroup.add(angleField);

		timeToExplode = new JTextField(DEFAULT_TIME_TO_EXPLODE + " s", 4);
		timeToExplode.addActionListener(this);
		timeToExplodeLabel = new JLabel("Time to Explosion: ");
		timeToExplodeGroup.add(timeToExplodeLabel);
		timeToExplodeGroup.add(timeToExplode);

        colorPicker = new JComboBox(colorChoices);
        colorPicker.addActionListener(this);
        colorPickerLabel = new JLabel("Color");
        colorGroup.add(colorPickerLabel);
        colorGroup.add(colorPicker);

        typeOfExplosion = new JComboBox(explosionChoices);
        typeOfExplosion.addActionListener(this);
        typeOfExplosionLabel = new JLabel("Type of Explosion");
        typeOfExplosionGroup.add(timeToExplodeLabel);
        typeOfExplosionGroup.add(typeOfExplosion);


		constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets((int) screenHeight/50 , 5, 10, 0);

		constraints.gridx = 0;
		constraints.gridy = 0;
		controls.add(speedGroup, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		controls.add(angleGroup, constraints);

		constraints.gridx = 0;
		constraints.gridy = 2;
		controls.add(timeToExplodeGroup, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        controls.add(colorGroup, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        controls.add(typeOfExplosionGroup, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        calculatedTimeToTouchdown = new JLabel("");
        controls.add(calculatedTimeToTouchdown, constraints);


		// Add the controls to the main panel
        constraints.insets = new Insets(0, 0, 0, 0);
		constraints.gridx = 0;
		constraints.gridy = 0;

        // By adding the controls to the left hand side, smaller screens and a resized window doesn't really matter
        // as much because the controls will almost always be visible and the only thing that changes it the size of
        // the illustration.
		panel.add(controls, constraints);

        updateFields();
		drawing = new GraphPanel();
		drawing.setBackground(Color.WHITE);
        drawing.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.GRAY));

		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.weightx = .5;
		constraints.weighty = 1;
		constraints.gridheight = 10;

		panel.add(drawing, constraints);

        panel.setSize(300, 300);


		//panel.add(toolBar, BorderLayout.PAGE_START);
		this.add(panel);
		this.setJMenuBar(menuBar);
		this.validate();

		this.setTitle("Fireworks Pro");

    }


    public void updateFields() {

        if (angleField.getValue() < 10 && angleField.getValue() > 0) {
            angleLabel.setText("Launch Angle: 0" + Integer.toString(angleField.getValue()) + "˚");
        } else {
            angleLabel.setText("Launch Angle: " + Integer.toString(angleField.getValue()) + "˚");
        }
        angle = Double.parseDouble(Integer.toString(angleField.getValue()));

        initialVelocity = Double.parseDouble(speedField.getText().replaceAll("[^0-9.]", "")) * Math.sin(Math
                .toRadians(angle));
        horizontalVelocity = Double.parseDouble(speedField.getText().replaceAll("[^0-9.]", "")) * Math.cos(Math
                .toRadians(angle));

        speedField.setText(speedField.getText().replaceAll("[^0-9.]", "") + " m/s");

        time = Double.parseDouble(timeToExplode.getText().replaceAll("[^0-9.]", ""));
        timeToExplode.setText(timeToExplode.getText().replaceAll("[^0-9.]", "") + " s");



        /*
         * Calculate some variables that are needed to graph the projectile
         */

        // This is true because the starting height and ending height are the same.
        finalVelocity = -initialVelocity;
        time = (finalVelocity - initialVelocity) / -GRAVITY;
        maximumHeight = (initialVelocity * (time / 2)) + (0.5 * -GRAVITY * (time/2)*(time/2));
        maximumWidth = Double.parseDouble(speedField.getText().replaceAll("[^0-9.]", "")) * Math.cos(Math
                .toRadians(angle)) * time;
        calculatedTimeToTouchdown.setText("Time for projectile to hit floor: " + time + " s");
        repaint();
    }

    public static void main(String[] args) {
		Fireworks app = new Fireworks();
		app.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		app.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == speedField) {
            updateFields();
		}

        if (arg0.getSource() == timeToExplode) {
            updateFields();
        }

		if (arg0.getSource() == resetMenuItem) {
            speedField.setText(DEFAULT_SPEEDFIELD + " m/s");
            angleField.setValue(DEFAULT_ANGLE);
            timeToExplode.setText(DEFAULT_TIME_TO_EXPLODE + " s");
		}

        if (arg0.getSource() == colorPicker) {
            switch ((String)colorPicker.getSelectedItem()) {
                case "White":
                    getGraphics().setColor(Color.WHITE);
                    break;
                case "Red":
                    getGraphics().setColor(Color.RED);
                    break;
                case "Blue":
                    getGraphics().setColor(Color.BLUE);
                    break;
                case "Green":
                    getGraphics().setColor(Color.GREEN);
                    break;
                case "Cyan":
                    getGraphics().setColor(Color.CYAN);
                    break;
                case "Magenta":
                    getGraphics().setColor(Color.MAGENTA);
                    break;
                case "Orange":
                    getGraphics().setColor(Color.ORANGE);
                    break;
                case "Pink":
                    getGraphics().setColor(Color.PINK);
                    break;
                case "Yellow":
                    getGraphics().setColor(Color.YELLOW);
                    break;

            }
        }
	}

	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == angleField) {
            updateFields();
		}
	}

	public void itemStateChanged(ItemEvent itemEvent) {
        System.out.println("Item State Changed " + itemEvent);
    }

    public class GraphPanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Offset for padding
            // I'm using a hard value here because I need a minimum of 30px. It should look fine on most screens.
            int offset = 30;

            if (getWidth() > getHeight()) {
                // Draw a square for the general drawing area
                g.drawRect(0, 0, getHeight(), getHeight());

                // Another rectangle for the actual graph
                g.drawRect(offset, offset, getHeight() - 2 * offset, getHeight() - 2 * offset);

                // Draw the ticks for the Y-axis
                for (double i = (getHeight() - offset); i > offset; i-= (getHeight() - getHeight()/50)/maximumHeight) {
                    g.drawLine(
                            offset - 2,
                            (int) i,
                            offset + 2,
                            (int) i
                    );
                }

                // Draw the ticks for the X-axis
                for (double i = offset; i <= (getHeight() - offset); i += (getHeight() - getHeight()/50)/maximumWidth) {
                    g.drawLine(
                            (int) i,
                            (getHeight()) - 1 - offset,
                            (int) i,
                            (getHeight()) + 1 - offset
                    );
                }

                /*
                 * Draw the scale
                 */
                char[] scaleXMax = Double.toString(calculateX(time)).toCharArray();
                char[] scaleYMax = Double.toString(calculateY(time/2)).toCharArray();

                // I don't think there exists a computer monitor that has enough pixels to make this not work
                g.drawChars(scaleXMax, 0, 5, getHeight() - 2 * offset, getHeight() - (offset / 2));
                g.drawChars(scaleYMax, 0, 4, 3, ((getHeight() - 2 * offset) / 2) + 10);

                /*
                 * Calculate the ratio's
                 */

                double ratioX = 0;
                double ratioY = 0;
                double tmpRatioX;
                double tmpRatioY;

                while (ratioX == 0) {

                    // double tmpRatioX = (getHeight() - offset) / calculateX(time);
                    tmpRatioX = 1000;
                    while (calculateX(time) * tmpRatioX > getHeight() - 2 * offset) {
                        tmpRatioX /= 1.3;
                    }
                    ratioX = tmpRatioX;
                }

                while (ratioY == 0) {

                    // double tmpRatioX = (getHeight() - offset) / calculateX(time);
                    tmpRatioY = 1000;
                    while (calculateY(time/2) * tmpRatioY > getHeight() - 2 * offset) {
                        tmpRatioY /= 1.3;
                    }
                    ratioY = tmpRatioY;
                }


                /*
                 * Draw the parabola
                 */
                for (double i = 0; i < time; i += time/50) {

                    g.drawLine((int) (calculateX(i) * ratioX + offset),
                                (int) (getHeight() - (calculateY(i) * ratioY + offset)),
                                (int) (calculateX(i + time/50) * ratioX + offset),
                                (int) (getHeight() - ((calculateY(i + time/50) * ratioY) + offset)));

                }

                /*
                 * Draw a marker where the explosion is
                 */
                int explosionLocationX = (int) calculateX(Double.parseDouble(timeToExplode.getText().replaceAll("[^0-9.]", "")));
                int explosionLocationY = (int) calculateY(Double.parseDouble(timeToExplode.getText().replaceAll("[^0-9.]", "")));

                for (int i = 1; i < 50; i*=2) {
                    g.drawOval(
                            (int) (calculateX( + time/50) * ratioX + offset) - (i/2),
                            (int) (getHeight() - ((calculateY(Double.parseDouble(timeToExplode.getText().replaceAll("[^0-9.]", "")) + time/50) * ratioY) + offset)) - (i/2),
                            i,
                            i
                    );
                }

            } else {
                g.drawRect(0, 0, getWidth(), getWidth());
            }
        }

    }
    public double calculateX(double time) {
        return horizontalVelocity * time;
    }

    public double calculateY(double time) {
        return (initialVelocity * (time)) + (0.5 * -GRAVITY * (time)*(time));
    }
}