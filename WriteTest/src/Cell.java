
public class Cell {
	private int x,y;
	private int type; //0 empty //1 path //2 start
	private boolean visited;
	public Cell(int x, int y, int type) {
		this.setX(x);
		this.setY(y);
		this.setType(type);
	}
	
	
	
	
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}





	public boolean isVisited() {
		return visited;
	}
	
	public void setVisited(boolean selected) {
		this.visited = selected;
	}
}
