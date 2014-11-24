package memory;

import instructions.Instruction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import utilities.CacheDetailsHolder;
import utilities.Pair;

public class Cache {
	private HashMap<String, CacheLineSet<Instruction>> iCache = new HashMap<String, CacheLineSet<Instruction>>();
	private HashMap<String, CacheLineSet<String>> dCache = new HashMap<String, CacheLineSet<String>>();
	private Cache lowerLevelCache = null;
	private boolean isWriteBack = false;
	private boolean isWriteAllocate = false;
	private int accessTime = 0;
	private int numberOfHits = 0;
	private int numberOfIssues = 0;
	private int size = 0;
	private int lineSize = 0;
	private int associativity = 0;


	public Cache(	ArrayList<CacheDetailsHolder> caches,
					Instruction[] instruction, 
					int instructionStartAddress, 
					HashMap<Integer, Integer> data) {
		
		CacheDetailsHolder details = caches.get(0);
		this.isWriteBack = details.isWriteBack();
		this.isWriteAllocate = details.isWriteAllocate();
		this.accessTime = details.getAccessTime();
		this.size = details.getSize();
		this.lineSize = details.getLineSize();
		this.associativity = details.getAssociativity();
		
		for(int q = 0; q < size; q++){
			dCache.put(q + "", new CacheLineSet<String>(associativity, lineSize));
			iCache.put(q + "", new CacheLineSet<Instruction>(associativity, lineSize));
		}
		
		if(caches.size() > 1){
			caches.remove(0);
			lowerLevelCache = new Cache(caches, instruction, instructionStartAddress, data);
			return;
		}
		
		for(int q = 0; q < instruction.length; q++){
			CacheLine<Instruction> line = new CacheLine<Instruction>(lineSize);
			line.setBlock(0, instruction[q]);
			line.setTag("0");
			
			CacheLineSet<Instruction> lineSet = new CacheLineSet<Instruction>(associativity, lineSize);
			lineSet.insert(0, line);
			
			iCache.put((instructionStartAddress + 2 * q) + "", lineSet);
		}
		
		Set<Integer> keys = data.keySet();
		for(Integer key : keys){
			String value = data.get(key).toString();
			
			CacheLine<String> line = new CacheLine<String>(lineSize);
			line.setBlock(0, value);
			line.setTag("0");
			
			CacheLineSet<String> lineSet = new CacheLineSet<String>(associativity, lineSize);
			lineSet.insert(0, line);
			
			dCache.put(key.toString(), lineSet);
		}
	}
	
	public Instruction readInstruction(int address){
		CacheLine<Instruction> line = readInstructionLine(address);
		return line.getBlock(address % lineSize);
	}


	private CacheLine<Instruction> readInstructionLine(int address) {
		address *= 2;
		
		numberOfIssues++;
		
		int index = (address / lineSize) % (size / associativity);
		int tag = address / (lineSize * size / associativity);
		
		CacheLineSet<Instruction> lineSet = iCache.get(index + "");
		int cacheLineIndex = lineSet.searchTags(tag);
		
		if(cacheLineIndex == -1){
			// Handle read miss
			CacheLine<Instruction> newCacheLine = lowerLevelCache.readInstructionLine(address);
			
			// Get replaced Line
			int replacedLineIndex = lineSet.getLineIndexToReplace();
			CacheLine<Instruction> toReplaceLine = lineSet.getCacheLine(replacedLineIndex);
			
			// Check for dirty bit
			if(toReplaceLine.getTag() != null && toReplaceLine.isDirty()){
				lowerLevelCache.writeInstructionLine(index, toReplaceLine);
			}
			
			// Replace it
			newCacheLine.setDirty(false);
			lineSet.insert(replacedLineIndex, newCacheLine);
			
			return newCacheLine;
		}
		
		numberOfHits++;
		
		return lineSet.getCacheLine(cacheLineIndex);
	}

	private void writeInstructionLine(int index,
			CacheLine<Instruction> replacedLine) {
		numberOfIssues++;
		
		CacheLineSet<Instruction> lineSet = iCache.get(index + "");
		int lineIndex = lineSet.searchTags(replacedLine.getTag());
		
		if(lineIndex == -1){
			lowerLevelCache.writeInstructionLine(index, replacedLine);
			
			if(isWriteAllocate){
				int toReplaceLineIndex = lineSet.getLineIndexToReplace();
				CacheLine<Instruction> toReplaceLine = lineSet.getCacheLine(toReplaceLineIndex);
				
				if(toReplaceLine.getTag() != null && toReplaceLine.isDirty()){
					lowerLevelCache.writeInstructionLine(index, toReplaceLine);
				}
				
				replacedLine.setDirty(false);
				lineSet.insert(toReplaceLineIndex, replacedLine);
			}
			
			return;
		}
		
		numberOfHits++;
		
		lineSet.insert(lineIndex, replacedLine);
		
		if(isWriteBack){
			replacedLine.setDirty(true);
		}
		else{
			replacedLine.setDirty(false);
			lowerLevelCache.writeInstructionLine(index, replacedLine);
		}
	}

