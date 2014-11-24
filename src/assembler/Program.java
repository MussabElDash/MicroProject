package assembler;

import java.util.HashMap;
import java.util.ArrayList;

import utilities.CacheDetailsHolder;
import utilities.Pair;
import gui.CacheHitWindow;
import gui.RegistersTable;
import instructions.Instruction;
import memory.Memory;

public class Program {
	Memory memory;
	int startAddress;
	int endAddress;
	int numOfInstructions;

	public Program(String code, int startAddress, int MemAccessTime,
			CacheDetailsHolder[] cacheDetails,
			HashMap<Integer, Integer> editedAddress) {
		ArrayList<CacheDetailsHolder> caches = new ArrayList<>();
		for (int i = 0; i < cacheDetails.length; i++) {
			caches.add(cacheDetails[i]);
		}
		String[] lines = code.split("\n");
		Instruction[] instructions = Assembler.assembleProgram(lines);
		memory = Memory.getInstance();
		memory.initialize(caches, MemAccessTime, instructions, startAddress,
				editedAddress);
		this.startAddress = startAddress;
		this.endAddress = startAddress + (instructions.length - 1);
		this.numOfInstructions = 0;
		this.execute();
		this.afterExec();
	}

	public void execute() {
		int val = 0;
		memory.setRegisterValue("PC", startAddress);
		do {
			val = memory.getRegisterValue("PC");
			Instruction current = memory.getInstruction(val);
			memory.setRegisterValue("PC", val + 1);
			current.execute();
			numOfInstructions++;
		} while (val != endAddress);
	}

	public void afterExec() {
		RegistersTable.updateRegisters();
		double amat = memory.getAMAT();
		ArrayList<Pair<Integer, Integer>> hitRatios = memory.getCacheStats();
		new CacheHitWindow(hitRatios, amat, numOfInstructions);
	}
}
