package tomasulo;

public class ReservationStation {
	
	private RSType type;
	
	public RSType getType() {
		return type;
	}
	
	public boolean free() {
		return false;
	}
}
