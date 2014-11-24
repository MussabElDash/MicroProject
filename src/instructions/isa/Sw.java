package instructions.isa;

import instructions.Instruction;

public class Sw extends Instruction {

	protected Sw(String[] params) {
		super(params, new String[]{"regA", "regB", "imm"}, "0100");
	}

	@Override
	public void execute() {
		mem.setMemoryValue(mem.getRegisterValue(regB)+immValue, mem.getRegisterValue(regA));
	}

	@Override
	public String getMachineCode() {
		// TODO Auto-generated method stub
		return null;
	}

}
