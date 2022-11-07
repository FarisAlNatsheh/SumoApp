import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.*;

public class Window2 extends JFrame implements KeyListener{
	JPanel drawPanel;
	String lastMsg;
	Timer timer = new Timer(50, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			lastMsg = netNode.getLastMessage();
		}


	});
	int mapSize =5;
	int cellSize= 150;
	int mouseX, mouseY;
	int startX=mapSize/2, startY=mapSize-1;
	Cell[][] cells = new Cell[mapSize][mapSize];
	String path="";
	int currX=startX, currY=startY;
	int order;
	Node netNode;
	public void clear() {
		order = 0;
		path = "";
		currentDir = 0;
		for(int i =0; i < mapSize; i++) {
			for(int j =0; j < mapSize; j++) {
				cells[i][j] = new Cell(i,j,0);

			}
		}
		startX=mapSize/2;
		startY=mapSize-1;
		currX = startX;
		currY = startY;
		cells[startX][startY].setType(2);
		
		refresh();
		repaint();
	}
	public void clearPath() {
		order = 0;
		startX = currX;
		startY= currY;
		for(int i =0; i < mapSize; i++) {
			for(int j =0; j < mapSize; j++) {
				cells[i][j] = new Cell(i,j,0);

			}
		}
		cells[startX][startY].setType(2);
		refresh();
		repaint();
	}
	int currentDir = 0; //1 left 2 right 3 down 0 up
	public void refresh() {
		for(int i =0; i < mapSize; i++) {
			for(int j =0; j < mapSize; j++) {
				cells[i][j].setEnabled(false);

			}
		}
		if(currX+1 < mapSize)
			if(!cells[currX+1][currY].isVisited())
				cells[currX+1][currY].setEnabled(true);
		if(currY+1 < mapSize)
			if(!cells[currX][currY+1].isVisited())
				cells[currX][currY+1].setEnabled(true);
		if(currX-1 >= 0)
			if(!cells[currX-1][currY].isVisited())
				cells[currX-1][currY].setEnabled(true);
		if(currY-1 >= 0)
			if(!cells[currX][currY-1].isVisited())
				cells[currX][currY-1].setEnabled(true);
	}


	public Window2(String ip, int port) {
		this.addKeyListener(this);
		clear();
		setTitle("Sumo App");
		setSize(cellSize*(mapSize+1)+200,cellSize*(mapSize+1));
		netNode = new Node();
		netNode.startServerSocket(port+1);
		netNode.startSendSocket(ip,port);

		drawPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				int x = cellSize*(mapSize+1)+30;
				int y = 50;
				int currDir = 0; //1 left -1 right -2/2 down 0 up
				String currentPath = path;

				for(int i = 0;i < currentPath.length();i++) {
					g.setColor(new Color(0,0,255,50));
					if((currentPath.charAt(0) == 'a')) {
						if(currDir == 0) {
							g.fillRect(x, y, 5, 25);
							y = y+25;
						}
						if(currDir == 2 || currDir == -2) {
							g.fillRect(x, y-25, 5, 25);
							y = y-25;
						}
						if(currDir == 1) {
							g.fillRect(x-25, y, 25, 5);
							x = x-25;
						}
						if(currDir == -1) {
							g.fillRect(x, y, 25, 5);
							x = x+25;
						}

					}
					if(currentPath.charAt(0) == 'l') {
						currDir++;
					}
					if(currentPath.charAt(0) == 'r') {
						currDir--;
					}
					if(currDir > 2)
						currDir = -1;
					if(currDir < -2)
						currDir = 1;
					currentPath = currentPath.substring(1);
					i--;
				}
				for(int i =0; i < mapSize; i++) {
					for(int j =0; j < mapSize; j++) {

						g.setColor(new Color(0,0,0));
						if(cells[i][j].getType() == 0)
							g.drawRect(i*cellSize, j*cellSize, cellSize, cellSize);
						if(cells[i][j].getType() == 1) {
							if(cells[i][j].getClicks()*50 < 255)
								g.setColor(new Color(255,0,0,cells[i][j].getClicks()*50));
							else
								g.setColor(new Color(255,0,0));
							g.fillRect(i*cellSize, j*cellSize, cellSize, cellSize);
						}
						if(cells[i][j].getType() == 2) {
							g.setColor(new Color(0,255,0));
							g.fillRect(i*cellSize, j*cellSize, cellSize, cellSize);
						}
						if(cells[i][j].getType() == 3) {
							g.setColor(new Color(0,0,255,50));
							g.fillRect(i*cellSize, j*cellSize, cellSize, cellSize);
						}

						g.setColor(new Color(0,0,0));
						g.drawRect(i*cellSize, j*cellSize, cellSize, cellSize);
						if(cells[i][j].getOrder()!= null)
							g.drawString(""+cells[i][j].getOrder(),i*cellSize+cellSize/2, j*cellSize+cellSize/2);
					}
				}
				try {
					if(lastMsg.equals("b"))
						g.fillRect(0,0, cellSize*(mapSize+1)+200,cellSize*(mapSize+1));
					if(lastMsg.equals("a")) {
						clearPath();
					}
					if(lastMsg.equals("r")) {
						clear();
					}
				}
				catch(Exception e) {
					
				}
				repaint();

			}
		};
		getContentPane().add(drawPanel);
		add(drawPanel);
		timer.start();
		drawPanel.setBackground(Color.white);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		refresh();

		addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				try {
					if(lastMsg.equals("b"))
						return;
				}
				catch(Exception e1) {
					
				}
				//clear();
				//netNode.sendMessage("hi");
				System.out.println(netNode.getLastMessage());
				for(int i =0; i < mapSize; i++) {
					for(int j =0; j < mapSize; j++) {
						cells[i][j].setVisited(false);

					}
				}
				//path = "";
				//currentDir = 0;
				mouseX = e.getX()-8;
				mouseY = e.getY()-32;
				int mMouseX, mMouseY;
				mMouseX = mouseX/cellSize;
				mMouseY = mouseY/cellSize;
				if(mMouseX < mapSize && mMouseY < mapSize) {
					if(cells[mMouseX][mMouseY].getType() == 3) {
						if(mMouseX == currX+1) {
							if(currentDir == 0)
								path+="ra";
							else if(currentDir == 1)
								path+="rra";
							else if(currentDir == 2)
								path+="a";
							else if(currentDir == 3)
								path+="la";
							currentDir = 2;
						}
						if(mMouseX == currX-1) {
							if(currentDir == 0)
								path+="la";
							else if(currentDir == 1)
								path+="a";
							else if(currentDir == 2)
								path+="lla";
							else if(currentDir == 3)
								path+="ra";
							currentDir = 1;
						}
						if(mMouseY == currY-1) {
							if(currentDir == 0)
								path+="a";
							else if(currentDir == 1)
								path+="ra";
							else if(currentDir == 2)
								path+="la";
							else if(currentDir == 3)
								path+="rra";
							currentDir = 0;
						}
						if(mMouseY == currY+1) {
							if(currentDir == 0)
								path+="lla";
							else if(currentDir == 1)
								path+="la";
							else if(currentDir == 2)
								path+="ra";
							else if(currentDir == 3)
								path+="a";
							currentDir = 3;
						}
						order++;
						cells[mMouseX][mMouseY].setType(1);
						currX = mMouseX;
						currY = mMouseY;
						cells[mMouseX][mMouseY].setVisited(true);
						cells[mMouseX][mMouseY].setClicks(cells[mMouseX][mMouseY].getClicks()+1);
						cells[mMouseX][mMouseY].setOrder(""+order);
						refresh();
					}
					cells[startX][startY].setType(2);
				}	
				System.out.println(path);
				netNode.sendMessage(path);
			}
		});
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void keyPressed(KeyEvent e) {
		clearPath();


	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	//	public static void main(String[] args) {
	//		new Window2("127.0.0.1",5000);
	//	}

}
