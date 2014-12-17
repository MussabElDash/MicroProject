package tomasulo;

import instructions.Instruction;

import java.util.ArrayList;

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
		table.add(new ReorderBufferElement(cnt%size, dest, "", false));
		cnt ++;
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
		String dest = null;
		if (instruction.getType() != RSType.ST) {
			dest = instruction.getRegA();
		}
	}
}
