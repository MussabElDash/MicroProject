package instructions;

import memory.Memory;
import tomasulo.RSType;

public abstract class Instruction {
	protected String[] parameters;
	protected String regA, regB, regC, imm;
	protected int regANum, regBNum, regCNum, immValue;
	protected Memory mem = Memory.getInstance();
	protected String opcode;
	protected RSType rsType;
	
	protected Instruction(String[] params, String[] target, String op, RSType type) {
		rsType = type;
		parameters = params;
		opcode = op;
		for(int i = 0; i < target.length; i++) {
			if(target[i].equals("regA")) {
				regA = params[i];
				regANum = mem.getRegisterId(regA);
			} else if(target[i].equals("regB")) {
				regB = params[i];
				regBNum = mem.getRegisterId(regB);
			} else if(target[i].equals("regC")) {
				regC = params[i];
				regCNum = mem.getRegisterId(regC);
			} else if(target[i].equals("imm")) {
				imm = params[i];
				immValue = Integer.parseInt(imm);
			} else {
				System.out.println("Instruction Parameter Target Error: " + target[i]);
			}
		}
	}
	
	public RSType getType() {
		return rsType;
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
	
	public String getRegA() {
		return regA;
	}
	
	public String getRegB() {
		return regB;
	}
	
	public String getRegC() {
		return regC;
	}
	
	public String getOpcode() {
		return opcode;
	}

	public int getImmValue() {
		return immValue;
	}
}
