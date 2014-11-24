package instructions.isa;

import utilities.Utilities;
import instructions.Instruction;

public class Jalr extends Instruction {

	public Jalr(String[] params) {
		super(params, new String[] { "regA", "regB" }, "0111");
	}

	@Override
	public void execute() {
		mem.setRegisterValue(regA, mem.getRegisterValue("PC") + 1);
		mem.setRegisterValue("PC", mem.getRegisterValue(regB));
	}

	@Override
	public String getMachineCode() {
		return getOpcode() + Utilities.getBinaryNumber(getRegANum(), 3)
				+ Utilities.getBinaryNumber(getRegBNum(), 3) + "0000000";
	}

}
