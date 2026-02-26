package TreeClasses;

/**
 *This class represents a point in 2D and stores the coordinates(x,y) of the point
 */

public class KDPoint {
	private int x;
	private int y;
	
	public KDPoint(int x, int y) {
		this.x = x;
		this.y = y;
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

}
