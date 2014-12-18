package instructions.isa;

import instructions.Instruction;
import tomasulo.RSType;
import utilities.Utilities;

public class Beq extends Instruction {

	public Beq(String[] params) {
		super(params, new String[] { "regA", "regB", "imm" }, "0110", RSType.ADD);
	}

	@Override
	public void execute() {
		if (mem.getRegisterValue(regA) == mem.getRegisterValue(regB))
			mem.setRegisterValue("PC", mem.getRegisterValue("PC") + immValue);
	}
	
	public int compute(int valA, int valB) {
		return valA - valB;
	}

	@Override
	public String getMachineCode() {
		return getOpcode() + Utilities.getBinaryNumber(getRegANum(), 3)
				+ Utilities.getBinaryNumber(getRegBNum(), 3)
				+ Utilities.getBinaryNumber(getImmValue(), 7);
	}

}
