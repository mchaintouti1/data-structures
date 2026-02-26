package Main;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import GeneralClasses.BinarySearch;
import GeneralClasses.DataClass;
import GeneralClasses.IndexFileClass;
import GeneralClasses.RandomGenerator;
import GeneralClasses.PairClass;
import GeneralClasses.WrittingAndReading;

public class Main {
	
	public static void methodA(WrittingAndReading war, int stringSize, int numOfData) throws IOException {
		long timeStart, timeEnd;
		float meanTime;
		float meanDiskAccesses;
		int[] keys;
		int[] keysToSearch;
		
		//Create DataClass instances
		DataClass[] dataInstances = new DataClass[numOfData];
		
		//Check if the numOfData is 1000. If true => generate random non unique keys.If false => generate random unique keys
		if(numOfData<1000) {
			keys = RandomGenerator.getNonUniqueRandomGenerator(1, 2*numOfData, numOfData);
			keysToSearch = RandomGenerator.getNonUniqueRandomGenerator(1, 2*numOfData, 1000);
		}else {
			keys = RandomGenerator.getUniqueRandomGenerator(1, 2*numOfData, numOfData);
			keysToSearch = RandomGenerator.getUniqueRandomGenerator(1, 2*numOfData, 1000);
		}
		
		String[] data = new String[numOfData];
		for(int j =0; j<data.length; j++) {
			data[j] = RandomGenerator.randomStringGenerator(stringSize);
		}

		for(int k = 0; k<numOfData; k++) {
			dataInstances[k] = new DataClass(keys[k], data[k]);
		}
		
		war.writeDataToFile(dataInstances, stringSize);
		
		timeStart = System.nanoTime();
		for(int i=0; i<keysToSearch.length; i++) {
			DataClass.searchInDataFile(keysToSearch[i], stringSize, war);
		}
		timeEnd = System.nanoTime();
		meanTime = (float)(timeEnd-timeStart)/keysToSearch.length;
		meanDiskAccesses = (float)war.getDiskAccesses()/keysToSearch.length;
		
		System.out.println("For: "+numOfData+" Mean Disk Accesses are: "+meanDiskAccesses+" , in mean time:  "+meanTime);
		war.setDiskAccesses(0);
	}
	
	public static void methodB(WrittingAndReading war,IndexFileClass indexFile, int stringSize, int numOfData) throws IOException {
		long timeStart, timeEnd;
		float meanTime;
		float meanDiskAccesses;
		int[] keys;
		int[] keysToSearch;
		
		//Create DataClass instances
		DataClass[] dataInstances = new DataClass[numOfData];
		
		//Check if the numOfData is 1000. If true => generate random non unique keys.If false => generate random unique keys
		if(numOfData<1000) {
			keys = RandomGenerator.getNonUniqueRandomGenerator(1, 2*numOfData, numOfData);
			keysToSearch = RandomGenerator.getNonUniqueRandomGenerator(1, 2*numOfData, 1000);
		}else {
			keys = RandomGenerator.getUniqueRandomGenerator(1, 2*numOfData, numOfData);
			keysToSearch = RandomGenerator.getUniqueRandomGenerator(1, 2*numOfData, 1000);
		}
				
		String[] data = new String[numOfData];
		for(int j =0; j<data.length; j++) {
			data[j] = RandomGenerator.randomStringGenerator(stringSize);
		}
		
		for(int k = 0; k<numOfData; k++) {
			dataInstances[k] = new DataClass(keys[k], data[k]);
		}
		
		war.writeDataToFile(dataInstances, stringSize);
		PairClass[] pairs = PairClass.createPair(dataInstances);
		PairClass.savePairs(pairs, indexFile);
		
		timeStart = System.nanoTime();
		for(int i=0; i<keysToSearch.length; i++) {
			PairClass.searchKey(keysToSearch[i], indexFile);
		}
		timeEnd = System.nanoTime();
		meanTime = (float)(timeEnd-timeStart)/keysToSearch.length;
		meanDiskAccesses = (float)indexFile.getPairDiskAcc()/keysToSearch.length;
		
		System.out.println("For: "+numOfData+" Mean Disk Accesses are: "+meanDiskAccesses+" , in mean time:  "+meanTime);
		indexFile.setPairDiskAcc(0);
		indexFile.reset();
	}
	
