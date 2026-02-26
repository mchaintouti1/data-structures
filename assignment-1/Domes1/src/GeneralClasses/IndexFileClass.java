package GeneralClasses;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;


public class IndexFileClass {	
	private File indexFile;
	private RandomAccessFile iFile;
	
	private int lastWriteOffset;
	private int writeOffset;
	
	private int pairDiskAcc;
	
	public IndexFileClass(File indexFile) throws FileNotFoundException {
		this.indexFile = indexFile;
		this.iFile = new RandomAccessFile(this.indexFile.getName(), "rw");
		this.lastWriteOffset = 0;
		this.writeOffset = 0;
		this.pairDiskAcc = 0;
	}
	
	public void reset() {
		this.lastWriteOffset = 0;
		this.writeOffset = 0;
		this.pairDiskAcc = 0;
	}

	public File getIndexFile() {
		return indexFile;
	}

	public void setIndexFile(File indexFile) {
		this.indexFile = indexFile;
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
	
	public int getPairDiskAcc() {
		return pairDiskAcc;
	}


	public void setPairDiskAcc(int pairDiskAcc) {
		this.pairDiskAcc = pairDiskAcc;
	}

	
	public void writeIndex(byte[] data) throws IOException {
		int bytesToWrite = data.length;
		
		FileOutputStream fos = new FileOutputStream(iFile.getFD());
		
		writeOffset = lastWriteOffset;
		
		while(bytesToWrite>0) {
			iFile.seek(writeOffset*DataPage.DataPageSize);
			if(bytesToWrite>= DataPage.DataPageSize) {
				fos.write(data, data.length-bytesToWrite, DataPage.DataPageSize);
			}else {
				fos.write(data, data.length-bytesToWrite, bytesToWrite);
			}
			writeOffset++;
			bytesToWrite -= DataPage.DataPageSize;
		}
		
		lastWriteOffset = writeOffset;
		
	}
	
	
	public byte[] readIndex(int dpOffset) throws IOException {
		if (dpOffset > lastWriteOffset)
			return null;
		
		FileInputStream fis = new FileInputStream(iFile.getFD());
		byte[] toRead = new byte[DataPage.DataPageSize];
		
		iFile.seek(dpOffset*DataPage.DataPageSize);
		fis.read(toRead, 0, DataPage.DataPageSize);	
		pairDiskAcc++;
		
		return toRead;

	}

}
