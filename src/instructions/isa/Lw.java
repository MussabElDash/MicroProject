package instructions.isa;

import instructions.Instruction;
import tomasulo.RSType;
import utilities.Utilities;

public class Lw extends Instruction {

	public Lw(String[] params) {
		super(params, new String[] { "regA", "regB", "imm" }, "0101", RSType.MEM);
	}

	@Override
	public void execute() {
		mem.setRegisterValue(
				regA,
				Utilities.getDecimalNumber(mem.getMemoryValue(mem
						.getRegisterValue(regB) + immValue)));
	}

	@Override
	public String getMachineCode() {
		return getOpcode() + Utilities.getBinaryNumber(getRegANum(), 3)
				+ Utilities.getBinaryNumber(getRegBNum(), 3)
				+ Utilities.getBinaryNumber(getImmValue(), 7);
	}

}
