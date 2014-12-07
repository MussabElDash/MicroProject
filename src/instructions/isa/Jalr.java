package instructions.isa;

import instructions.Instruction;
import tomasulo.RSType;
import utilities.Utilities;

public class Jalr extends Instruction {

	public Jalr(String[] params) {
		super(params, new String[] { "regA", "regB" }, "0111", RSType.JMP);
	}

	@Override
	public void execute() {
		mem.setRegisterValue(regA, mem.getRegisterValue("PC"));
		mem.setRegisterValue("PC", mem.getRegisterValue(regB));
	}

	@Override
	public String getMachineCode() {
		return getOpcode() + Utilities.getBinaryNumber(getRegANum(), 3)
				+ Utilities.getBinaryNumber(getRegBNum(), 3) + "0000000";
	}

}
