package memory;

import instructions.Instruction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import com.jgoodies.forms.util.Utilities;

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
			String tag = null;
			if(caches.size() == 1){
				tag = "0";
			}
			CacheLineSet<String> dCacheLineSet = new CacheLineSet<String>(associativity, lineSize, tag);
			CacheLineSet<Instruction> iCacheLineSet = new CacheLineSet<Instruction>(associativity, lineSize, tag);
			
			dCache.put(q + "", dCacheLineSet);
			iCache.put(q + "", iCacheLineSet);
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
			
			iCache.put((instructionStartAddress + q) + "", lineSet);
		}
		
		for(int q = 0; q < size; q++){
			String value = "0";
			
			CacheLine<String> line = new CacheLine<String>(lineSize);
			line.setBlock(0, value);
			line.setTag("0");
			
			CacheLineSet<String> lineSet = new CacheLineSet<String>(associativity, lineSize);
			lineSet.insert(0, line);
			
			dCache.put(q + "", lineSet);
		}
		
		Set<Integer> keys = data.keySet();
		for(Integer key : keys){
			String value = data.get(key).toString();
			String binValue = utilities.Utilities.getBinaryNumber(Integer.parseInt(value), 16);
			
			CacheLine<String> line = new CacheLine<String>(lineSize);
			line.setBlock(0, binValue);
			line.setTag("0");
			
			CacheLineSet<String> lineSet = new CacheLineSet<String>(associativity, lineSize);
			lineSet.insert(0, line);
			
			dCache.put(key.toString(), lineSet);
		}
	}
	
	public Instruction readInstruction(int address){
		return readInstruction(address, true);
	}
	
	public Instruction readInstruction(int address, boolean considerIt){
		CacheLine<Instruction> line = readInstructionLine(address, considerIt);
		return line.getBlock(address % lineSize);
	}


	private CacheLine<Instruction> readInstructionLine(int address, boolean considerIt) {
		if(considerIt){
			numberOfIssues++;
		}
		
		int index = (address / lineSize) % (size / associativity);
		int tag = address / (lineSize * size / associativity);

		CacheLineSet<Instruction> lineSet = iCache.get(index + "");
		int cacheLineIndex = lineSet.searchTags(tag);
		
		if(cacheLineIndex == -1){
			// Handle read miss
			
			CacheLine<Instruction> newCacheLine = new CacheLine<Instruction>(lineSize);
			newCacheLine.setTag(tag + "");
			
			int lowerLevelLineSize = lowerLevelCache.lineSize;
			int currentBaseAddress = address - address % lineSize;
			int currentAdd = currentBaseAddress % lowerLevelLineSize;
			CacheLine<Instruction> lowerCacheLine = lowerLevelCache.readInstructionLine(currentBaseAddress + currentAdd, true);
			
			int i = 0;
			for(int q = 0; q < lineSize; q++){
				if(i == lowerLevelLineSize){
					i = 0;
					currentAdd += lowerLevelLineSize;
					lowerCacheLine = lowerLevelCache.readInstructionLine(currentBaseAddress + currentAdd, true);
				}
				Instruction value = lowerCacheLine.getBlock(i);
				newCacheLine.setBlock(q, value);
				i++;
			}
			
			// Get replaced Line
			int replacedLineIndex = lineSet.getLineIndexToReplace();
			CacheLine<Instruction> toReplaceLine = lineSet.getCacheLine(replacedLineIndex);
			
			// Check for dirty bit
			if(toReplaceLine.getTag() != null && toReplaceLine.isDirty()){
				lowerLevelCache.writeInstructionLine(index, toReplaceLine, address);
			}
			
			// Replace it
			newCacheLine.setDirty(false);
			lineSet.insert(replacedLineIndex, newCacheLine);
			
			return newCacheLine;
		}
		
		if(considerIt){
			numberOfHits++;
		}
		
		return lineSet.getCacheLine(cacheLineIndex);
	}

	private void writeInstructionLine(int index, CacheLine<Instruction> replacedLine, int absoluteAddress) {
		numberOfIssues++;
		
		CacheLineSet<Instruction> lineSet = iCache.get(index + "");
		int lineIndex = lineSet.searchTags(replacedLine.getTag());
		
		if(lineIndex == -1){
			int i = 0, j = 0;
			int lowerLineSize = lowerLevelCache.lineSize;
			int currentAddress = absoluteAddress - absoluteAddress % lineSize;
			CacheLine<Instruction> lowerCacheLine = lowerLevelCache.readInstructionLine(currentAddress, false);
			while(i < lineSize){
				if(j == lowerLineSize){
					lowerLevelCache.writeInstructionLine(currentAddress / lowerLineSize, lowerCacheLine, currentAddress);
					currentAddress += lowerLineSize;
					lowerCacheLine = lowerLevelCache.readInstructionLine(currentAddress, false);
					j = 0;
				}
				lowerCacheLine.setBlock(j, replacedLine.getBlock(i));
				
				i++;
				j++;
			}
			
			lowerLevelCache.writeInstructionLine(currentAddress / lowerLineSize, lowerCacheLine, currentAddress);
			
			if(isWriteAllocate){
				int toReplaceLineIndex = lineSet.getLineIndexToReplace();
				CacheLine<Instruction> toReplaceLine = lineSet.getCacheLine(toReplaceLineIndex);
				
				if(toReplaceLine.getTag() != null && toReplaceLine.isDirty()){
					lowerLevelCache.writeInstructionLine(index, toReplaceLine, absoluteAddress);
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
			lowerLevelCache.writeInstructionLine(index, replacedLine, absoluteAddress);
		}
	}
	

	public String readData(int address){
		return readData(address, true);
	}
	
	public String readData(int address, boolean considerIt){
		CacheLine<String> line = readLine(address, considerIt);
		return line.getBlock(address % lineSize);
	}
	
	public CacheLine<String> readLine(int address, boolean considerIt){
		if(considerIt){
			numberOfIssues++;
		}
		
		int index = ((address / lineSize) % (size / associativity)) % size;
		int tag = address / (lineSize * size / associativity);
		
		CacheLineSet<String> lineSet = dCache.get(index + "");
		int cacheLineIndex = lineSet.searchTags(tag);
		
		if(cacheLineIndex == -1){
			// Handle read miss
			/*System.out.println("Read miss " + lowerLevelCache);
			int baseAddress = address - address % lineSize;
			CacheLine<String> newCacheLine = new CacheLine<String>(lineSize);
			newCacheLine.setTag(tag + "");
			
			for(int q = 0; q < lineSize; q++){
				String value = lowerLevelCache.readData(baseAddress + q, q == 0);
				newCacheLine.setBlock(q, value);
			}
			*/
			
			CacheLine<String> newCacheLine = new CacheLine<String>(lineSize);
			newCacheLine.setTag(tag + "");
			
			int lowerLevelLineSize = lowerLevelCache.lineSize;
			int currentBaseAddress = address - address % lineSize;
			int currentAdd = currentBaseAddress % lowerLevelLineSize;
			CacheLine<String> lowerCacheLine = lowerLevelCache.readLine(currentBaseAddress + currentAdd, true);
			
			int i = 0;
			for(int q = 0; q < lineSize; q++){
				if(i == lowerLevelLineSize){
					i = 0;
					currentAdd += lowerLevelLineSize;
					lowerCacheLine = lowerLevelCache.readLine(currentBaseAddress + currentAdd, true);
				}
				String value = lowerCacheLine.getBlock(i);
				newCacheLine.setBlock(q, value);
				i++;
			}
			// Get replaces Line
			int replacedLineIndex = lineSet.getLineIndexToReplace();
			CacheLine<String> toReplaceLine = lineSet.getCacheLine(replacedLineIndex);
			
			// Check for dirty bit
			if(toReplaceLine.getTag() != null && toReplaceLine.isDirty()){
				lowerLevelCache.writeLine(index, toReplaceLine, address);
			}
			// Replace it
			
			newCacheLine.setDirty(false);
			lineSet.insert(replacedLineIndex, newCacheLine);
			
			return newCacheLine;
		}
		
		if(considerIt){
			numberOfHits++;
		}
		
		System.out.println("Read hit " + lowerLevelCache);
		return lineSet.getCacheLine(cacheLineIndex);
	}
	
	private void writeLine(int index, CacheLine<String> replacedLine, int absoluteAddress) {
		numberOfIssues++;
		
		CacheLineSet<String> lineSet = dCache.get(index + "");
		int lineIndex = lineSet.searchTags(replacedLine.getTag());
		
		if(lineIndex == -1){
			//TODO do it !

			int i = 0, j = 0;
			int lowerLineSize = lowerLevelCache.lineSize;
			int currentAddress = absoluteAddress - absoluteAddress % lineSize;
			CacheLine<String> lowerCacheLine = lowerLevelCache.readLine(currentAddress, false);
			while(i < lineSize){
				if(j == lowerLineSize){
					lowerLevelCache.writeLine(currentAddress / lowerLineSize, lowerCacheLine, currentAddress);
					currentAddress += lowerLineSize;
					lowerCacheLine = lowerLevelCache.readLine(currentAddress, false);
					j = 0;
				}
				lowerCacheLine.setBlock(j, replacedLine.getBlock(i));
				
				i++;
				j++;
			}
			
			lowerLevelCache.writeLine(currentAddress / lowerLineSize, lowerCacheLine, currentAddress);
			
			System.out.println("Write miss " + lowerLevelCache);
			if(isWriteAllocate){
				int toReplaceLineIndex = lineSet.getLineIndexToReplace();
				CacheLine<String> toReplaceLine = lineSet.getCacheLine(toReplaceLineIndex);
				
				if(toReplaceLine.getTag() != null && toReplaceLine.isDirty()){
					lowerLevelCache.writeLine(index, toReplaceLine, absoluteAddress);
				}
				
				replacedLine.setDirty(false);
				lineSet.insert(toReplaceLineIndex, replacedLine);
			}
			
			return;
		}
		
		numberOfHits++;
		System.out.println("Write hit " + lowerLevelCache);
		lineSet.insert(lineIndex, replacedLine);
		
		if(lowerLevelCache == null){
			return;
		}
		
		if(isWriteBack){
			replacedLine.setDirty(true);
		}
		else{
			replacedLine.setDirty(false);
			lowerLevelCache.writeLine(index, replacedLine, absoluteAddress);
		}
	}


	public void writeData(int address, String value){
		numberOfIssues++;
		
		int offset = address % lineSize;
		int index = ((address / lineSize) % (size / associativity)) % size;
		int tag = address / (lineSize * size / associativity);
		
		CacheLineSet<String> lineSet = dCache.get(index + "");
		int cacheLineIndex = lineSet.searchTags(tag);
		
		if(cacheLineIndex == -1){
			// Handle write miss
			lowerLevelCache.writeData(address, value);
			
			if(isWriteAllocate){
				//CacheLine<String> line = lowerLevelCache.readLine(address, true);
				//line.setBlock(offset, value);
				//line.setTag(tag + "");
				
				CacheLine<String> lowerCacheLine = lowerLevelCache.readLine(address, true);
				CacheLine<String> line = new CacheLine<String>(lineSize);
				line.setTag(tag + "");
				
				int lowerLevelLineSize = lowerLevelCache.lineSize;
				int currentBaseAddress = address - address % lineSize;
				int currentAdd = currentBaseAddress % lowerLevelLineSize;
				int i = 0;
				for(int q = 0; q < lineSize; q++){
					if(i >= lowerLevelLineSize){
						i = 0;
						currentAdd += lowerLevelLineSize;
						lowerCacheLine = lowerLevelCache.readLine(currentAdd, true);
					}
					String value2 = lowerCacheLine.getBlock(i);
					line.setBlock(q, value2);
					i++;
				}
				line.setBlock(offset, value);
				line.setTag(tag + "");
				
				////
				
				int toReplaceLineIndex = lineSet.getLineIndexToReplace();
				CacheLine<String> toReplaceLine = lineSet.getCacheLine(toReplaceLineIndex);
				System.out.println(toReplaceLineIndex + " to replace index");
				if(toReplaceLine.getTag() != null && toReplaceLine.isDirty()){
					lowerLevelCache.writeLine(index, toReplaceLine, address);
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
		line.setTag(tag + "");
		
		if(lowerLevelCache == null){
			return;
		}
		
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
/*
 * BenchMark
addi R1 R1 5
addi R2 R2 6
add R3 R1 R2
sw R1 R3 0
lw R4 R3 0
 */