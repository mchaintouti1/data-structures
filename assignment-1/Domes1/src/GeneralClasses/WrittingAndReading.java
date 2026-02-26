package GeneralClasses;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class WrittingAndReading {

	private static final int FIELD_SIZE = 256; // Default character field size
	public static final byte[] zeroBytes = new byte[FIELD_SIZE];

	private int diskAccesses;
	private File f;
	private RandomAccessFile file;
	private ByteBuffer buf;

	private int lastWriteOffset;
	private int writeOffset;

	public WrittingAndReading(File f) throws FileNotFoundException {
		this.diskAccesses = 0;
		this.f = f;
		this.file = new RandomAccessFile(this.f.getName(), "rw");
		this.buf = ByteBuffer.allocate(DataPage.DataPageSize);
		this.lastWriteOffset = 0;
		this.writeOffset = 0;
	}

	public int getDiskAccesses() {
		return diskAccesses;
	}

	public void setDiskAccesses(int diskAccesses) {
		this.diskAccesses = diskAccesses;
	}

	public File getF() {
		return f;
	}

	public void setF(File f) {
		this.f = f;
	}

	public int getLastWriteOffset() {
		return lastWriteOffset;
	}

	public void setLastWriteOffset(int lastWriteOffset) {
		this.lastWriteOffset = lastWriteOffset;
	}

	public int getWriteOffset() {
		return writeOffset;
	}

	public void setWriteOffset(int writeOffset) {
		this.writeOffset = writeOffset;
	}

	/*
	 * public void writeDataToFile(byte[] toWrite) throws IOException { int
	 * bytesToWrite = toWrite.length; 
	 * FileOutputStream fos = new FileOutputStream(file.getFD());
	 * 
	 * writeOffset = lastWriteOffset;
	 * 
	 * while(bytesToWrite>0) { 
	 * file.seek(writeOffset*DataPage.DataPageSize);
	 * fos.write(toWrite, toWrite.length - bytesToWrite, DataPage.DataPageSize);
	 * 
	 * writeOffset++;
	 * bytesToWrite -= DataPage.DataPageSize; }
	 * 
	 * lastWriteOffset = writeOffset; }
	 */

	/**
	 * The method writes data in the file 
	 */
	public void writeDataToFile(DataClass[] dataToWrite, int dataSize) throws IOException {
		int maxDataPerDP = (int) Math.floor((float) DataPage.DataPageSize / (dataSize + Integer.BYTES));

		for (int k = 0; k < dataToWrite.length; k += maxDataPerDP) {
			for (int i = k; i < k + maxDataPerDP && i < dataToWrite.length; i++) {
				buf.putInt(dataToWrite[i].getKey());
				buf.put(dataToWrite[i].getData().getBytes(StandardCharsets.US_ASCII));
			}

			file.write(buf.array());
			writeOffset++;

			// clear buffer
			buf.rewind();
			buf.put(zeroBytes);
			buf.rewind();

		}

		lastWriteOffset = writeOffset;
	}

	/**
	 * The method reads bytes from the file in pages
	 */
	public byte[] read(int dataPageOffset) throws IOException {

		if (dataPageOffset > lastWriteOffset)
			return null;

		FileInputStream fis = new FileInputStream(file.getFD());
		byte[] toRead = new byte[DataPage.DataPageSize];

		file.seek(dataPageOffset * DataPage.DataPageSize);
		fis.read(toRead, 0, DataPage.DataPageSize);
		diskAccesses++;

		return toRead;

	}

	public DataClass[] retrieveData(int stringSize, int dpOffset) throws IOException {
		byte[] bytesRead = this.read(dpOffset);
		DataClass[] toReturn = DataClass.byteToDataArray(stringSize, bytesRead);

		return toReturn;

	}

}
