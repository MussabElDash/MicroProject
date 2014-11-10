package instructions.isa;

import instructions.Instruction;

public class Beq extends Instruction {

	protected Beq(String[] params) {
		super(params, new String[]{"regA", "regB", "imm"}, "0110");
	}

	@Override
	public void execute() {
		if(mem.getRegister(regA) == mem.getRegister(regB))
			mem.setRegister("PC", mem.getRegister("PC") + 1 + immValue);
	}

	@Override
	public String machinecode() {
		// TODO Auto-generated method stub
		return null;
	}

}
