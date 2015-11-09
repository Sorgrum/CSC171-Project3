import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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
	private JPanel timeToExplodeGroup;

	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem resetMenuItem;

	private JTextField speed;
	private JLabel speedLabel;

	private JSlider angle;
	private JLabel angleLabel;

	private JTextField timeToExplode;
	private JLabel timeToExplodeLabel;

	public Fireworks() {

		/*
		 * Set the dimensions of the window to the size of the inhabitable screen. Basically it the window will take
		 * up as much screen space as is available without covering things.
		 */
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double screenWidth = screenSize.getWidth();
		double screenHeight = screenSize.getHeight();
		this.setSize((int) screenWidth, (int) screenHeight);


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
		timeToExplodeGroup = new JPanel(new FlowLayout(FlowLayout.LEFT));

		controls = new JPanel(new GridBagLayout());

		speedLabel = new JLabel("Launch Speed: ");
		speed = new JTextField("25 m/s", 5);
		speed.addActionListener(this);
		speedGroup.add(speedLabel);
		speedGroup.add(speed);


		angle = new JSlider(SwingConstants.HORIZONTAL, 1, 89, 45);
		angle.addChangeListener(this);
		angleLabel = new JLabel("Launch Angle: " + Integer.toString(angle.getValue()) + "˚");
		angleGroup.add(angleLabel);
		angleGroup.add(angle);

		timeToExplode = new JTextField("10s", 4);
		timeToExplode.addActionListener(this);
		timeToExplodeLabel = new JLabel("Time to Explosion: ");
		timeToExplodeGroup.add(timeToExplodeLabel);
		timeToExplodeGroup.add(timeToExplode);

		constraints.fill = GridBagConstraints.BOTH;

		constraints.gridx = 0;
		constraints.gridy = 0;
		controls.add(speedGroup, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		controls.add(angleGroup, constraints);

		constraints.gridx = 0;
		constraints.gridy = 2;
		controls.add(timeToExplodeGroup, constraints);
		controls.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));

		// Add the controls to the main panel
		constraints.gridx = 0;
		constraints.gridy = 0;
		panel.add(controls, constraints);


		drawing = new JPanel();
		drawing.setSize(200, 300);
		drawing.setBackground(Color.WHITE);

		System.out.println(drawing.getMaximumSize());
		System.out.println(drawing.getMinimumSize());
		System.out.println(drawing.getPreferredSize());

		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.weightx = .5;
		constraints.weighty = 1;
		constraints.gridheight = 10;

		panel.add(drawing, constraints);



		//panel.add(toolBar, BorderLayout.PAGE_START);
		this.add(panel);
		//this.setJMenuBar(menuBar);
		this.validate();
		this.pack();
		this.setTitle("Fireworks Pro");
	}




	public static void main(String[] args) {
		Fireworks app = new Fireworks();
		app.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		app.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == speed) {
			// TODO Add regex to change the speed to only the numeric value (drop the units)
			System.out.println(speed.getText());
			System.out.println(speed.getText().replaceAll("[^0-9]", ""));
		}

		if (arg0.getSource() == resetMenuItem) {
			// TODO Have fields reset to originals
		}
	}

	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == angle) {
			angleLabel.setText("Launch Angle: " + Integer.toString(angle.getValue()) + "˚");
		}
	}

	public void itemStateChanged(ItemEvent itemEvent) {

	}
}