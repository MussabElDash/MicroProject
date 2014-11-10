package instructions.isa;

import instructions.Instruction;

public class Jalr extends Instruction {

	protected Jalr(String[] params) {
		super(params, new String[]{"regA", "regB"}, "0111");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		mem.setRegister(regA, mem.getRegister("PC") + 1);
		mem.setRegister("PC", mem.getRegister(regB));
	}

	@Override
	public String machinecode() {
		// TODO Auto-generated method stub
		return null;
	}

}
