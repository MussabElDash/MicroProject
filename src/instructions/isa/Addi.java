package instructions.isa;

import instructions.Instruction;

public class Addi extends Instruction {

	protected Addi(String[] params) {
		super(params, new String[]{"regA", "regB", "imm"});
	}

	@Override
	public void execute() {
		mem.setRegister(regA, mem.getRegister(regB) + immValue);
	}

	@Override
	public String machinecode() {
		// TODO Auto-generated method stub
		return null;
	}

}
