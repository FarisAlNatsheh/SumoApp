
public class Cell {
	private int x,y;
	private int type; //0 empty //1 path //2 start
	private boolean visited;
	private boolean enabled;
	private String order;
	private int clicks;
	public Cell(int x, int y, int type) {
		this.setX(x);
		this.setY(y);
		this.setType(type);
	}
	
	
	
	
	
	public int getType() {
		if(enabled)
			return 3;
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
	public void setEnabled(boolean n) {
		enabled = n;
	}




	public boolean isVisited() {
		return visited;
	}
	
	public void setVisited(boolean selected) {
		this.visited = selected;
	}





	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		if(this.order != null) {
			this.order += "/" + order;
			return;
		}
		this.order = order;
	}

	public int getClicks() {
		return clicks;
	}

	public void setClicks(int clicks) {
		this.clicks = clicks;
	}
}
