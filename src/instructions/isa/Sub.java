package instructions.isa;

import utilities.Utilities;
import instructions.Instruction;

public class Sub extends Instruction {

	protected Sub(String[] params) {
		super(params, new String[]{"regA", "regB", "regC"}, "1011");
	}

	@Override
	public void execute() {
		mem.setRegisterValue(regA, mem.getRegisterValue(regB) - mem.getRegisterValue(regC));
	}

	@Override
	public String getMachineCode() {
		return getOpcode() + Utilities.getBinaryNumber(getRegANum(), 3)
				+ Utilities.getBinaryNumber(getRegBNum(), 3) + "0000"
				+ Utilities.getBinaryNumber(getRegCNum(), 3);
	}

}
