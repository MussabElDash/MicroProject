package tomasulo;

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
	
	public static boolean insert(Register dest, int num) {
		if (table.size()+1 < size) {
			table.add(new ReorderBufferElement(cnt%size, dest, "", false, num));
			cnt ++;
			return true;
		}
		return false;
	}
	
	public static int getFirst() {
		return table.get(0).getINum();
	}
	
	public static void removeFirst() {
		if (table.size() > 0) {
			table.remove(0);
		}
	}
}
