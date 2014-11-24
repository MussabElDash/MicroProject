package memory;

import instructions.Instruction;

import java.util.ArrayList;
import java.util.HashMap;

import utilities.CacheDetailsHolder;
import utilities.Pair;
import utilities.Utilities;

public class Memory {
	public static int NOT_FOUND_VALUE = -1111111111;
	private static Memory rootMemory = null;

	private Cache cache = null;
	private Register[] registers = null;

	private Memory() {
		registers = new Register[8];
		for (int i = 0; i < 7; i++) {
			registers[i] = new Register((i == 0), ("R" + i));
		}
		registers[7] = new Register(false, "PC");
	}

	public static Memory getInstance() {
		if (rootMemory == null)
			rootMemory = new Memory();
		return rootMemory;
	}

	public void initialize(ArrayList<CacheDetailsHolder> caches,
			int mainMemoryAccessTime, Instruction[] instructions,
			int startAddress, HashMap<Integer, Integer> data) {
		caches.add(new CacheDetailsHolder(65536, 1, 1, false, false,
				mainMemoryAccessTime));
		cache = new Cache(caches, instructions, startAddress, data);
	}

	public int getRegisterId(String registerTitle) {
		for (int q = 0; q < 8; q++) {
			if (registers[q].getTitle().equals(registerTitle)) {
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

	public void setMemoryValue(int memoryAddress, String value) {
		cache.writeData(memoryAddress, value);
	}

	public String getMemoryValue(int memoryAddress) {
		return cache.readData(memoryAddress);
	}

	public Instruction getInstruction(int memoryAddress) {
		return cache.readInstruction(memoryAddress);
	}

	public ArrayList<Pair<Integer, Integer>> getCacheStats() {
		return cache.getCacheStats();
	}

	public double getAMAT() {
		return cache.getAMAT();
	}
}
