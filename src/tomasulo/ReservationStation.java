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

	public int getDestination() {
		return destination;
	}
	
	public void setValues(int j, int k) {
		Vj = j;
		Vk = k;
	}
	
	public void setSources(int j, int k) {
		Qj = j;
		Qk = k;
	}
	
	public int getFirstOperand() {
		return Vj;
	}
	
	public int getSecondOperand() {
		return Vk;
	}
	
	public int getFirstSource() {
		return Qj;
	}
	
	public int getSecondSource() {
		return Qk;
	}

	public void setDestination(int destination) {
		this.destination = destination;
	}
	
	public void setCycles(int cycles) {
		this.cycles = cycles;
	}
	
	public int getCycles() {
		return cycles;
	}
}
