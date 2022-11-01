import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class Window extends JFrame{
	JPanel drawPanel;
	int mapSize =11;
	int cellSize= 50;
	int mouseX, mouseY;
	int startX=mapSize/2, startY=mapSize-5;
	Cell[][] cells = new Cell[mapSize][mapSize];
	public void clear() {
		for(int i =0; i < mapSize; i++) {
			for(int j =0; j < mapSize; j++) {
				cells[i][j] = new Cell(i,j,0);
				
			}
		}
		cells[startX][startY].setType(2);
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
				clear();
				mouseX = e.getX()-8;
				mouseY = e.getY()-32;
				int mMouseX, mMouseY;
				mMouseX = mouseX/cellSize;
				mMouseY = mouseY/cellSize;
				
				cells[mMouseX][mMouseY].setType(1);
				
				
				
				System.out.println(mMouseX + " " + mMouseY);
				
				if(startY - mMouseY > 0) {
					for(int i = mMouseY; i <= startY; i++) 
						cells[mMouseX][i].setType(1);
				}
				else {
					for(int i = startY+1; i < mMouseY; i++) 
						cells[mMouseX][i].setType(1);
				}
				
				if(startX - mMouseX > 0) {
					for(int i = mMouseX; i < startX; i++) 
						cells[i][startY].setType(1);
				}
				else {
					for(int i = startX+1; i <= mMouseX; i++) 
						cells[i][startY].setType(1);
				}
				cells[startX][startY].setType(2);
			}	
		});
	}
	public static void main(String[] args) {
		new Window();
	}

}
