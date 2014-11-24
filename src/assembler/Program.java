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
		this.numOfInstructions = instructions.length;
		this.execute();
	}

	public void execute() {
		memory.setRegisterValue("PC", startAddress);
		for (int i = 0; i < numOfInstructions; i++) {
			int val = memory.getRegisterValue("PC");
			Instruction current = memory.getInstruction(val);
			memory.setRegisterValue("PC", val + 4);
			current.execute();
		}
	}

	public void afterExec() {
		RegistersTable.updateRegisters();
		double amat = 0;
		// arraylist(i).first Hit
		// arraylist(i).second requests
		ArrayList<Pair<Integer, Integer>> hitRatios;
		hitRatios = new ArrayList<Pair<Integer, Integer>>();
		new CacheHitWindow(hitRatios,amat);
	}
}
