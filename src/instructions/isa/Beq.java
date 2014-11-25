package instructions.isa;

import instructions.Instruction;
import utilities.Utilities;

public class Beq extends Instruction {

	public Beq(String[] params) {
		super(params, new String[] { "regA", "regB", "imm" }, "0110");
	}

	@Override
	public void execute() {
		if (mem.getRegisterValue(regA) == mem.getRegisterValue(regB))
			mem.setRegisterValue("PC", mem.getRegisterValue("PC") + immValue);
	}

	@Override
	public String getMachineCode() {
		return getOpcode() + Utilities.getBinaryNumber(getRegANum(), 3)
				+ Utilities.getBinaryNumber(getRegBNum(), 3)
				+ Utilities.getBinaryNumber(getImmValue(), 7);
	}
}
