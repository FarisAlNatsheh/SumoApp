import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class AdminWindow extends JFrame implements ActionListener{
	JPanel drawPanel;
	Timer timer = new Timer(1000, this);
	Node netNode1;
	Node netNode2;
	SerialInput player1Serial;
	SerialInput player2Serial;
	String lastMsg1, lastMsg2;
	JTextArea player1 = new JTextArea();
	JTextArea player2 = new JTextArea();
	JButton start = new JButton("Start");
	public AdminWindow(String ip1, String ip2) {
		timer.start(); 
		player1Serial = new SerialInput("COM27");
		player2Serial = new SerialInput("COM20");
		netNode1 = new Node();
		netNode2 = new Node();
		setTitle("Sumo App");
		setSize(500,500);
		netNode1.startServerSocket(5000);
		netNode1.startSendSocket(ip1, 5001);
		netNode2.startServerSocket(6000);
		netNode2.startSendSocket(ip2, 6001);
		drawPanel = new JPanel();
		getContentPane().add(drawPanel);
		add(drawPanel);
		drawPanel.add(player1);
		drawPanel.add(start);
		drawPanel.add(player2);
		drawPanel.setLayout(new GridLayout());
		start.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					for(int i =0; i < lastMsg1.length(); i++) {
						if(lastMsg1.charAt(i) == 'a') {
							player1Serial.command((byte) (97));
						}
						if(lastMsg1.charAt(i) == 'l') {
							player1Serial.command((byte) (99));
						}
						if(lastMsg1.charAt(i) == 'r') {
							player1Serial.command((byte) (100));
						}
						player1Serial.command((byte) (101));
					}
				}
				catch(Exception e1) {
					JOptionPane.showMessageDialog(null, "Could not connect to Serial port (Check Bluetooth connection)", "Player 1 Error", JOptionPane.ERROR_MESSAGE);
				}

				try {
					for(int i =0; i < lastMsg2.length(); i++) {
						if(lastMsg2.charAt(i) == 'a') {
							player2Serial.command((byte) (97));
						}
						if(lastMsg2.charAt(i) == 'l') {
							player2Serial.command((byte) (99));
						}
						if(lastMsg2.charAt(i) == 'r') {
							player2Serial.command((byte) (100));
						}
						player2Serial.command((byte) (101));
					}
				}
				catch(Exception e1) {
					JOptionPane.showMessageDialog(null, "Could not connect to Serial port (Check Bluetooth connection)", "Player 2 Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		});
		//drawPanel.setBackground(Color.white);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		lastMsg1 = netNode1.getLastMessage();
		lastMsg2 = netNode2.getLastMessage();
		//System.out.println(lastMsg1);
		//System.out.println(lastMsg2);
		lastMsg1 = "aaa";
		lastMsg2 = "aaa";
		
		player1.setText("Player 1:\n\n "+ lastMsg1);
		player2.setText("Player 2:\n\n "+ lastMsg2);
	}
}
