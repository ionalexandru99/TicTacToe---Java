import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

public class GameWindow extends JFrame{

	private char whosTurn = 'X';
	private String Player1_;
	private String Player2_;
	private JButton exit_button;
	private JButton reset_button;
	private JPanel game;
	private JPanel buttons;
	private JLabel score;
	private JLabel Player1;
	private JLabel Player2;
	private JLabel emptyspace;
	private Cell[][] cells = new Cell[3][3];
	private static int p1win = 0;
	private static int p2win = 0;
	private boolean press = true;
	private BufferedImage x_png; 
	private BufferedImage o_png;
	
	public GameWindow(String player1, String player2) {
		
		super("X & 0");
		this.setSize(600,800);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//this.setBackground(Color.MAGENTA);
		try {
			x_png = ImageIO.read(getClass().getResourceAsStream("x.png"));
			o_png = ImageIO.read(getClass().getResourceAsStream("o.png"));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		buttons = new JPanel(new GridLayout(1,2,0,0));
		
	
		reset_button = new JButton("PLAY AGAIN");
		buttons.add(reset_button);
		
		exit_button = new JButton("RETURN TO MAIN MENIU");
		buttons.add(exit_button);
		
		add(buttons, BorderLayout.SOUTH);
		
		
		exit_button.addActionListener(new HandlerClass());
		reset_button.addActionListener(new HandlerClass());
		
		
		Player1 = new JLabel(player1);
		Player2 = new JLabel(player2);
		Player1_ = player1;
		Player2_ = player2;
		score = new JLabel(p1win  + ":" + p2win);
		emptyspace = new JLabel(" ");
		
		Player1.setFont(new Font("Serif", Font.BOLD, 56));
		Player2.setFont(new Font("Serif", Font.BOLD, 56));
		score.setFont(new Font("Serif", Font.BOLD, 56));
		
		
		Player1.setForeground(Color.BLUE);

		game = new JPanel(new GridLayout(6,3,0,0));
		game.setBackground(Color.WHITE);
		game.setAlignmentX(CENTER_ALIGNMENT);
		game.setAlignmentY(CENTER_ALIGNMENT);
		game.add(Player1);
		game.add(score);
		game.add(Player2);
		game.add(emptyspace);
		game.add(emptyspace);
		game.add(emptyspace);
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++)
				game.add(cells[i][j] = new Cell());
		game.add(emptyspace);
		game.add(emptyspace);
		game.add(emptyspace);
		
		
		add(game, BorderLayout.CENTER);

	} // ends GameWindow

	private boolean isFull() {
		for(int row = 0; row < 3; row++)
			for(int column = 0; column < 3; column++)
				if(cells[row][column].getToken() == ' ')
					return false;
			return true;
	} // ends isFull
	
	private boolean Won(char token) {
		
		//rows
		for(int row = 0; row < 3; row++)
			if((cells[row][0].getToken() == token) && (cells[row][1].getToken() == token) && (cells[row][2].getToken() == token))
				return true;
		
		// column
		for(int column = 0; column < 3; column++)
			if((cells[0][column].getToken() == token) && (cells[1][column].getToken() == token) && (cells[2][column].getToken() == token))
				return true;
		
		// diagonals
		if((cells[0][0].getToken() == token) && (cells[1][1].getToken() == token) && (cells[2][2].getToken() == token))
			return true;
		
		if((cells[0][2].getToken() == token) && (cells[1][1].getToken() == token) && (cells[2][0].getToken() == token))
			return true;
		return false;
	} // ends Won
	
	
	private class HandlerClass implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if(event.getSource() == exit_button ){
				dispose();
				MainWindow mw = new MainWindow();
				mw.setLocationRelativeTo(null);
				mw.setVisible(true);
				p1win = 0;
				p2win = 0;
			}
			else if(event.getSource() == reset_button) {
				dispose();
				GameWindow gw = new GameWindow(Player1_, Player2_);
				gw.setLocationRelativeTo(null);
				gw.setVisible(true);
			}
		} // ends actionPerformed
	} // ends HandlerClass
	
	public class Cell extends JPanel{
		
		private char token = ' ';
		
		public Cell() {
			this.setBackground(Color.WHITE);
			setBorder(new LineBorder(Color.BLACK, 2));
			addMouseListener(new myMouseListener());
		} // ends Cell constructor
		
		public char getToken() {
			return token;
		} // ends getToken
		
		public void setToken(char c) {
			token = c;			
			repaint();
		} // ends setToken
		
		public void paintComponent(Graphics g) {
			
			super.paintComponent(g);

			if(token == 'X') {
				g.drawImage(x_png, 10, 10, getWidth()-10, getHeight()-10, null);
			}
			
			else if(token == 'O') {
				g.drawImage(o_png, 10, 10, getWidth()-10, getHeight()-10, null);
			}
			
		} // ends paintComponent
		
		private class myMouseListener extends MouseAdapter{
			public void mouseClicked(MouseEvent event) {
				if(press == true) {
				
					if(token == ' ' && whosTurn != ' ')
						setToken(whosTurn);
					else if(token != ' ' && whosTurn != ' ')
						whosTurn = (whosTurn == 'X') ? 'O' : 'X';
					
					if(Won(whosTurn)) {
						if(whosTurn == 'X') {
								p1win ++;
								JOptionPane.showMessageDialog(null, Player1_ + " has won!");
							}
						else if(whosTurn == 'O') { 
								
							p2win++;
							JOptionPane.showMessageDialog(null, Player2_ + " has won!");
							
						}
						
						whosTurn = ' ';
						press = false;
					}
					else if(isFull())
						{
							JOptionPane.showMessageDialog(null, "It's a draw");
							press = false;
						}
					else {
						whosTurn = (whosTurn == 'X') ? 'O' : 'X';
						if(whosTurn == 'X') {
							Player1.setForeground(Color.BLUE);
							Player2.setForeground(Color.BLACK);
						}
						else {
							Player1.setForeground(Color.BLACK);
							Player2.setForeground(Color.RED);
						}
					}
					
				} // ends if (press)
			} // ends mousedCicked
		} // ends Mouse Listener
	
	} // ends Class Cell
} // ends Class GameWindow
