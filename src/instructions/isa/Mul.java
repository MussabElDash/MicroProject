package instructions.isa;

import instructions.Instruction;
import tomasulo.RSType;
import utilities.Utilities;

public class Mul extends Instruction {

	public Mul(String[] params) {
		super(params, new String[]{"regA", "regB", "regC"}, "1001", RSType.MUL);
	}

	@Override
	public void execute() {
		mem.setRegisterValue(regA, mem.getRegisterValue(regB)*mem.getRegisterValue(regC));
	}
	
	public int compute(int valA, int valB) {
		return valA * valB;
	}

	@Override
	public String getMachineCode() {
		return getOpcode() + Utilities.getBinaryNumber(getRegANum(), 3)
				+ Utilities.getBinaryNumber(getRegBNum(), 3) + "0000"
				+ Utilities.getBinaryNumber(getRegCNum(), 3);
	}

}
