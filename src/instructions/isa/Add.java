package instructions.isa;

import instructions.Instruction;
import utilities.Utilities;

public class Add extends Instruction {

	public Add(String[] params) {
		super(params, new String[] { "regA", "regB", "regC" }, "0000");
	}

	@Override
	public void execute() {
		mem.setRegisterValue(regA, mem.getRegisterValue(regB) + mem.getRegisterValue(regC));
	}

	@Override
	public String getMachineCode() {
		return getOpcode() + Utilities.getBinaryNumber(getRegANum(), 3)
				+ Utilities.getBinaryNumber(getRegBNum(), 3) + "0000"
				+ Utilities.getBinaryNumber(getRegCNum(), 3);
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
