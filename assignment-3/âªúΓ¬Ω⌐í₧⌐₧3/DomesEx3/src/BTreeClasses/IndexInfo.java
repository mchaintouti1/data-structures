package BTreeClasses;

public class IndexInfo {
	private String fileName;
	private int position;
	
	public IndexInfo(String fileName, int position) {
		this.fileName = fileName;
		this.position = position;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public int getPosition() {
		return position;
	}

}
