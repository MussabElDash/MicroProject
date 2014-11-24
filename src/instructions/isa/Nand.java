package instructions.isa;

import instructions.Instruction;

public class Nand extends Instruction {

	protected Nand(String[] params) {
		super(params, new String[]{"regA", "regB", "regC"}, "0010");
	}

	@Override
	public void execute() {
		mem.setRegisterValue(regA, mem.getRegisterValue(regB) & (~mem.getRegisterValue(regC)));
	}

	@Override
	public String getMachineCode() {
		// TODO Auto-generated method stub
		return null;
	}

}
