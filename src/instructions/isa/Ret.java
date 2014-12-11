package instructions.isa;

import instructions.Instruction;
import tomasulo.RSType;
import utilities.Utilities;

public class Ret extends Instruction {

	public Ret(String[] params) {
		super(params, new String[]{"regA"}, "1010", RSType.JMP);
	}

	@Override
	public void execute() {
		mem.setRegisterValue("PC", mem.getRegisterValue(regA));
	}

	@Override
	public String getMachineCode() {
		return getOpcode() + Utilities.getBinaryNumber(getRegANum(), 3)
				+ Utilities.getBinaryNumber(getImmValue(), 10);
	}

	@Override
	public void writeBack() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void commit() {
		// TODO Auto-generated method stub
		
	}

}
