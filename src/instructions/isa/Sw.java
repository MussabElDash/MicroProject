package instructions.isa;

import utilities.Utilities;
import instructions.Instruction;

public class Sw extends Instruction {

	public Sw(String[] params) {
		super(params, new String[] { "regA", "regB", "imm" }, "0100");
	}

	@Override
	public void execute() {
		mem.setMemoryValue(mem.getRegisterValue(regB) + immValue,
				Utilities.getBinaryNumber(mem.getRegisterValue(regA), 16));
	}

	@Override
	public String getMachineCode() {
		return getOpcode() + Utilities.getBinaryNumber(getRegANum(), 3)
				+ Utilities.getBinaryNumber(getRegBNum(), 3)
				+ Utilities.getBinaryNumber(getImmValue(), 7);
	}

	@Override
	public void writeBack() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void commit() {
		// TODO Auto-generated method stub
		
	}

}
