package instructions.isa;

import tomasulo.RSType;
import utilities.Utilities;
import instructions.Instruction;

public class Nand extends Instruction {

	public Nand(String[] params) {
		super(params, new String[]{"regA", "regB", "regC"}, "0010", RSType.ADD);
	}

	@Override
	public void execute() {
		mem.setRegisterValue(regA, ~(mem.getRegisterValue(regB) & mem.getRegisterValue(regC)));
	}

	public int compute(int valA, int valB) {
		return ~(valA & valB);
	}
	
	@Override
	public String getMachineCode() {
		return getOpcode() + Utilities.getBinaryNumber(getRegANum(), 3)
				+ Utilities.getBinaryNumber(getRegBNum(), 3) + "0000"
				+ Utilities.getBinaryNumber(getRegCNum(), 3);
	}

}
