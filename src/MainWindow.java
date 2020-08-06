import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainWindow extends JFrame{

	private JTextField titleText;
	private JButton play_button;
	private JButton exit_button;
	private String player1;
	private String player2;
	
	
	public MainWindow(){
		
		super("X & 0");
		this.setSize(600, 400);
		
		
		titleText = new JTextField("X & 0");
		titleText.setFont(new Font("Serif", Font.BOLD, 200));
		titleText.setEditable(false);
		titleText.setBackground(Color.WHITE);
		titleText.setHorizontalAlignment(JTextField.CENTER);
		add(titleText, BorderLayout.CENTER);
		
		play_button = new JButton("PLAY");
		add(play_button, BorderLayout.NORTH);
		
		exit_button = new JButton("EXIT");
		add(exit_button, BorderLayout.SOUTH);
		
		HandlerClass handler = new HandlerClass();
		play_button.addActionListener(handler);
		exit_button.addActionListener(handler);
		
	} // ends MainWindow constructor
	
	private class HandlerClass implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if(event.getSource() == exit_button)
				System.exit(0);
			else {
				dispose();
				
				player1 = JOptionPane.showInputDialog("Enter first player: ");
				player2 = JOptionPane.showInputDialog("Enter second player: ");
				
				GameWindow gw = new GameWindow(player1, player2);
				gw.setLocationRelativeTo(null);
				gw.setVisible(true);
				
				
			}
		} // ends actionPerformed
	
	} // ends HandlerClass
} // ends Class MainWindow
