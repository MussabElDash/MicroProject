package instructions.isa;

import instructions.Instruction;
import utilities.Utilities;

public class Addi extends Instruction {

	public Addi(String[] params) {
		super(params, new String[] { "regA", "regB", "imm" }, "0001");
	}

	@Override
	public void execute() {
		mem.setRegisterValue(regA, mem.getRegisterValue(regB) + immValue);
	}

	@Override
	public String getMachineCode() {
		return getOpcode() + Utilities.getBinaryNumber(getRegANum(), 3)
				+ Utilities.getBinaryNumber(getRegBNum(), 3)
				+ Utilities.getBinaryNumber(getImmValue(), 7);
	}

	@Override
	public void writeBack() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void commit() {
		// TODO Auto-generated method stub
		
	}

}
