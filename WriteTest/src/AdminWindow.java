import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class AdminWindow extends JFrame implements ActionListener, KeyListener{
	JPanel drawPanel;
	Timer timer = new Timer(1000, this);
	Node netNode1;
	Node netNode2;
	SerialInput player1Serial;
	SerialInput player2Serial;
	String lastMsg1, lastMsg2;
	JLabel player1 = new JLabel();
	JLabel player2 = new JLabel();
	JButton start = new JButton("Start");
	JButton ready = new JButton("Ready");
	boolean switch1 = true;
	boolean ready1 = true;
	public AdminWindow(String ip1, String ip2) {
		timer.start(); 
		player1Serial = new SerialInput("COM17");
		player2Serial = new SerialInput("COM7");
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
		drawPanel.add(player2);
		drawPanel.add(ready);
		drawPanel.add(start);
		drawPanel.setLayout(new GridLayout(2,2));
		this.addKeyListener(this);
		player1.addKeyListener(this);
		start.addKeyListener(this);
		player2.addKeyListener(this);
		ready.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				if(ready1) {
					netNode1.sendMessage("b");
					netNode2.sendMessage("b");
					ready.setText("Not Ready");
				}
				else {
					netNode1.sendMessage("a");
					netNode2.sendMessage("a");
					try {
						Thread.sleep(100);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					netNode1.sendMessage("l");
					netNode2.sendMessage("l");
					ready.setText("Ready");
				}
				ready1 = !ready1;
			}
			
		});
		start.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				netNode1.sendMessage("b");
				netNode2.sendMessage("b");
				ready1 = false;
				ready.setText("Not Ready");
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
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println(switch1);
		if(e.getKeyCode() == KeyEvent.VK_P) {
			switch1 = !switch1;
		}
		if(switch1) {
			if(e.getKeyCode() == KeyEvent.VK_W) {
				player2Serial.command((byte) ('W'));
			}
			else if (e.getKeyCode() == KeyEvent.VK_A) {
				player2Serial.command((byte) ('A'));
			}
			else if (e.getKeyCode() == KeyEvent.VK_S) {
				player2Serial.command((byte) ('S'));
			}
			else if (e.getKeyCode() == KeyEvent.VK_D) {
				player2Serial.command((byte) ('D'));
			}
		}
		else{
			if(e.getKeyCode() == KeyEvent.VK_W) {
				player1Serial.command((byte) ('W'));
			}
			else if (e.getKeyCode() == KeyEvent.VK_A) {
				player1Serial.command((byte) ('A'));
			}
			else if (e.getKeyCode() == KeyEvent.VK_S) {
				player1Serial.command((byte) ('S'));
			}
			else if (e.getKeyCode() == KeyEvent.VK_D) {
				player1Serial.command((byte) ('D'));
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		try {
			player1Serial.command((byte) (101));
		}catch(Exception e1) {}
		try {
			player2Serial.command((byte) (101));
		}catch(Exception e1) {}
	}
}
