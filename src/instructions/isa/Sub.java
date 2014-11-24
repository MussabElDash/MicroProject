package instructions.isa;

import instructions.Instruction;

public class Sub extends Instruction {

	protected Sub(String[] params) {
		super(params, new String[]{"regA", "regB", "regC"}, "1011");
	}

	@Override
	public void execute() {
		mem.setRegisterValue(regA, mem.getRegisterValue(regB) - mem.getRegisterValue(regC));
	}

	@Override
	public String getMachineCode() {
		// TODO Auto-generated method stub
		return null;
	}

}
