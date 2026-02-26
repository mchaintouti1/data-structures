package TreeClasses;

public class QuadNode {
	private QuadNode internal;

	int x,y;
	int Xmin,Xmax, Ymin, Ymax;
	
    QuadNode NW;
	QuadNode NE;
	QuadNode SW;
	QuadNode SE;
	boolean leaf;
	boolean divided;
	
	QuadNode(int xmin,int ymin,int xmax,int ymax) //internal node
    { 
		this.Xmin = xmin;
		this.Xmax = xmax;
		this.Ymin = ymin;
		this.Ymax = ymax;
        this.NW=null;
        this.NE=null;
        this.SW=null;
        this.SE=null;
        leaf=true;
        this.divided=false;

    }
	
	QuadNode(int x,int y,int xmin,int ymin,int xmax,int ymax) //leaf nodes
    { 
		this.Xmin = xmin;
		this.Xmax = xmax;
		this.Ymin = ymin;
		this.Ymax = ymax;
		this.x = x;
		this.y =y;
		leaf=true;

    }
	
	
	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}



	public QuadNode getInternal() {
		return internal;
	}

	public void setInternal(QuadNode internal) {
		this.internal = internal;
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

	public int getXmin() {
		return Xmin;
	}

	public void setXmin(int xmin) {
		Xmin = xmin;
	}

	public int getXmax() {
		return Xmax;
	}

	public void setXmax(int xmax) {
		Xmax = xmax;
	}

	public int getYmin() {
		return Ymin;
	}

	public void setYmin(int ymin) {
		Ymin = ymin;
	}

	public int getYmax() {
		return Ymax;
	}

	public void setYmax(int ymax) {
		Ymax = ymax;
	}

	public QuadNode getNW() {
		return NW;
	}

	public void setNW(QuadNode nW) {
		NW = nW;
	}

	public QuadNode getNE() {
		return NE;
	}

	public void setNE(QuadNode nE) {
		NE = nE;
	}

	public QuadNode getSW() {
		return SW;
	}

	public void setSW(QuadNode sW) {
		SW = sW;
	}

	public QuadNode getSE() {
		return SE;
	}

	public void setSE(QuadNode sE) {
		SE = sE;
	} 

}
