package GeneralClasses;

import java.nio.ByteBuffer;

public class DataPage {
	public static final int DataPageSize = 256; //Default data page size
	private static ByteBuffer buffer;
	
	public DataPage() {
		DataPage.buffer = ByteBuffer.allocate(DataPageSize);
	}
	

	/**
	 *Method to get the byte representation of the page
	 */
	public static byte[] getBytes() {
		byte[] pageBytes = buffer.array();
		int pageOffset = buffer.arrayOffset();
		int position = buffer.position();
		byte[] bytes = new byte[position];
		
		System.arraycopy(pageBytes, 0, bytes, pageOffset, position);
		
		return bytes;
		}
}
