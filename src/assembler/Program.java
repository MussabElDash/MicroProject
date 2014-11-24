package assembler;

import java.util.HashMap;
import java.util.ArrayList;

import utilities.CacheDetailsHolder;
import gui.CacheHitWindow;
import gui.RegistersTable;
import instructions.Instruction;
import memory.Memory;

public class Program {
	Instruction[] instructions;
	Memory memory;
	int startAddress;

	public Program(String code, int startAddress, int MemAccessTime,
			CacheDetailsHolder[] cacheDetails,
			HashMap<Integer, Integer> editedAddress) {
		ArrayList<CacheDetailsHolder> caches = new ArrayList<>();
		for (int i=0; i<cacheDetails.length; i++) {
			caches.add(cacheDetails[i]);
		}
		String[] lines = code.split("\n");
		instructions = Assembler.assembleProgram(lines);
		memory = Memory.getInstance();
		memory.initialize(caches, MemAccessTime, instructions, startAddress, editedAddress);
		this.startAddress = startAddress;
	}

	public Program(String code, int startAddress,
			CacheDetailsHolder[] caches, int mainMemoryAccessTime) {
		
		int address = startAddress;
		for (int i = 0; i < instructions.length; i++) {
			memory.setMemoryValue(address, instructions[i].getMachineCode());
			address += 4;
		}
	}

	public static void afterExec() {
		RegistersTable.updateRegisters();
		new CacheHitWindow();
	}
}
