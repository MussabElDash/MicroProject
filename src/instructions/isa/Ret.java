package instructions.isa;

import instructions.Instruction;

public class Ret extends Instruction {

	protected Ret(String[] params) {
		super(params, new String[]{"regA"}, "1010");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		mem.setRegisterValue("PC", mem.getRegisterValue(regA));
	}

	@Override
	public String getMachineCode() {
		// TODO Auto-generated method stub
		return null;
	}

}
