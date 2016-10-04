/*
 *Daniel Cancelmo
 *Project 4 -- Videogame
 *Lab: Tues. & Thurs. 12:30-1:45
 *TA: Patrick Conway
 *I did not collaborate with anyone on this assignment.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PongGUI extends JFrame implements ActionListener {
	
	//GUI Instance variables.
	protected JButton playButton;
	protected JLabel gameIntro, ballColorLabel, paddleColorLabel;
	@SuppressWarnings("rawtypes")
	protected JComboBox ballColorBox;
	@SuppressWarnings("rawtypes")
	protected JComboBox paddleColorBox;
	
	//Instance variables to be passed into the game.
	protected Color ballColor = Color.BLACK;
	protected Color paddleColor = Color.BLACK;

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PongGUI() {
		//Sets the behavior of the frame.
		setLayout(new BorderLayout());
		setTitle("Lob Pong Launcher");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel subpanelIntro = new JPanel();
		gameIntro = new JLabel("Welcome to Lob Pong. Select your desried colors and press play to start. Don't let the ball go below the paddle! Use the arrow keys or 'WASD' to move the paddle.");
		subpanelIntro.add(gameIntro);
		add(subpanelIntro, BorderLayout.NORTH);
		
		JPanel subpanel = new JPanel();
		
		ballColorLabel = new JLabel("Select the desired ball color: ");
		subpanel.add(ballColorLabel);
		
		String[] colors = {"Black", "Blue", "Cyan", "Red", "Orange", "Green", "Magenta", "Pink"};
		ballColorBox = new JComboBox(colors);
		ballColorBox.addActionListener(this);
		subpanel.add(ballColorBox);
		
		paddleColorLabel = new JLabel("Select the desired paddle color: ");
		subpanel.add(paddleColorLabel);
		
		paddleColorBox = new JComboBox(colors);
		paddleColorBox.addActionListener(this);
		subpanel.add(paddleColorBox);
		
		playButton = new JButton("Play!");
		playButton.addActionListener(this);
		subpanel.add(playButton);
		
		add(subpanel, BorderLayout.CENTER);
		
		pack();
	}
	
	
	//Main method. Creates instance of PongGUI and sets visible.
	public static void main(String[] args) {
		new PongGUI().setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("PongGUI.actionPerformed");
		Object source = e.getSource();
		//If-else-if identifies source
		if (source == playButton) {
			System.out.println("Let's play!");
			new PongGame(ballColor, paddleColor).setVisible(true);;
		} else if (source == ballColorBox) {
			//Converts the color to a string.
			String colorString = (String) ballColorBox.getSelectedItem();
			//Which object's color will change. To make the console print more informative.
			String colorObject = "The ball's";
			ballColor = colorSelect(colorObject, colorString);
		} else if (source == paddleColorBox) {
			//Converts the color to a string.
			String colorString = (String) paddleColorBox.getSelectedItem();
			//Which object's color will change. To make the console print more informative.
			String colorObject = "The paddle's";
			paddleColor = colorSelect(colorObject, colorString);
		}
	}
	
	//Method to change the color of an object. Either the ball or the paddle.
	public Color colorSelect(String colorObject, String colorString) {
		Color color = Color.BLACK;
		//Identifies the color and changes the selected color to the user's choice.
		switch (colorString) {
			case "Black":
				color = Color.BLACK;
				break;
			case "Blue":
				color = Color.BLUE;
				break;
			case "Cyan":
				color = Color.CYAN;
				break;
			case "Red":
				color = Color.RED;
				break;
			case "Orange":
				color = Color.ORANGE;
				break;
			case "Green":
				color = Color.GREEN;
				break;
			case "Magenta":
				color = Color.MAGENTA;
				break;
			case "Pink":
				color = Color.PINK;
				break;
		}
		System.out.println(colorObject + " color changed to " + colorString + ".");
		return color;
	}
	
}
