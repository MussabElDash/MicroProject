package instructions.isa;

import instructions.Instruction;

public class Ret extends Instruction {

	protected Ret(String[] params) {
		super(params, new String[]{"regA"});
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		mem.setRegister("PC", mem.getRegister(regA));
	}

	@Override
	public String machinecode() {
		// TODO Auto-generated method stub
		return null;
	}

}
