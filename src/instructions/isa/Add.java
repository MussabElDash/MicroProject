package instructions.isa;

import instructions.Instruction;
import utilities.Utilities;

public class Add extends Instruction {

	protected Add(String[] params) {
		super(params, new String[] { "regA", "regB", "regC" }, "0000");
	}

	@Override
	public void execute() {
		mem.setRegisterValue(regA, mem.getRegisterValue(regB) + mem.getRegisterValue(regC));
	}

	@Override
	public String getMachineCode() {
		return getOpcode() + Utilities.getBinaryNumber(getRegANum())
				+ Utilities.getBinaryNumber(getRegBNum()) + "0000"
				+ Utilities.getBinaryNumber(getRegCNum());
	}

}
