package instructions.isa;

import instructions.Instruction;

public class Sw extends Instruction {

	protected Sw(String[] params) {
		super(params, new String[]{"regA", "regB", "imm"}, "0100");
	}

	@Override
	public void execute() {
		mem.setMemoryValue(mem.getRegister(regB)+immValue, mem.getRegister(regA));
	}

	@Override
	public String machinecode() {
		// TODO Auto-generated method stub
		return null;
	}

}
