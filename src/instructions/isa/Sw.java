package instructions.isa;

import utilities.Utilities;
import instructions.Instruction;

public class Sw extends Instruction {

	protected Sw(String[] params) {
		super(params, new String[]{"regA", "regB", "imm"}, "0100");
	}

	@Override
	public void execute() {
		mem.setMemoryValue(mem.getRegisterValue(regB)+immValue, mem.getRegisterValue(regA));
	}

	@Override
	public String getMachineCode() {
		return getOpcode() + Utilities.getBinaryNumber(getRegANum(), 3)
				+ Utilities.getBinaryNumber(getRegBNum(), 3)
				+ Utilities.getBinaryNumber(getImmValue(), 7);
	}

}
