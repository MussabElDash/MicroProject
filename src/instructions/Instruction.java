package instructions;
public abstract class Instruction {
	String[] parameters;
	protected Instruction(String[] params) {
		parameters = params;
	}
	public abstract void execute();
	public abstract String machinecode();
}
