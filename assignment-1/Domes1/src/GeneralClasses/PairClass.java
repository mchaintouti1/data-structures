package GeneralClasses;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class PairClass implements Comparable<PairClass>{
	private int key;
	private int pagePosition;
	
	public PairClass(int key, int pagePosition) {
		this.key = key;
		this.pagePosition = pagePosition;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public int getPagePosition() {
		return pagePosition;
	}

	public void setPagePosition(int pagePosition) {
		this.pagePosition = pagePosition;
	}
	
	public static PairClass[] createPair(DataClass[] dc) {
		int offset = 0;
		PairClass[] pc = new PairClass[dc.length];
		int amountOfDC = (int) Math.floor(DataPage.DataPageSize/(dc[0].getStringSize()+Integer.BYTES));
		
		for(int i = 0; i<dc.length; i++) {
			pc[i] = new PairClass(dc[i].getKey(), offset);
			
			if(i%amountOfDC==0 && i!=0)
				offset++;
		}
		
		return pc;
	}

	public static void savePairs(PairClass[] pc, IndexFileClass iFile) throws IOException {
		byte[] bytesToStore = pairToByteArray(pc);
		
		iFile.writeIndex(bytesToStore);
	}
	
	/**
     * This method is used to convert our data to byte array
     */
	public static byte[] pairToByteArray(PairClass[] dataArray) {
		ByteBuffer buffer = ByteBuffer.allocate(DataPage.DataPageSize);
		
		byte[] byteArray = new byte[Integer.BYTES*2*dataArray.length];
		int offset = 0;
		
		for(int i=0; i<dataArray.length; i++) {
			buffer.putInt(dataArray[i].getKey());
			buffer.putInt(dataArray[i].getPagePosition());
			
			if(buffer.remaining()<Integer.BYTES*2 || buffer.remaining() == 0) {
				System.arraycopy(buffer.array(), 0, byteArray, offset*DataPage.DataPageSize, DataPage.DataPageSize);
				offset++;
				buffer.clear();
				Arrays.fill(buffer.array(), (byte)0);
				
			}
		}
			
		return byteArray;
	}
	
	 /**
     * This method is used to convert the byte array to pair array
     */
	public static PairClass[] byteToPairArray(byte[] bytes) {
		if(bytes== null)
			return null;

		int amountOfPC = bytes.length/(Integer.BYTES*2);
		PairClass[] toReturn = new PairClass[amountOfPC];
		
		ByteBuffer buffer = ByteBuffer.wrap(bytes);
		for(int j = 0; j<amountOfPC; j++) {
			int key = buffer.getInt();
			int pagePosition = buffer.getInt();
			
			 toReturn[j] = new PairClass(key, pagePosition);
		}
		
		return toReturn;
	}
	
	/**
     * Method used to search for key in the PairClass array
	 * @throws IOException 
     */
	public static PairClass searchKey(int key, IndexFileClass iFile) throws IOException {
		int offset = 0;
		byte[] bytesRead = iFile.readIndex(offset++); 
		PairClass[] pc;
		
		while(bytesRead!=null) {
			pc = byteToPairArray(bytesRead);
			
			if(pc == null)
				break;
			
			for(int i = 0; i<pc.length; i++) {
				if(pc[i].getKey() == key)
					return pc[i];
			}
			
			bytesRead = iFile.readIndex(offset++);
		}
		return null;
	}
	
	@Override
	public int compareTo(PairClass p) {
		if(this.key> p.key)
			return 1;
		else if (this.key< p.key)
			return -1;
		else
			return 0;
	}
}