	public String readData(int address){
		CacheLine<String> line = readLine(address);
		return line.getBlock(address % lineSize);
	}
	
	public CacheLine<String> readLine(int address){
		address *= 2;
		
		numberOfIssues++;
		
		int index = (address / lineSize) % (size / associativity);
		int tag = address / (lineSize * size / associativity);
		
		CacheLineSet<String> lineSet = dCache.get(index + "");
		int cacheLineIndex = lineSet.searchTags(tag);
		
		if(cacheLineIndex == -1){
			// Handle read miss
			CacheLine<String> newCacheLine = lowerLevelCache.readLine(address);
			
			// Get replaces Line
			int replacedLineIndex = lineSet.getLineIndexToReplace();
			CacheLine<String> toReplaceLine = lineSet.getCacheLine(replacedLineIndex);
			
			// Check for dirty bit
			if(toReplaceLine.getTag() != null && toReplaceLine.isDirty()){
				lowerLevelCache.writeLine(index, toReplaceLine);
			}
			// Replace it
			
			newCacheLine.setDirty(false);
			lineSet.insert(replacedLineIndex, newCacheLine);
			
			return newCacheLine;
		}
		
		numberOfHits++;
		
		return lineSet.getCacheLine(cacheLineIndex);
	}
	
	private void writeLine(int index, CacheLine<String> replacedLine) {
		numberOfIssues++;
		
		CacheLineSet<String> lineSet = dCache.get(index + "");
		int lineIndex = lineSet.searchTags(replacedLine.getTag());
		
		if(lineIndex == -1){
			lowerLevelCache.writeLine(index, replacedLine);
			
			if(isWriteAllocate){
				int toReplaceLineIndex = lineSet.getLineIndexToReplace();
				CacheLine<String> toReplaceLine = lineSet.getCacheLine(toReplaceLineIndex);
				
				if(toReplaceLine.getTag() != null && toReplaceLine.isDirty()){
					lowerLevelCache.writeLine(index, toReplaceLine);
				}
				
				replacedLine.setDirty(false);
				lineSet.insert(toReplaceLineIndex, replacedLine);
			}
			
			return;
		}
		
		numberOfHits++;
		
		lineSet.insert(lineIndex, replacedLine);
		
		if(isWriteBack){
			replacedLine.setDirty(true);
		}
		else{
			replacedLine.setDirty(false);
			lowerLevelCache.writeLine(index, replacedLine);
		}
	}


	public void writeData(int address, String value){
		address *= 2;
		
		numberOfIssues++;
		
		int offset = address % lineSize;
		int index = (address / lineSize) % (size / associativity);
		int tag = address / (lineSize * size / associativity);
		
		CacheLineSet<String> lineSet = dCache.get(index + "");
		int cacheLineIndex = lineSet.searchTags(tag);
		
		if(cacheLineIndex == -1){
			// Handle write miss
			lowerLevelCache.writeData(address, value);
			
			if(isWriteAllocate){
				CacheLine<String> line = lowerLevelCache.readLine(address);
				line.setBlock(offset, value);
				
				int toReplaceLineIndex = lineSet.getLineIndexToReplace();
				CacheLine<String> toReplaceLine = lineSet.getCacheLine(toReplaceLineIndex);
				
				if(toReplaceLine.getTag() != null && toReplaceLine.isDirty()){
					lowerLevelCache.writeLine(index, toReplaceLine);
				}
				
				line.setDirty(false);
				lineSet.insert(toReplaceLineIndex, line);
			}
			
			return;
		}
		
		numberOfHits++;
		
		// Handle write hit
		
		CacheLine<String> line = lineSet.getCacheLine(cacheLineIndex);
		line.setBlock(offset, value);
		
		if(isWriteBack){
			line.setDirty(true);
		}
		else{
			line.setDirty(false);
			lowerLevelCache.writeData(address, value);
		}
	}
	
	public ArrayList<Pair<Integer, Integer>> getCacheStats() {
		ArrayList<Pair<Integer, Integer>> result = new ArrayList<Pair<Integer, Integer>>();
		if(lowerLevelCache != null){
			result = lowerLevelCache.getCacheStats();
		}
		result.add(0, new Pair<Integer, Integer>(new Integer(numberOfHits), new Integer(numberOfIssues)));
		
		return result;
	}


	public double getAMAT() {
		if(lowerLevelCache == null){
			return accessTime;
		}
		return numberOfHits * 1.0 / numberOfIssues * accessTime + (numberOfIssues - numberOfHits) * 1.0 / numberOfIssues * lowerLevelCache.getAMAT();
	}
}
