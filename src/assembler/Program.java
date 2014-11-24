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
		for (int i = 0; i < cacheDetails.length; i++) {
			caches.add(cacheDetails[i]);
		}
		String[] lines = code.split("\n");
		instructions = Assembler.assembleProgram(lines);
		memory = Memory.getInstance();
		memory.initialize(caches, MemAccessTime, instructions, startAddress,
				editedAddress);
		this.startAddress = startAddress;
		this.execute();
	}

	public void execute() {

	}

	public static void afterExec() {
		RegistersTable.updateRegisters();
		// arraylist<Pair<Integer>>
		// arraylist(i).first Hit
		// arraylist(i).second requests
		// new CacheHitWindow(arraylist);
	}
}
