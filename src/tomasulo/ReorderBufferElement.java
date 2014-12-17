package tomasulo;

import memory.Register;

public class ReorderBufferElement {
	private int idx;
	private String dest;
	private String val;
	private boolean ready;
	
	public ReorderBufferElement(int idx, String dest, String val, boolean ready) {
		this.idx = idx;
		this.dest = dest;
		this.val = val;
		this.ready = ready;
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
}
