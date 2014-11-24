package assembler;

import java.util.HashMap;
import java.util.ArrayList;

import utilities.CacheDetailsHolder;
import instructions.Instruction;
import memory.Memory;

public class Program {
	Instruction[] instructions;
	Memory memory;
	int startAddress;

	public Program(String code, int startAddress, int MemAccessTime,
			ArrayList<CacheDetailsHolder> cacheDetails,
			HashMap<Integer, Integer> editedAddress) {
		String[] lines = code.split("\n");
		instructions = Assembler.assembleProgram(lines);
		memory = Memory.getInstance();
		memory.initialize(cacheDetails, MemAccessTime);
	}

	public Program(String code, int startAddress,
			ArrayList<CacheDetailsHolder> caches, int mainMemoryAccessTime) {
		
		int address = startAddress;
		for (int i = 0; i < instructions.length; i++) {
			memory.setMemoryValue(address, instructions[i].getMachineCode());
			address += 4;
		}
	}
}
