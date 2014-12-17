package memory;

import utilities.Utilities;

public class Register {
	private short value = 0;
	private String title = "NOT SET";
	private boolean isZeroRegister = false;
	private int robNum;
	
	public Register(boolean isZeroRegister, String title){
		this.isZeroRegister = isZeroRegister;
		this.title = title;
		value = 0;
		robNum = -1;
	}
	
	public int getValue() {
		return value;
	}

	public void setValue(short value) {
		if(isZeroRegister){
			Utilities.raiseError("You can't set the zero register!");
		}
		else{
			this.value = value;
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getROBNum() {
		return robNum;
	}

	public void setROBNum(int robNum) {
		this.robNum = robNum;
	}
}
