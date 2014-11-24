package memory;

import instructions.Instruction;

import java.util.ArrayList;
import java.util.HashMap;

import utilities.CacheDetailsHolder;

public class Cache {
	private HashMap<String, Instruction> iCache = new HashMap<String, Instruction>();
	private HashMap<String, CacheLineSet> dCache = new HashMap<String, CacheLineSet>();
	private Cache lowerLevelCache = null;
	private boolean isWriteBack = false;
	private boolean isWriteAllocate = false;
	private int accessTime = 0;
	private int numberOfHits = 0;
	private int numberOfIssues = 0;
	private int size = 0;
	private int lineSize = 0;
	private int associativity = 0;
	
	public Cache(	int size, 
					int lineSize, 
					int associativity, 
					boolean isWriteBack, 
					boolean isWriteAllocate, 
					int accessTime){
		
		this.isWriteBack = isWriteBack;
		this.isWriteAllocate = isWriteAllocate;
		this.accessTime = accessTime;
		this.size = size;
		this.lineSize = lineSize;
		this.associativity = associativity;
	}


	public Cache(	ArrayList<CacheDetailsHolder> caches,
					Instruction[] instruction, 
					int instructionStartAddress, 
					HashMap<Integer, Integer> data) {
		// TODO Auto-generated constructor stub
	}
	
	public Instruction readInstruction(int address){
		numberOfIssues++;
		
		int offset = address % lineSize;
		int index = (address / lineSize) % (size / associativity);
		int tag = address / (lineSize * size / associativity);
		
		CacheLineSet lineSet = dCache.get(index);
		
		/*if(result == null){
			// Handle read miss
			//result = lowerLevelCache.readInstruction(address);
			//insertInstruction(address, result);
		}
		else{
			numberOfHits++;
		}*/
		
		return null;
	}
	
	private void insertInstruction(int address, Instruction result) {
		
	}


	public String readData(int address){
		address *= 2;
		
		numberOfIssues++;
		
		int offset = address % lineSize;
		int index = (address / lineSize) % (size / associativity);
		int tag = address / (lineSize * size / associativity);
		
		CacheLineSet lineSet = dCache.get(index);
		int cacheLineIndex = lineSet.searchTags(tag);
		
		if(cacheLineIndex == -1){
			// Handle read miss
			int startAddress = address - address % lineSize;
			ArrayList<String> newLine = new ArrayList<String>();
			for(int q = 0; q < lineSize; q++){
				String word = lowerLevelCache.readData(startAddress + q);
				newLine.add(word);
			}
			
			CacheLine newCacheLine = new CacheLine(lineSize);
			newCacheLine.setLine(newLine);
			
			// Get replaces Line
			CacheLine replacedLine = lineSet.getLineIndexToReplace();
			
			// Check for dirty bit
			if(replacedLine.getTag() != null && replacedLine.isDirty()){
				lowerLevelCache.writeLine(index, replacedLine);
			}
			// Replace it
			replacedLine = newCacheLine;
			
			return newCacheLine.getBlock(offset);
		}
		
		numberOfHits++;
		
		CacheLine line = lineSet.getCacheLine(cacheLineIndex);
		return line.getBlock(offset);
	}
	
	private void writeLine(int index, CacheLine replacedLine) {
		numberOfIssues++;
		
		CacheLineSet lineSet = dCache.get(index);
		int lineIndex = lineSet.searchTags(replacedLine.getTag());
		
		if(lineIndex == -1){
			lowerLevelCache.writeLine(index, replacedLine);
			
			if(!isWriteAllocate){
				CacheLine toReplaceLine = lineSet.getLineIndexToReplace();
				if(toReplaceLine.getTag() != null && toReplaceLine.isDirty()){
					lowerLevelCache.writeLine(index, toReplaceLine);
				}
				
				toReplaceLine = replacedLine;
				toReplaceLine.setDirty(false);
			}
			
			return;
		}
		
		numberOfHits++;
		
		CacheLine oldLine = lineSet.getCacheLine(lineIndex);
		oldLine = replacedLine;
		
		if(isWriteBack){
			replacedLine.setDirty(true);
		}
		else{
			replacedLine.setDirty(false);
			lowerLevelCache.writeLine(index, replacedLine);
		}
	}


	public void writeData(int address, String value){
		
	}
	
	public double[] getStatistics(){
		return null;
	}
}
