package instructions.isa;

import utilities.Utilities;
import instructions.Instruction;

public class Ret extends Instruction {

	public Ret(String[] params) {
		super(params, new String[]{"regA"}, "1010");
	}

	@Override
	public void execute() {
		mem.setRegisterValue("PC", mem.getRegisterValue(regA));
	}

	@Override
	public String getMachineCode() {
		return getOpcode() + Utilities.getBinaryNumber(getRegANum(), 3)
				+ Utilities.getBinaryNumber(getImmValue(), 10);
	}

}
