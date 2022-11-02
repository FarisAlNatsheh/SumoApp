import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class Window extends JFrame{
	JPanel drawPanel;
	int mapSize =5;
	int cellSize= 150;
	int mouseX, mouseY;
	int startX=mapSize/2, startY=mapSize-1;
	Cell[][] cells = new Cell[mapSize][mapSize];
	String path="";
	public void clear() {
		path = "";
		currentDir = 0;
		for(int i =0; i < mapSize; i++) {
			for(int j =0; j < mapSize; j++) {
				cells[i][j] = new Cell(i,j,0);

			}
		}
		cells[startX][startY].setType(2);
	}
	int currentDir = 0; //1 left 2 right 3 down 0 up
	public void findPath(int x, int y) {
		cells[x][y].setVisited(true);

		if(x - 1 >= 0) {
			if(!cells[x-1][y].isVisited() && cells[x-1][y].getType() == 1) {
				if(currentDir == 0)
					path+="la";
				else if(currentDir == 1)
					path+="a";
				else if(currentDir == 2)
					path+="lla";
				else if(currentDir == 3)
					path+="ra";
				currentDir = 1;
				findPath(x-1,y);
			}
		}
		
		if(x + 1 < mapSize) {
			if(!cells[x+1][y].isVisited() && cells[x+1][y].getType() == 1) {
				if(currentDir == 0)
					path+="ra";
				else if(currentDir == 1)
					path+="rra";
				else if(currentDir == 2)
					path+="a";
				else if(currentDir == 3)
					path+="la";
				currentDir = 2;
				findPath(x+1,y);
			}
		}
		
		if(y - 1 >= 0) {
			if(!cells[x][y-1].isVisited() && cells[x][y-1].getType() == 1) {
				if(currentDir == 0)
					path+="a";
				else if(currentDir == 1)
					path+="ra";
				else if(currentDir == 2)
					path+="la";
				else if(currentDir == 3)
					path+="rra";
				currentDir = 0;
				
				findPath(x,y-1);
			}
		}
		
		if(y + 1 < mapSize) {
			if(!cells[x][y+1].isVisited() && cells[x][y+1].getType() == 1) {
				if(currentDir == 0)
					path+="lla";
				else if(currentDir == 1)
					path+="la";
				else if(currentDir == 2)
					path+="ra";
				else if(currentDir == 3)
					path+="a";
				currentDir = 3;
				
				findPath(x,y+1);
			}
		}

		
		
		
		
		
	}
	
	
	
	
	public Window() {
		clear();
		setTitle("Sumo App");
		setSize(cellSize*(mapSize+1),cellSize*(mapSize+1));

		drawPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				for(int i =0; i < mapSize; i++) {
					for(int j =0; j < mapSize; j++) {

						g.setColor(new Color(0,0,0));
						if(cells[i][j].getType() == 0)
							g.drawRect(i*cellSize, j*cellSize, cellSize, cellSize);
						if(cells[i][j].getType() == 1) {
							g.setColor(new Color(255,0,0,50));
							g.fillRect(i*cellSize, j*cellSize, cellSize, cellSize);
						}
						if(cells[i][j].getType() == 2) {
							g.setColor(new Color(0,255,0));
							g.fillRect(i*cellSize, j*cellSize, cellSize, cellSize);
						}

						g.setColor(new Color(0,0,0));
						g.drawRect(i*cellSize, j*cellSize, cellSize, cellSize);
					}
				}
				repaint();

			}
		};
		getContentPane().add(drawPanel);
		add(drawPanel);
		drawPanel.setBackground(Color.white);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		
		addMouseListener(new MouseAdapter() {
			
			public void mousePressed(MouseEvent e) {
				//clear();
				for(int i =0; i < mapSize; i++) {
					for(int j =0; j < mapSize; j++) {
						cells[i][j].setVisited(false);

					}
				}
				path = "";
				currentDir = 0;
				mouseX = e.getX()-8;
				mouseY = e.getY()-32;
				int mMouseX, mMouseY;
				mMouseX = mouseX/cellSize;
				mMouseY = mouseY/cellSize;
				if(mMouseX < mapSize && mMouseY < mapSize) {
					if(cells[mMouseX][mMouseY].getType() != 1)
						cells[mMouseX][mMouseY].setType(1);
					else
						cells[mMouseX][mMouseY].setType(0);
//					System.out.println(mMouseX + " " + mMouseY);
//					if(startY - mMouseY > 0) {
//						for(int i = mMouseY; i <= startY; i++) 
//							cells[mMouseX][i].setType(1);
//					}
//					else {
//						for(int i = startY+1; i < mMouseY; i++) 
//							cells[mMouseX][i].setType(1);
//					}
//
//					if(startX - mMouseX > 0) {
//						for(int i = mMouseX; i < startX; i++) 
//							cells[i][startY].setType(1);
//					}
//					else {
//						for(int i = startX+1; i <= mMouseX; i++) 
//							cells[i][startY].setType(1);
//					}
					cells[startX][startY].setType(2);
				}	
				findPath(startX, startY);
				System.out.println(path);
			}
		});
	}
	public static void main(String[] args) {
		new Window();
	}

}
