package tomasulo;

import instructions.Instruction;
import instructions.isa.Jalr;

import java.util.ArrayList;

import memory.Memory;
import memory.Register;

public class ReorderBuffer {
	private static ArrayList<ReorderBufferElement> table;
	private static int size;
	private static int cnt = 0;

	public static void init(int sz) {
		size = sz;
		table = new ArrayList<ReorderBufferElement>(size);
	}

	public static void insert(String dest) {
		table.add(new ReorderBufferElement(cnt % size, dest, "", false));
		Memory.getInstance().getRegister(dest).setROBNum(cnt % size);
		cnt++;
	}

	public static int getFirst() {
		return table.get(0).getIdx();
	}

	public static void removeFirst() {
		if (table.size() > 0) {
			table.remove(0);
		}
	}

	public static boolean isFull() {
		return (table.size() == size);
	}

	public static void issue(Instruction instruction) {
		String dest = "";
		if (instruction.getType() != RSType.ST
				&& (instruction.getType() != RSType.JMP || (instruction
						.getType() == RSType.JMP && instruction instanceof Jalr))) {
			dest = instruction.getRegA();
		}
		insert(dest);
	}
}
