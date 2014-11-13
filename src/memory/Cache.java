package memory;

import java.util.HashMap;

public class Cache {
	HashMap<Integer, Integer> iCache = new HashMap<Integer, Integer>();
	HashMap<Integer, Integer> dCache = new HashMap<Integer, Integer>();
	Cache lowerLevelCache = null;
	boolean isWriteBack = false;
	boolean isWriteAllocate = false;
	
	public Cache() {
		
	}
}
