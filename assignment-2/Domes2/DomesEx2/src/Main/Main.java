package Main;

import java.awt.Point;
import java.util.Random;

import TreeClasses.KDPoint;
import TreeClasses.KDTree;
import TreeClasses.QuadNode;
import TreeClasses.QuadTree;

public class Main {
	
	public static void main(String[] args) {
		 int[] M = {200, 500, 1000, 10000,30000, 50000, 70000, 100000};
		 int N = (int)Math.pow(2, 18);
		 int numOfSearches = 100;
		 float meanSuccessfulDepth;
		 float meanUnsuccessfulDepth;
		 
		 long timeStart, timeEnd;
		 float meanTimeKD, meanTimeQuad;
		 
		 /************************KD-TREE*********************/
		 System.out.println("------------------KD-TREE-----------------");
		 timeStart = System.nanoTime();
		 for(int i=0; i<M.length; i++) {
			 //Generate M random points
			 Random rand = new Random();
			 KDTree tree = new KDTree();
			 KDPoint[] points = new KDPoint[M[i]];
			 
			 //Insert random points in the tree
			 for(int j = 0; j < M[i]; j++) {
				 int x = rand.nextInt(N);
				 int y = rand.nextInt(N);
				 KDPoint point = new KDPoint(x, y);
				 points[j] = point;
				 tree.insert(point);
			 }
			 
			 int successfulDepthSum = 0;//Depth sum for successful searches
			 int unsuccessfulDepthSum = 0;//Depth sum for unsuccessful searches
			 
			 //Perform searches for existing and non-existing  points
			 for(int k = 0; k < numOfSearches; k++) {
				//Search for existing point
				 KDPoint existingPoint = points[rand.nextInt(M[i])];
				 int successfulDepth = tree.KDsearch(existingPoint);
				 successfulDepthSum += successfulDepth;
				 
				 //Search for non-existing point
				 KDPoint nonExistingPoint = new KDPoint(rand.nextInt(N), rand.nextInt(N));
				 int unsuccessfulDepth = tree.KDsearch(nonExistingPoint);
				 unsuccessfulDepthSum += unsuccessfulDepth;
				 
			 }
			 			 
			 meanSuccessfulDepth = (float)successfulDepthSum/numOfSearches;
			 meanUnsuccessfulDepth = (float)unsuccessfulDepthSum/numOfSearches;

			 System.out.println("For: "+M[i]+" the mean successful depth is: "+meanSuccessfulDepth);
			 System.out.println("For: "+M[i]+" the mean unsuccessful depth is: "+meanUnsuccessfulDepth);
			 
		 }
		 timeEnd = System.nanoTime();
		 meanTimeKD = (float)(timeEnd-timeStart)/numOfSearches;

		 System.out.println("The mean time for KD-Tree is:"+meanTimeKD);

		 
		 /**************************PR QUAD-TREE*************************/
		 System.out.println("\n------------------PR QUAD-TREE-----------------");
		 timeStart = System.nanoTime();
		 for(int i=0; i<M.length; i++) {
			//Generate M random points
			 Random rand = new Random();
//			 QuadPoint[] quadPoints = new QuadPoint[M[i]];		
			 int [][] data = new int[M[i]][2];
			 
			 for(int j = 0; j<M[i]; j++) {
				 data[j][0] = rand.nextInt(N);
	             data[j][1] = rand.nextInt(N);
			 }
			 
			 QuadTree quadTree = new QuadTree(0,0, N,N);
			 for(int[] point:data) {
				 quadTree.insert(point[0], point[1]);
			 }
			 
			 int successfulDepthSum = 0; // Depth sum for successful searches
			 int unsuccessfulDepthSum = 0; // Depth sum for unsuccessful searches

			 // Perform searches for existing and non-existing points
			 for (int k = 0; k < numOfSearches; k++) {
			     // Search for existing point
			     int[] existingPoint = data[rand.nextInt(M[i])];
			     quadTree.setLevel(0);
			     QuadNode successfulDepthNode = quadTree.search(existingPoint[0], existingPoint[1]);
			     successfulDepthSum += quadTree.getLevel();
			    

			     // Search for non-existing point
			     int[] nonExistingPoint = new int[]{rand.nextInt(N), rand.nextInt(N)};
			     quadTree.setLevel(0);
			     QuadNode unsuccessfulDepthNode = quadTree.search(nonExistingPoint[0], nonExistingPoint[1]);
			     unsuccessfulDepthSum += quadTree.getLevel();
			     
			 }
			 			 
			 meanSuccessfulDepth = (float)successfulDepthSum/numOfSearches;
			 meanUnsuccessfulDepth = (float)unsuccessfulDepthSum/numOfSearches;

			 System.out.println("For: "+M[i]+" the mean successful depth is: "+meanSuccessfulDepth);
			 System.out.println("For: "+M[i]+" the mean unsuccessful depth is: "+meanUnsuccessfulDepth);
		 }
		 timeEnd = System.nanoTime();
		 meanTimeQuad = (float)(timeEnd-timeStart)/numOfSearches;

		 System.out.println("The mean time for PR Quad-Tree is: "+meanTimeQuad);

	}

}
