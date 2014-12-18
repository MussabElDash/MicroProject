package assembler;

import gui.CacheHitWindow;
import gui.RegistersTable;
import instructions.Instruction;
import instructions.isa.Beq;
import instructions.isa.Jalr;
import instructions.isa.Jmp;
import instructions.isa.Ret;

import java.util.ArrayList;
import java.util.HashMap;

import memory.Memory;
import tomasulo.InstructionQueue;
import tomasulo.RSMaster;
import tomasulo.RSType;
import tomasulo.ReorderBuffer;
import utilities.CacheDetailsHolder;
import utilities.Pair;

public class Program {
	private Memory memory;
	private int startAddress;
	private int endAddress;
	public static int numOfInstructions;
	public static int numOfCycles;
	public static int numOfBranches;
	public static int numOfMisPredictions;
	public static int PC;

	public Program(String code, int startAddress, int MemAccessTime,
			CacheDetailsHolder[] cacheDetails,
			HashMap<Integer, Integer> editedAddress,
			HashMap<RSType, Integer> rStations, HashMap<RSType, Integer> delay,
			int ROBSize, int pipelineWidth) {
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

		InstructionQueue.setPipelineWidth(pipelineWidth);
		Program.numOfInstructions = 0;
		Program.numOfCycles = 0;
		Program.numOfBranches = 0;
		Program.numOfMisPredictions = 0;

		RSMaster.init(rStations, delay);
		ReorderBuffer.init(ROBSize);

		this.run();
		this.afterRun();
	}

	public void run() {
		int val = 0;
		memory.setRegisterValue("PC", startAddress);
		do {
			numOfCycles++;
			int m = InstructionQueue.getPipelineWidth();
			val = memory.getRegisterValue("PC");
			for (int i = 0; i < m && !InstructionQueue.isFull()
					&& val != endAddress; i++) {
				Instruction current = memory.getInstruction(val);
				memory.setRegisterValue("PC", val + 1);
				if (current instanceof Jmp || current instanceof Jalr
						|| current instanceof Ret) {
					if (current instanceof Jmp) {
						numOfBranches++;
					}
					current.execute();
				} else if (current instanceof Beq) {
					numOfBranches++;
					PC = val + 1;
					if (current.getImmValue() < 0) {
						current.execute();
					}
					InstructionQueue.enqueue(current);
				} else {
					InstructionQueue.enqueue(current);
				}
				val = memory.getRegisterValue("PC");
			}
			if (!InstructionQueue.isEmpty())
				InstructionQueue.issue();
			if (!RSMaster.isEmpty())
				RSMaster.stepForth();
			ReorderBuffer.commit();
		} while (val != endAddress || !InstructionQueue.isEmpty());
	}

	public void afterRun() {
		RegistersTable.updateRegisters();
		double amat = memory.getAMAT();
		ArrayList<Pair<Integer, Integer>> hitRatios = memory.getCacheStats();
		new CacheHitWindow(hitRatios, amat, numOfInstructions);
	}
}
