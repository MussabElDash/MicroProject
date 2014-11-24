package instructions.isa;

import instructions.Instruction;
import utilities.Utilities;

public class Addi extends Instruction {

	protected Addi(String[] params) {
		super(params, new String[] { "regA", "regB", "imm" }, "0001");
	}

	@Override
	public void execute() {
		mem.setRegisterValue(regA, mem.getRegisterValue(regB) + immValue);
	}

	@Override
	public String getMachineCode() {
		return getOpcode() + Utilities.getBinaryNumber(getRegANum())
				+ Utilities.getBinaryNumber(getRegBNum())
				+ Utilities.getBinaryNumber(getImmValue());
	}

}
