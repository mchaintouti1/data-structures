package GeneralClasses;

import java.io.IOException;

public class BinarySearch {
	private IndexFileClass iFile;
	
	public BinarySearch(IndexFileClass ifc) {
		this.iFile= ifc;
	}

	/**
	 * Binary in the Index File for a given key
	 *
	 * @throws IOException
	 */
	public PairClass search(int key) throws IOException {
		//If Index File is undefined, return null
		if(this.iFile == null)
			return null;

		//Else binary search in the Index File
		return doBSearch(key);
	}
	
	/**
	 * Searches data array from left index to right index for given key.
	 * It returns the key if found, otherwise Integer.MIN_VALUE
	 * @throws IOException 
	 */
	public PairClass doBSearch(int key) throws IOException {
		PairClass[] pcsRetrieved; //Retrieved PairClasses from the Index File
		byte[] bytesRead; //Read bytes from Index File

		int leftIndex = 0;
		int rightIndex = (int) Math.ceil((double)iFile.getIndexFile().length()/DataPage.DataPageSize);
		int mid= (leftIndex+ rightIndex)/2;

		bytesRead = iFile.readIndex(mid);
		while(rightIndex >= leftIndex) {
			pcsRetrieved = PairClass.byteToPairArray(bytesRead);
			if(pcsRetrieved== null)
				return null;

			for(int i= 0; i< pcsRetrieved.length; i++) {
				if(pcsRetrieved[i].getKey()== key)
					return pcsRetrieved[i];
				//If key value is 0, the file has ended and the key doesn't exist here
				else if(pcsRetrieved[i].getKey()== 0)
					return null;
			}

			if(key< pcsRetrieved[0].getKey())
				rightIndex= mid-1;
			else
				leftIndex= mid+1;

			//mid position calculation
			mid = (int) Math.floor(leftIndex + (rightIndex - leftIndex) / 2);
		 	bytesRead = iFile.readIndex(mid);
		}
		
		//If key is not found, null is returned,
		return null;
	}
}
