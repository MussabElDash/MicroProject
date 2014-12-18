package tomasulo;

import instructions.Instruction;

public class ReorderBufferElement {
	private int idx;
	private String dest;
	private int val;
	private boolean ready;
	private Instruction op;
	
	public ReorderBufferElement(int idx, String dest, int val, boolean ready, Instruction op) {
		this.idx = idx;
		this.dest = dest;
		this.val = val;
		this.ready = ready;
		this.op = op;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getDest() {
		return dest;
	}

	public void setDest(String dest) {
		this.dest = dest;
	}

	public int getVal() {
		return val;
	}

	public void setVal(int val) {
		this.val = val;
	}
	
	public Instruction getOp() {
		return op;
	}

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}
}
