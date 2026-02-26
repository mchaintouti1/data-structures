package Main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import BTreeClasses.*;
import BTreeClasses.BPlusTreeMetrics;
import BTreeClasses.BTree;
import BTreeClasses.Implementation;
import BTreeClasses.IndexInfo;


public class Main {
	public static void menu() {
		System.out.println("--------------MENU--------------");
		System.out.println("1.Insert word information.");
		System.out.println("2.Search word and print the information about it");
		System.out.println("3.100 searches for existed words in the text");
		System.out.println("0.EXIT");
	}

	public static void main(String[] args) throws IOException{
		int[] Ms = {10, 20};
		int option, M;
		
		Scanner scn = new Scanner(System.in);
		System.out.println("Insert the data order M. It has to be 10 or 20: ");
		M = Integer.parseInt(scn.nextLine());
		
		Implementation bpTree = new Implementation(M);
		
		while(true) {
			System.out.println("\nChoose one of the following: ");
			menu();
			String inp= scn.nextLine();
			option= Integer.parseInt(inp);
			
			switch(option) {
			case 1:
				System.out.println("\nEnter the name of the desired file: ");
				String fname = scn.nextLine();
				bpTree.processFile(fname);
				break;
			case 2:
				System.out.println("\nEnter the word you want to search: ");
				String word = scn.nextLine();
				bpTree.searchWordInIndex(word);
				System.out.println("Total node accesses: " + bpTree.getIndex().bpMetrics.getNodeAccessCount());
                System.out.println("Total comparisons: " +bpTree.getIndex().bpMetrics.getComparisonCount());
				break;
			case 3:
			    BTree<String, LinkedList<IndexInfo>> index = new BTree<>(M);
			    LinkedList<String> fileNames = new LinkedList<String>();
			    
				float totalAccessedNodes = 0;
				float totalComparisons = 0;


				    float[] measurements = bpTree.performSearches("1.txt", "2.txt");
				    totalAccessedNodes = measurements[0];
				    totalComparisons = measurements[1];
		
                 float averageAccessedNodes =  totalAccessedNodes / 100;
                 float averageComparisons = totalComparisons / 100;
                 
                 System.out.println("\nSearch statistics:");
                 System.out.println("Average number of node accesses: " + averageAccessedNodes);
                 System.out.println("Average number of comparisons: " + averageComparisons);
                 break;
			case 0:
				System.out.println("EXIT");
                System.exit(0);
                break;
            default:
                System.out.println("Sorry!Invalid option,try again!");
			}
		
		}
		
		
	}

}
