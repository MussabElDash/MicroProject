package memory;

public class Register {
	private int value = 0;
	private String title = "NOT SET";
	private boolean isZeroRegister = false;
	
	public Register(boolean isZeroRegister, String title){
		this.isZeroRegister = isZeroRegister;
		this.title = title;
		value = 0;
	}
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		if(isZeroRegister){
			throw new UnsupportedOperationException("You can't set the zero register!");
		}
		
		this.value = value;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
