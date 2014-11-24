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

	public Program(String code, int startAddress, int MemAccessTime,
			CacheDetailsHolder[] cacheDetails,
			HashMap<Integer, Integer> editedAddress) {
		System.out.println(code);
		System.out.println(startAddress);
		System.out.println(MemAccessTime);
		System.out.println(cacheDetails.length);
		System.out.println(editedAddress);
	}

	int startAddress;

	public Program(String code, int startAddress,
			ArrayList<CacheDetailsHolder> caches, int mainMemoryAccessTime) {
		String[] lines = code.split("\n");
		instructions = Assembler.assembleProgram(lines);
		memory = Memory.getInstance();
		memory.initialize(caches, mainMemoryAccessTime);
		int address = startAddress;
		for (int i = 0; i < instructions.length; i++) {
			memory.setMemoryValue(address, instructions[i].getMachineCode(),
					true);
			address += 4;
		}
	}

	public static void afterExec() {
		RegistersTable.updateRegisters();
		new CacheHitWindow();
	}
}
