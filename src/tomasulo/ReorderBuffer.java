package tomasulo;

import instructions.Instruction;
import instructions.isa.Beq;

import java.util.ArrayList;

import assembler.Program;
import utilities.Utilities;
import memory.Memory;
import memory.Register;

public class ReorderBuffer {
	private static ArrayList<ReorderBufferElement> table;
	private static int size;
	private static int cnt = 0;
	private static Memory mem = Memory.getInstance();

	public static void init(int sz) {
		size = sz;
		table = new ArrayList<ReorderBufferElement>(size);
	}

	private static void insert(String dest, Instruction op) {
		table.add(new ReorderBufferElement(cnt + 1, dest, 0, false, op));
		Memory.getInstance().getRegister(dest).setROBNum(cnt + 1);
	}

	public static ReorderBufferElement getFirst() {
		return table.get(0);
	}

	public static void removeFirst() {
		if (table.size() > 0) {
			table.remove(0);
		}
	}

	public static boolean isFull() {
		return (table.size() == size);
	}

	public static int issue(Instruction instruction) {
		String dest = "";
		if (instruction.getType() != RSType.ST && !(instruction instanceof Beq)) {
			dest = instruction.getRegA();
		}
		insert(dest, instruction);
		int ret = cnt + 1;
		cnt = (cnt + 1) % size;
		return ret;
	}

	public static ReorderBufferElement getROBElement(int idx) {
		for (int i = 0; i < table.size(); i++) {
			if (table.get(i).getIdx() == idx) {
				return table.get(i);
			}
		}
		return null;
	}

	public static boolean before(int dest1, int dest2) {
		for (int i = 0; i < table.size(); i++) {
			if (table.get(i).getIdx() == dest1) {
				return true;
			}
			if (table.get(i).getIdx() == dest2) {
				return false;
			}
		}
		return false;
	}

	public static void commit() {
		ReorderBufferElement f = getFirst();
		Instruction ins = f.getOp();
		if (f.isReady() == true) {
			Program.numOfInstructions++;
			if (ins instanceof Beq) {
				if ((f.getVal() == 0 && ins.getImmValue() > 0)
						|| (f.getVal() != 0 && ins.getImmValue() < 0)) {
					mem.setRegisterValue("PC", Program.PC);
					Program.numOfMisPredictions++;
					if (f.getVal() == 0) {
						ins.execute();
					}
					ReorderBuffer.flush();
					RSMaster.flush();
				}
			} else if (ins.getType() == RSType.ST) {
				int dest = Integer.parseInt(f.getDest());
				String val = Utilities.getBinaryNumber(f.getVal(), 16);
				mem.setMemoryValue(dest, val);
			} else if (ins.getType() == RSType.ADD
					|| ins.getType() == RSType.MUL
					|| ins.getType() == RSType.LD) {
				String dest = f.getDest();
				int val = f.getVal();
				mem.setRegisterValue(dest, val);
				Register reg = mem.getRegister(dest);
				if (reg.getROBNum() == f.getIdx()) {
					reg.setROBNum(-1);
				}
			}
			removeFirst();
		}
	}

	public static void flush() {
		while (table.size() > 0) {
			ReorderBufferElement f = getFirst();
			Instruction ins = f.getOp();
			String dest = f.getDest();
			if (!dest.equals("") && ins.getType() != RSType.ST) {
				Register reg = mem.getRegister(dest);
				if (reg.getROBNum() == f.getIdx()) {
					reg.setROBNum(-1);
				}
			}
			removeFirst();
		}
	}
}
