package memory;

import java.util.ArrayList;

import utilities.CacheDetailsHolder;
import utilities.Utilities;

public class Memory {
	public static int NOT_FOUND_VALUE = -1111111111;
	private static Memory rootMemory = new Memory();
	
	private Cache cache = null;
	private Register[] registers = null;
	
	public static Memory getInstance(){
		return rootMemory;
	}
	
	public void initialize(ArrayList<CacheDetailsHolder> caches, int mainMemoryAccessTime) {
		caches.add(new CacheDetailsHolder(65536, 1, 1, false, false, mainMemoryAccessTime));
		cache = new Cache(caches);
		registers = new Register[8];
	}
	
	public int getRegisterId(String registerTitle){
		for(int q = 0; q < 8; q++){
			if(registers[q].getTitle().equals(registerTitle)){
				return q;
			}
		}

		return NOT_FOUND_VALUE;
	}

	public void setRegisterValue(String registerTitle, int value) {
		int registerId = getRegisterId(registerTitle);

		if (registerId == NOT_FOUND_VALUE) {
			Utilities.raiseError("Register " + registerTitle + " not found!");
			return;
		}

		registers[registerId].setValue((short) value);
	}

	public int getRegisterValue(String registerTitle) {
		int registerId = getRegisterId(registerTitle);

		if (registerId == NOT_FOUND_VALUE) {
			Utilities.raiseError("Register " + registerTitle + " not found!");
			return NOT_FOUND_VALUE;
		}

		return registers[registerId].getValue();
	}
	
	public void setMemoryValue(int memoryAddress, String value){
		this.setMemoryValue(memoryAddress, value, false);
	}
	
	public void setMemoryValue(int memoryAddress, String value, boolean isInstruction){
		cache.setValue(memoryAddress, value, isInstruction);
	}
	
	public int getMemoryValue(int memoryAddress){
		return this.getMemoryValue(memoryAddress, false);
	}
	
	public int getMemoryValue(int memoryAddress, boolean isInstruction){
		return cache.getValue(memoryAddress, isInstruction);
	}
}
