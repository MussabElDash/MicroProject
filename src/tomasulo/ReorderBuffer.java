package tomasulo;

import instructions.Instruction;

import java.util.ArrayList;

import memory.Memory;

public class ReorderBuffer {
	private static ArrayList<ReorderBufferElement> table;
	private static int size;
	private static int cnt = 0;

	public static void init(int sz) {
		size = sz;
		table = new ArrayList<ReorderBufferElement>(size);
	}

	private static void insert(String dest) {
		table.add(new ReorderBufferElement(cnt+1, dest, 0, false));
		Memory.getInstance().getRegister(dest).setROBNum(cnt+1);
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

	public static int issue(Instruction instruction) {
		String dest = "";
		if (instruction.getType() != RSType.ST) {
			dest = instruction.getRegA();
		}
		insert(dest);
		int ret = cnt + 1;
		cnt = (cnt + 1) % size;
		return ret;
	}
	
	public static ReorderBufferElement getROBElement(int idx) {
		for (int i=0; i<table.size(); i++) {
			if (table.get(i).getIdx() == idx) {
				return table.get(i);
			}
		}
		return null;
	}
}
