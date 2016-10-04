/*
 *Daniel Cancelmo
 *Project 4 -- Videogame
 *Lab: Tues. & Thurs. 12:30-1:45
 *TA: Patrick Conway
 *I did not collaborate with anyone on this assignment.
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


@SuppressWarnings("serial")
public class PongGame extends JFrame {
	
	protected Color ballColor;
	protected Color paddleColor;
	
	public PongGame(Color ballColor, Color paddleColor) {
		this.ballColor = ballColor; 
		this.paddleColor = paddleColor;
		System.out.println("Game started!");
		System.out.println("Ball color: " + ballColor);
		System.out.println("Paddle color: " + paddleColor);
		setTitle("Lob Pong");
		setSize(640, 480);
        setResizable(false);
		//Calls the Canvas class nested in PongGame and adds it to the frame.
		Canvas canvas = new Canvas();
		add(canvas);
		canvas.setVisible(true);		
	}
	
	//Nested class. Creates the canvas the game will draw on.
	public class Canvas extends JPanel implements KeyListener {	
		
		protected int paddleMovement = 0;
		protected Timer timer;
		protected int paddleWidth = 90;
		protected int paddleHeight = 20;
		protected int paddleLocationX;
		protected int paddleLocationY;
		protected int ballWidth = 20;
		protected int ballHeight = 20;
		protected int ballLocationX;
		protected int ballLocationY;
		protected double ballAngleRad;
		protected int ballSpeed = 10;
		protected int ballSpeedX = 10;
		protected int ballSpeedY = -10;
		protected int yDirection = 1;
		protected int xDirection = 1;
		protected int lives = 3;
		protected int timeRemaining = 5;
		long levelTime = System.currentTimeMillis() + (timeRemaining * 1000);
		protected int level = 1;
		protected JLabel livesLabel;
		protected JLabel timeLabel;
		protected JLabel levelLabel;
		protected double t = 0;
		protected Random rand = new Random();
		
		public Canvas() {
			addKeyListener(this);
			livesLabel = new JLabel("Lives remaining: " + lives);
			add(livesLabel);
			timeLabel = new JLabel("Time remaining: " + timeRemaining);
			add(timeLabel);
			levelLabel = new JLabel("Level: " + level);
			add(levelLabel);
			timer = new Timer(10, new TimerCallback()); //1000ms = 1 sec
			timer.start();
			
			//Randomly selects the initial angle
			int angleSelection = rand.nextInt(2);
			if (angleSelection == 1) {
				ballAngleRad = (4 * Math.PI / 3);
			} else {
				ballAngleRad = (5 * Math.PI / 3);
			}
			
			
			//ballAngleRad = rand.nextInt(135) + 25;
			//ballAngleRad = Math.toRadians(ballAngleRad);
			
			
//Does not resize. But it does not need to.
			paddleLocationX = 640/2 - paddleWidth/2;
			paddleLocationY = 458 - 20 - paddleHeight/2;
			ballLocationX = 640/2 - ballWidth/2;
			ballLocationY = 458/2 - ballHeight/2;
			//ballLocationX = paddleLocationX + paddleHeight/2 + ballWidth/2;
			//ballLocationY = paddleLocationY - paddleHeight;
		}
		
		@Override
		public void paintComponent(Graphics img) {
			System.out.println("PongGame.paintComponent");
			img.setColor(paddleColor);
			img.fillRect(paddleLocationX, paddleLocationY, paddleWidth, paddleHeight);
			img.setColor(ballColor);
			img.fillOval(ballLocationX, ballLocationY, ballWidth, ballHeight);
			//If statement ends the game
			if (lives <= 0) {
				timer.stop();
				System.out.println("Game Over!");
				img.setFont(new Font("TimesRoman", Font.PLAIN, 50)); 
				int stringWidth = img.getFontMetrics().stringWidth("Game Over!");
				img.setColor(Color.RED);
				img.drawString("Game Over!", getWidth()/2 - stringWidth/2, getHeight()/2);
				timeLabel.setText("Time remaining: 0");
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			//Ignore.
		}

		@Override
		public void keyPressed(KeyEvent e) {
			System.out.println("PongGame.Canvas.keyPressed");
			System.out.println(e);
			int directionKey = e.getKeyCode();
			System.out.println(directionKey);
			//If-else statement determines which key was pressed.
			if (directionKey == 39 || directionKey == 68) { //Right arrow key
				System.out.println("Move right.");
				paddleMovement = 10;
				repaint();
			} else if (directionKey == 37 || directionKey == 65) { //Left arrow key
				System.out.println("Move left.");
				paddleMovement = -10;
				repaint();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			System.out.println("PongGame.Canvas.keyReleased");
			System.out.println("Stop moving.");
			paddleMovement = 0;
			repaint();
		}
		
		//Allows the key listener to display the input on the screen by focusing on the keyboard.
		public void addNotify() {
			super.addNotify();
			requestFocus();
		}
		
		//Nested class. Executes when timer is fired.
		protected class TimerCallback implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				//Prevents paddle from leaving the right side of the window.
				if (paddleLocationX > getWidth() - paddleWidth) {
					paddleLocationX = getWidth() - paddleWidth;
					paddleMovement = 0;
				}
				//Prevents paddle from leaving the left side of the window.
				if (paddleLocationX < 0) {
					paddleLocationX = 0;
					paddleMovement = 0;
				}
				
				//Moves the paddle.
				paddleLocationX += paddleMovement;
				
				//Deflects ball off of paddle.
				if (ballLocationX + ballWidth/2 >= paddleLocationX && ballLocationX + ballWidth/2 <= paddleLocationX + paddleWidth ) {
					if (ballLocationY + ballHeight >= paddleLocationY) {
						ballLocationY = paddleLocationY - ballHeight;
						yDirection *= -1;
					}
				}
				//Stops ball from going out the top.
				if (ballLocationY <= 0) {
					yDirection *= -1;
					//Prevents ball from speeding up when it hits the top.
					t = 0.5;
				}
				
				//Stops ball from going out the left.
				if (ballLocationX <= 0) {
					xDirection *= -1;

				}
				
				//Stops ball from going out the right.
				if (ballLocationX >= getWidth() - ballWidth) {
					xDirection *= -1;
				}
				
				
				//Moves the ball.
				t += 0.005;
				//ballSpeedX = (int) (ballSpeed*Math.cos(ballAngleRad));
				//ballSpeedY = (int) (ballSpeed*Math.sin(ballAngleRad) - 9.8*t);
				//ballSpeed = (int) Math.sqrt(Math.pow(ballSpeedX, 2)+ Math.pow(ballSpeedY, 2));
				ballLocationX += xDirection * ballSpeed * Math.cos(ballAngleRad)*t;
				ballLocationY -= yDirection * ballSpeed * Math.sin(ballAngleRad)*t - 0.5 * 9.8*Math.pow(t, 2);
				
				
				//Updates the timeLabel
				if(System.currentTimeMillis() < levelTime) {
					if (lives >= 0) {
						timeLabel.setText("Remaining time: " + (levelTime - System.currentTimeMillis()) / 1000 );
					}
				}
				
				//If the user keeps the ball in the air until the levelTime reaches zero
				if (((levelTime-System.currentTimeMillis())/1000)==0){
					level++;
					timeRemaining += 5;
					levelTime = System.currentTimeMillis() + (timeRemaining * 1000);
					timeLabel.setText("Remaining time: " + (levelTime - System.currentTimeMillis()) / 1000 );
					levelLabel.setText("Level: " + level);
				}
				
				//When ball falls out of screen. Ends a round and resets the game for a new round.
				if (ballLocationY > getHeight()) {
					timer.stop();
					System.out.println("Round Over.");
					lives -= 1;
					System.out.println("Lives remaining: " + lives);
					livesLabel.setText("Lives remaining: " + lives);
					//dispose();
					paddleLocationX = 640/2 - paddleWidth/2;
					ballLocationX = 640/2 - ballWidth/2;
					ballLocationY = 458/2 - ballHeight/2;
					ballSpeed = 10;
					t = 0;
					levelTime = System.currentTimeMillis() + (timeRemaining * 1000);

					timer.start();
				}
				
				repaint();
			}
		}
	
	}
	
}