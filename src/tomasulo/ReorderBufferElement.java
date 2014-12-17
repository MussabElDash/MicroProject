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

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public Register getDest() {
		return dest;
	}

	public void setDest(Register dest) {
		this.dest = dest;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public int getINum() {
		return iNum;
	}

	public void setINum(int iNum) {
		this.iNum = iNum;
	}
}
