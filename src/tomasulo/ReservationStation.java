package tomasulo;

import instructions.Instruction;

public class ReservationStation {
	
	private RSType type;
	private Instruction op;
	private int Vj, Vk;
	private int Qj, Qk;
	private int destination;
	private int A;
	private int cycles;
	
	public ReservationStation(RSType type) {
		this.type = type;
		this.cycles = RSMaster.getDelay(type);
	}
	
	public RSType getType() {
		return type;
	}
	
	public boolean free() {
		return op == null;
	}
	
	public boolean ready() {
		return Qj == 0 && Qk == 0;
	}
	
	public void calcAddress() {
		A = Vj + A;
	}

	public Instruction getOp() {
		return op;
	}

	public void setOp(Instruction op) {
		this.op = op;
	}

	public int getVj() {
		return Vj;
	}

	public void setVj(int vj) {
		Vj = vj;
	}

	public int getVk() {
		return Vk;
	}

	public void setVk(int vk) {
		Vk = vk;
	}

	public int getQj() {
		return Qj;
	}

	public void setQj(int qj) {
		Qj = qj;
	}

	public int getQk() {
		return Qk;
	}

	public void setQk(int qk) {
		Qk = qk;
	}

	public int getDestination() {
		return destination;
	}

	public void setDestination(int destination) {
		this.destination = destination;
	}

	public int getA() {
		return A;
	}

	public void setA(int a) {
		A = a;
	}

	public int getCycles() {
		return cycles;
	}

	public void setCycles(int cycles) {
		this.cycles = cycles;
	}
	
	public void flush() {
		cycles = RSMaster.getDelay(type);
		op = null;
		Vj = Vk = Qj = Qk = 0;
		destination = A = 0;
	}

}
