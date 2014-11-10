package instructions.isa;

import instructions.Instruction;

public class Jmp extends Instruction {

	protected Jmp(String[] params) {
		super(params, new String[]{"regA", "imm"}, "1000");
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		mem.setRegister("PC", mem.getRegister("PC") + 1 + mem.getRegister(regA) + immValue);

	}

	@Override
	public String machinecode() {
		// TODO Auto-generated method stub
		return null;
	}

}
