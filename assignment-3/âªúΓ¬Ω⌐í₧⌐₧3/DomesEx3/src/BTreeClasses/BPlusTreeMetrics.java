package BTreeClasses;

public class BPlusTreeMetrics {
    private int nodeAccessCount;
    private int comparisonCount;
    
    public BPlusTreeMetrics() {
        nodeAccessCount = 0;
        comparisonCount = 0;
    }
    
    public int incrementNodeAccessCount() {
        nodeAccessCount++;
        return nodeAccessCount;
    }
    
    public int incrementComparisonsCount() {
        comparisonCount++;
        return comparisonCount;
    }
    
    public int getNodeAccessCount() {
        return nodeAccessCount;
    }
    
    public int getComparisonCount() {
        return comparisonCount;
    }
    
    public void resetMetrics() {
        nodeAccessCount = 0;
        comparisonCount = 0;
    }
}