	public static void methodC(WrittingAndReading war, IndexFileClass indexFile, int stringSize, int numOfData) throws IOException {
		long timeStart, timeEnd;
		float meanTime;
		float meanDiskAccesses;
		int[] keys;
		int[] keysToSearch;
		
		//Create DataClass instances
		DataClass[] dataInstances = new DataClass[numOfData];
				
		//Check if the numOfData is 1000. If true => generate random non unique keys.If false => generate random unique keys
		if(numOfData<1000) {
			keys = RandomGenerator.getNonUniqueRandomGenerator(1, 2*numOfData, numOfData);
			keysToSearch = RandomGenerator.getNonUniqueRandomGenerator(1, 2*numOfData, 1000);
		}else {
			keys = RandomGenerator.getUniqueRandomGenerator(1, 2*numOfData, numOfData);
			keysToSearch = RandomGenerator.getUniqueRandomGenerator(1, 2*numOfData, 1000);
		}
		
		String[] data = new String[numOfData];
		for(int j =0; j<data.length; j++) {
			data[j] = RandomGenerator.randomStringGenerator(stringSize);
		}
		
		for(int k = 0; k<numOfData; k++) {
			dataInstances[k] = new DataClass(keys[k], data[k]);
		}
		
		war.writeDataToFile(dataInstances, stringSize);
		PairClass[] pairs = PairClass.createPair(dataInstances);
		Arrays.sort(pairs);
		PairClass.savePairs(pairs, indexFile);
		BinarySearch bs= new BinarySearch(indexFile);

		timeStart = System.nanoTime();
		for(int i=0; i<keysToSearch.length; i++) {
			bs.search(keysToSearch[i]);
		}
		timeEnd = System.nanoTime();
		meanTime = (float)(timeEnd-timeStart)/keysToSearch.length;
		meanDiskAccesses = (float)indexFile.getPairDiskAcc()/keysToSearch.length;
		
		System.out.println("For: "+numOfData+" Mean Disk Accesses are: "+meanDiskAccesses+" , in mean time:  "+meanTime);
		indexFile.setPairDiskAcc(0);
		indexFile.reset();
	}

	public static void main(String[] args) throws IOException {
		int[] N = {50,100,200,500,800, 1000, 2000, 5000, 10000, 50000 ,100000, 200000};
		
		
		File fileSize55 = new  File("fileSize55");
		fileSize55.delete();
		fileSize55.createNewFile();
		WrittingAndReading war_data55 = new WrittingAndReading(fileSize55);
		

		File indexFile55 = new File("indexFile55");
		indexFile55.delete();
		indexFile55.createNewFile();
		IndexFileClass iFile_55 = new IndexFileClass(indexFile55);
		
		File binaryFile55 = new File("binaryFile55");
		binaryFile55.delete();
		binaryFile55.createNewFile();
		IndexFileClass binFile_55 = new IndexFileClass(binaryFile55);
		
		
		File fileSize27 = new  File("fileSize27");
		fileSize55.delete();
		fileSize55.createNewFile();
		WrittingAndReading war_data27 = new WrittingAndReading(fileSize27);
		
		File indexFile27 = new File("indexFile27");
		indexFile55.delete();
		indexFile55.createNewFile();
		IndexFileClass iFile_27 = new IndexFileClass(indexFile27);

		File binaryFile27 = new File("binaryFile27");
		binaryFile27.delete();
		binaryFile27.createNewFile();
		IndexFileClass binFile_27 = new IndexFileClass(binaryFile27);
		
		
		/***********************METHOD A*************************/
		System.out.println("----------------Method A-----------------");
		System.out.println("Running for byte number 55.");
		
		for(int i = 0; i<N.length; i++) {
			methodA(war_data55, 55, N[i]);
		}
		
		System.out.println("\nRunning for byte number 27.");
		
		for(int i = 0; i<N.length; i++) {
			methodA(war_data27, 27, N[i]);
		}

		/***********************METHOD B************************/
		System.out.println("----------------Method B-----------------");
		System.out.println("Running for byte number 55.");
		
		for(int i = 0; i<N.length; i++) {
			methodB(war_data55,iFile_55, 55, N[i]);
		}
		
		System.out.println("\nRunning for byte number 27.");
		
		for(int i = 0; i<N.length; i++) {
			methodB(war_data27,iFile_27, 17, N[i]);
		}
		
		/***********************METHOD C************************/
		System.out.println("----------------Method C-----------------");
		System.out.println("Running for byte number 55.");
		for(int i = 0; i<N.length; i++) {
			methodC(war_data55,binFile_55, 55, N[i]);
		}

		System.out.println("\nRunning for byte number 27.");
		for(int i = 0; i<N.length; i++) {
			methodC(war_data27,binFile_27, 27, N[i]);
		}
	}
}
