package tomasulo;

import memory.Register;

public class ReorderBufferElement {
	private int idx;
	private Register dest;
	private String val;
	private boolean ready;
	private int iNum;
	
	public ReorderBufferElement(int idx, Register dest, String val, boolean ready, int iNum) {
		this.idx = idx;
		this.dest = dest;
		this.val = val;
		this.ready = ready;
		this.iNum = iNum;
	}
	
	public int getINum() {
		return iNum;
	}
}
