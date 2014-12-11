package instructions.isa;

import instructions.Instruction;
import tomasulo.RSType;
import utilities.Utilities;

public class Jmp extends Instruction {

	public Jmp(String[] params) {
		super(params, new String[]{"regA", "imm"}, "1000", RSType.JMP);
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		mem.setRegisterValue("PC", mem.getRegisterValue("PC") + mem.getRegisterValue(regA) + immValue);

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
