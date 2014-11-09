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
}
