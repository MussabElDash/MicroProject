package memory;

import utilities.CacheDetailsHolder;
import utilities.Utilities;

public class Memory {
	public static int NOT_FOUND_VALUE = -1111111111;
	private static Memory rootMemory = null;
	
	private Cache cache = null;
	private Register[] registers = new Register[8];
	
	public static Memory getInstance(CacheDetailsHolder caches, int mainMemoryAccessTime){
		if (rootMemory == null) {
			rootMemory = new Memory(caches, mainMemoryAccessTime);
		}
		return rootMemory;
	}
	
	private Memory(CacheDetailsHolder caches, int mainMemoryAccessTime) {
		cache = new Cache(caches);
	}
	
	public int getRegisterId(String registerTitle){
		for(int q = 0; q < 8; q++){
			if(registers[q].getTitle().equals(registerTitle)){
				return q;
			}
		}
		
		return NOT_FOUND_VALUE;
	}
	
	public void setRegisterValue(String registerTitle, int value){
		int registerId = getRegisterId(registerTitle);
		
		if(registerId == NOT_FOUND_VALUE){
			Utilities.raiseError("Register " + registerTitle + " not found!");
			return;
		}
		
		registers[registerId].setValue((short) value);
	}
	
	public int getRegisterValue(String registerTitle){
		int registerId = getRegisterId(registerTitle);
		
		if(registerId == NOT_FOUND_VALUE){
			Utilities.raiseError("Register " + registerTitle + " not found!");
			return NOT_FOUND_VALUE;
		}
		
		return registers[registerId].getValue();
	}
	
	public void setMemoryValue(int memoryAddress, int value){
		cache.setValue(memoryAddress, value);
	}
	
	public int getMemoryValue(int memoryAddress){
		return cache.getValue(memoryAddress);
	}
}
