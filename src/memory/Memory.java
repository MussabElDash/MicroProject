package memory;
public class Memory {
	private static Memory rootMemory = new Memory();
	
	public static Memory getInstance(){
		return rootMemory;
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
