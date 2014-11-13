package instructions.isa;

import instructions.Instruction;

public class Lw extends Instruction {

	Lw(String[] params) {
		super(params, new String[]{"regA", "regB", "imm"}, "0101");
	}

	@Override
	public void execute() {
		mem.setRegister(regA, mem.getRegister(regB)+immValue);
	}

	@Override
	public String machinecode() {
		// TODO Auto-generated method stub
		return null;
	}

}
