package instructions.isa;

import utilities.Utilities;
import instructions.Instruction;

public class Jalr extends Instruction {

	protected Jalr(String[] params) {
		super(params, new String[] { "regA", "regB" }, "0111");
		// TODO Auto-generated constructor stub
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
