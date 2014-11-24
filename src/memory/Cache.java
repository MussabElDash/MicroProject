package memory;

import java.util.ArrayList;
import java.util.HashMap;

import utilities.CacheDetailsHolder;

public class Cache {
	private HashMap<String, String> iCache = new HashMap<String, String>();
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
	
	public int getValue(int address, boolean isInstruction){
		numberOfIssues++;
		//HashMap<String String> cache = iCache;
		if(isInstruction){
			/// Handle fetching instruction
		}
		else{
			if(dCache.containsKey(address + "")){
				numberOfHits++;
				return Integer.parseInt(dCache.get(address + ""));
			}
			else{
				/// Handle read miss
			}
		}
		return 0;
	}
	
	public void setValue(int address, int value, boolean isInstruction){
		
	}

}
