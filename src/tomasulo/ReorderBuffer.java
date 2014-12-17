package tomasulo;

import java.util.ArrayList;

import memory.Register;

public class ReorderBuffer {
	private ArrayList<ReorderBufferElement> table;
	private int size;
	public static int cnt = 0;
	
	public ReorderBuffer(int size) {
		this.size = size;
		this.table = new ArrayList<ReorderBufferElement>(size);
	}
	
	public boolean insert(Register dest, int num) {
		if (table.size()+1 < size) {
			table.add(new ReorderBufferElement(cnt%size, dest, "", false, num));
			cnt ++;
			return true;
		}
		return false;
	}
	
	public int getFirst() {
		return table.get(0).getINum();
	}
	
	public void removeFirst() {
		if (table.size() > 0) {
			table.remove(0);
		}
	}
}
