package instructions.isa;

import instructions.Instruction;

public class Sub extends Instruction {

	protected Sub(String[] params) {
		super(params, new String[]{"regA", "regB", "regC"}, "1011");
	}

	@Override
	public void execute() {
		mem.setRegister(regA, mem.getRegister(regB) - mem.getRegister(regC));
	}

	@Override
	public String machinecode() {
		// TODO Auto-generated method stub
		return null;
	}

}
