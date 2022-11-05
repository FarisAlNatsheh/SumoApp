import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;

public class AdminWindow extends JFrame{
	JPanel drawPanel;
	public AdminWindow() {
		setTitle("Sumo App");
		setSize(500,500);
		//startServerSocket(5000);
		drawPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				
			}
		};
		getContentPane().add(drawPanel);
		add(drawPanel);
		drawPanel.setBackground(Color.white);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	public static void main(String[] args) {
		new AdminWindow();
	}
}
