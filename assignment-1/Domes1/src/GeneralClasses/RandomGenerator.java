package GeneralClasses;

public class RandomGenerator {

	private static int minIntNumber;
	private static int maxIntNumber;
	private static int numberOfNumbers;
	//public static int[] randomKeys;
	
	public RandomGenerator(int minIntNumber,int maxIntNumber,int numberOfNumbers) {
		this.maxIntNumber = maxIntNumber;
		this.minIntNumber = minIntNumber;
		this.numberOfNumbers = numberOfNumbers;
		
	}
	
	/**
	 * Method used to create unique random numbers
	 */
	public static int[] getUniqueRandomGenerator(int min, int max, int numOfInstances) {
	java.util.Random randomGenerator = new java.util.Random();
	int[] randomKeys = randomGenerator.ints(min,
	max).distinct().limit(numOfInstances).toArray();
	
	return randomKeys;
	}

	/**
	 * Method used to create non-unique random numbers
	 */
	public static int[] getNonUniqueRandomGenerator(int min, int max, int numOfInstances) {
		java.util.Random randomGenerator = new java.util.Random();
		int[] randomKeys = randomGenerator.ints(min,
		max).limit(numOfInstances).toArray();
		
		return randomKeys;
	}
	
	/**
	 * Method used to create unique random numbers
	 */
	public static String randomStringGenerator(int n) {
		String dataString =  " !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~"; 
		StringBuilder sb = new StringBuilder(n);
		
		for(int i = 0; i<n; i++) {
			int index = (int)(dataString.length()*Math.random());
			
			sb.append(dataString.charAt(index));
		}
		
		return sb.toString();
	}
	
}
