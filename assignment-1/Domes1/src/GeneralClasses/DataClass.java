package GeneralClasses;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class DataClass {
	public static final int stringSize55 = 55;
	public static final int stringSize27 = 27;
	//public static int pagePosition; 
	
	private int key;
	private String data;
	private int stringSize;
	
	public DataClass(int key, String data) {
		this.data = data;
		this.key = key;
		this.stringSize = data.length();	
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int getStringSize() {
		return stringSize;
	}

	public void setStringSize(int stringSize) {
		this.stringSize = stringSize;
	}
	
	/**
	 * This method is used to convert our data to byte array
	 */
	public static byte[] dataToByteArray(DataClass[] dataArray){
		ByteBuffer buf = ByteBuffer.allocate(DataPage.DataPageSize);
		
		int dataSize = dataArray[0].stringSize + Integer.BYTES;
		int dataByteSize = (dataArray[0].stringSize + Integer.BYTES)*dataArray.length;
		int nDPs = (int)Math.ceil((double)dataByteSize/DataPage.DataPageSize);
		byte[] byteArray = new byte[nDPs*DataPage.DataPageSize];
		int offsetInByteArray = 0;
		
		for(int i=0; i<dataArray.length; i++) {
			buf.putInt(dataArray[i].key);
			buf.put(dataArray[i].data.getBytes(StandardCharsets.US_ASCII));
			
			if(buf.remaining()<dataSize || buf.remaining() == 0) {
				System.arraycopy(buf.array(), 0, byteArray, offsetInByteArray*DataPage.DataPageSize, DataPage.DataPageSize);
				offsetInByteArray++;
				buf.clear();
				Arrays.fill(buf.array(), (byte)0);
			}
		}
		
		return byteArray;
	}
	
	
	/**
	 * This method is used to convert the byte array to data
	 */
	public static DataClass[] byteToDataArray(int stringSize, byte[] bytes) {
		int amountOfDC = (int) Math.floor(DataPage.DataPageSize/(stringSize+Integer.BYTES));
		DataClass[] toReturn = new DataClass[amountOfDC];
		
		int amountOfConvDC = 0; //Amount of converted DataClasses
		
		ByteBuffer buf = ByteBuffer.wrap(bytes);
		for(int j = 0; j<amountOfDC; j++) {
			int key = buf.getInt();
			
			//Check if a DP has not 4 elements to return 
			if(key==0) {
				if(amountOfConvDC == 0)
					return null;
				else {
					DataClass[] tR = new DataClass[amountOfConvDC];
					System.arraycopy(toReturn, 0, tR, 0, amountOfConvDC);
					return tR;
				}
			}
			//temporary variable tmp to handle a varying size of bytes	
			byte[] tmp = new byte[stringSize];
			buf.get(tmp);
			String data = new String(tmp, StandardCharsets.US_ASCII);
			
			toReturn[j] = new DataClass(key, data); 
		}
		
		return toReturn;
	}
	
	/**
	 *Method used to search for key  in the DataClass
	 */
	public static DataClass searchInDataFile(int key, int stringSize, WrittingAndReading war) throws IOException{
		int offset = 0;
		byte[] bytesRead = war.read(offset++);
		DataClass[] dc;
		
		while(bytesRead!=null) {
			dc = byteToDataArray(stringSize, bytesRead);
				
			if(dc==null)
				break;
			
			for(int i=0; i<dc.length; i++) {
				if(dc[i].key == key) {
					return dc[i];
				}
			}
			
			bytesRead = war.read(offset++);
		}
		
		return null;
	}
}
