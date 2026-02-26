package TreeClasses;

public class KDTree {
	private static final int k = 2;
	
	private class KDNode{
		private KDPoint point;
		private KDNode left;
		private KDNode right;
		
		public KDNode(KDPoint point) {
			this.point = point;
		}
	}
	
	private static KDNode root;
	
	public  void insert(KDPoint point) {
		root = insert(root, point, 0);
	}
	
	public KDNode insert(KDNode node, KDPoint point, int depth) {
		if(node==null)
			return new KDNode(point);
		
		int cd = depth%k;
		
		if(cd != 0) {
			if(point.getX()<node.point.getX()) {
				node.left = insert(node.left, point, depth+1);
			}else {
				node.right = insert(node.right, point, depth+1);
			}
		}else {
			if(point.getY()<node.point.getY()) {
				node.left = insert(node.left, point, depth+1);
			}else {
				node.right = insert(node.right, point, depth+1);
			}
		}
		
		return node;
	}
	
	public int KDsearch(KDPoint point) {
		return search(root, point, 0);
	}
	
	 public int search(KDNode node, KDPoint point, int depth) {
	        if (node == null)
	            return depth;

	        int cd = depth % k;

	        if (node.point.getX() == point.getX() && node.point.getY() == point.getY()) {
	         //   System.out.println("Point found: (" + point.getX() + "," + point.getY() + ")");
	            return depth;
	        } else if (cd != 0) {
	            if (point.getX() < node.point.getX()) {
	                return search(node.left, point, depth + 1);
	            } else {
	                return search(node.right, point, depth + 1);
	            }
	        } else {
	            if (point.getY() < node.point.getY()) {
	                return search(node.left, point, depth + 1);
	            } else {
	                return search(node.right, point, depth + 1);
	            }
	        }
	    }

	
	
}
