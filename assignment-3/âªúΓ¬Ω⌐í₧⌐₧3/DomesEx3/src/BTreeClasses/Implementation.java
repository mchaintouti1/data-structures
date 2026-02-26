package BTreeClasses;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Implementation {
	private BTree<String, LinkedList<IndexInfo>> index;
	
	public Implementation(int M) {
		this.index = new BTree<>(M);
	}

	public void processFile(String fileName) throws FileNotFoundException, IOException {
		File file = new File(fileName);
		
		try(RandomAccessFile raf = new RandomAccessFile(file, "r")) {
			String word;
			int position = 0;
			
			while((word = readNextWord(raf)) != null) {
				if(index.search(word) != null) {
					LinkedList<IndexInfo> indexInfoList = index.search(word);
					indexInfoList.add(new IndexInfo(fileName, position));
				}else {
					LinkedList<IndexInfo> indexInfoList = new LinkedList<>();
					indexInfoList.add(new IndexInfo(fileName, position));
					index.insert(word, indexInfoList);
				}
				position+= word.length()+ 1;
			}
		}catch(FileNotFoundException e) {
				System.out.println("File not found!");
				e.printStackTrace();
		}catch(IOException e) {
			System.out.println("Error at file reading.");
			e.printStackTrace();
		}	
	}
	
	public String readNextWord(RandomAccessFile raf) throws IOException {
		StringBuilder wordBuilder = new StringBuilder();
		char currentChar;
		
		//Check we reach the end of file
		if(raf.getFilePointer() == raf.length()) {
			return null;
		}
		
		//Read every character until we find null or next line
		while(true) {
			currentChar = (char) raf.readByte();
			
			if(Character.isWhitespace(currentChar) || currentChar == '\n') {
				break;
			}
			
			wordBuilder.append(currentChar);
		}
		return wordBuilder.toString();
	}
	
	public void searchWordInIndex(String searchWord) {
		BTree.bpMetrics.resetMetrics();

		LinkedList<IndexInfo> indexInfoList = index.search(searchWord);
		
		if(indexInfoList != null) {
			System.out.println("The word " + searchWord + " is located at the following files and positions:");
			
			for(IndexInfo indexInfo: indexInfoList) {
				System.out.println("File: " + indexInfo.getFileName() + ", position: " + indexInfo.getPosition());
			}
		}else {
			System.out.println("The word " + searchWord + " was not found.");
		}
	}
	
	public float[] performSearches(String fileName1, String fileName2) throws IOException {
		BTree.bpMetrics.resetMetrics();

		float[] measurements = new float[2];
	    LinkedList<String> fileNames = new LinkedList<String>();
	    fileNames.add(fileName1);
	    fileNames.add(fileName2);
	    Random random = new Random();

	       
	        LinkedList<String> randomWord = getRandomWordFromFile(fileName1, 50);
	        LinkedList<String> randomWord2 = getRandomWordFromFile(fileName2, 50);
	        randomWord.addAll(randomWord2);
	        
	        for(int i = 0; i<randomWord.size(); i++) {
	    		BTree.bpMetrics.resetMetrics();
	    		 int randomIndex = random.nextInt(randomWord.size());
	    		 
	        	index.search(randomWord.get(randomIndex));

		        measurements[0] += (float) index.bpMetrics.getNodeAccessCount();
		        measurements[1] += (float) index.bpMetrics.getComparisonCount();
	        }
	        
	    return measurements;
	}

	public LinkedList<String> getRandomWordFromFile(String fileName, int size) throws IOException {
	    LinkedList<String> words = new LinkedList<>();
	    LinkedList<String> randomWords = new LinkedList<>();


	    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
	        String line;

	        while ((line = reader.readLine()) != null) {
	            String[] lineWords = line.split(" ");

	            for (String word : lineWords) {
	                words.add(word);
	            }
	           line = reader.readLine();
	        } reader.close();

            Random random = new Random(System.currentTimeMillis());
            for (int i = 0; i < size; i++) {
                int randomIndex = random.nextInt(words.size());
                randomWords.add(words.get(randomIndex));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return randomWords;
	}

	public BTree<String, LinkedList<IndexInfo>> getIndex() {
		return index;
	}

}
