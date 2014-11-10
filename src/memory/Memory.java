package memory;
public abstract class Memory {
	private Memory nextLevel, prevLevel;

	public Memory getPrevLevel() {
		return prevLevel;
	}

	public void setPrevLevel(Memory prevLevel) {
		this.prevLevel = prevLevel;
	}

	public Memory getNextLevel() {
		return nextLevel;
	}

	public void setNextLevel(Memory nextLevel) {
		this.nextLevel = nextLevel;
	}
	
	public int getRegisterNumber(String registerTitle){
		return 0;
	}
	
	public void setRegister(String registerTitle, int value){
		
	}
	
	public int getRegister(String registerTitle){
		return 0;
	}
	
	public void setMemoryValue(int memoryAddress, int value){
		
	}
	
	public int getMemoryValue(int memoryAddress){
		return 0;
	}
}
