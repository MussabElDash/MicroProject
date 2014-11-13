package instructions.isa;

import instructions.Instruction;

public class Add extends Instruction {

	protected Add(String[] params) {
		super(params, new String[]{"regA", "regB", "regC"}, "0000");
	}

	@Override
	public void execute() {
		mem.setRegister(regA, mem.getRegister(regB) + mem.getRegister(regC));
	}

	@Override
	public String getMachineCode() {
		return getOpcode() + 
	}

}
