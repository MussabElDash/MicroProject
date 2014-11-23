package instructions;

import memory.Memory;

public abstract class Instruction {
	protected String[] parameters;
	protected String regA, regB, regC, imm;
	protected int regANum, regBNum, regCNum, immValue;
	protected Memory mem = Memory.getInstance();
	protected String opcode;
	
	protected Instruction(String[] params, String[] target, String op) {
		parameters = params;
		opcode = op;
		for(int i = 0; i < target.length; i++) {
			if(target[i].equals("regA")) {
				regA = target[i];
				regANum = mem.getRegisterNumber(regA);
			} else if(target[i].equals("regB")) {
				regB = target[i];
				regBNum = mem.getRegisterNumber(regB);
			} else if(target[i].equals("regC")) {
				regC = target[i];
				regCNum = mem.getRegisterNumber(regC);
			} else if(target[i].equals("imm")) {
				imm = target[i];
				immValue = Integer.parseInt(imm);
			} else {
				System.out.println("Instruction Parameter Target Error: " + target[i]);
			}
		}
	}
	
	public abstract void execute();
	public abstract String getMachineCode();
	
	public int getRegANum() {
		return regANum;
	}
	
	public int getRegBNum() {
		return regBNum;
	}
	
	public int getRegCNum() {
		return regCNum;
	}
	
	public String getOpcode() {
		return opcode;
	}

	public int getImmValue() {
		return immValue;
	}
}
