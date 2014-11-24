package instructions.isa;

import utilities.Utilities;
import instructions.Instruction;

public class Mul extends Instruction {

	public Mul(String[] params) {
		super(params, new String[]{"regA", "regB", "regC"}, "1001");
	}

	@Override
	public void execute() {
		mem.setRegisterValue(regA, mem.getRegisterValue(regB)*mem.getRegisterValue(regC));
	}

	@Override
	public String getMachineCode() {
		return getOpcode() + Utilities.getBinaryNumber(getRegANum(), 3)
				+ Utilities.getBinaryNumber(getRegBNum(), 3) + "0000"
				+ Utilities.getBinaryNumber(getRegCNum(), 3);
	}

}
