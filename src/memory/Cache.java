package memory;

import instructions.Instruction;

import java.util.ArrayList;
import java.util.HashMap;

import utilities.CacheDetailsHolder;

public class Cache {
	private HashMap<String, Instruction> iCache = new HashMap<String, Instruction>();
	private HashMap<String, String> dCache = new HashMap<String, String>();
	private Cache lowerLevelCache = null;
	private boolean isWriteBack = false;
	private boolean isWriteAllocate = false;
	private int accessTime = 0;
	private int numberOfHits = 0;
	private int numberOfIssues = 0;
	private int size = 0;
	private int lineSize = 0;
	private int associativity = 0;
	
	public Cache(int size, int lineSize, int associativity, boolean isWriteBack, boolean isWriteAllocate, int accessTime){
		this.isWriteBack = isWriteBack;
		this.isWriteAllocate = isWriteAllocate;
		this.accessTime = accessTime;
		this.size = size;
		this.lineSize = lineSize;
		this.associativity = associativity;
	}


	public Cache(ArrayList<CacheDetailsHolder> caches) {
		// TODO Auto-generated constructor stub
	}


	public Cache getLowerLevelCache() {
		return lowerLevelCache;
	}


	public void setLowerLevelCache(Cache lowerLevelCache) {
		this.lowerLevelCache = lowerLevelCache;
	}
	
	public Instruction readInstruction(int address){
		numberOfIssues++;
		Instruction result = iCache.get(address + "");
		
		if(result == null){
			// Handle read miss
			result = lowerLevelCache.readInstruction(address);
			insertInstruction(address, result);
		}
		else{
			numberOfHits++;
		}
		
		return result;
	}
	
	private void insertInstruction(int address, Instruction result) {
		
	}


	public String readData(int address){
		numberOfIssues++;
		String result = dCache.get(address + "");
		
		if(result == null){
			// Handle read miss
			result = lowerLevelCache.readData(address);
			insertData(address, result);
		}
		else{
			numberOfHits++;
		}
		
		return result;
	}
	
	private void insertData(int address, String result) {
		// TODO Auto-generated method stub
		
	}


	public void writeData(int address, String value){
		
	}

}
