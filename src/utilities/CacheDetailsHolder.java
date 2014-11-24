package utilities;

public class CacheDetailsHolder {
	private boolean isWriteBack = false;
	private boolean isWriteAllocate = false;
	private int accessTime = 0;
	private int size = 0;
	private int lineSize = 0;
	private int associativity = 0;

	public CacheDetailsHolder(int size, int lineSize, int associativity,
			boolean isWriteBack, boolean isWriteAllocate, int accessTime) {
		this.isWriteBack = isWriteBack;
		this.isWriteAllocate = isWriteAllocate;
		this.accessTime = accessTime;
		this.size = size;
		this.lineSize = lineSize;
		this.associativity = associativity;
	}

	public boolean isWriteBack() {
		return isWriteBack;
	}

	public boolean isWriteAllocate() {
		return isWriteAllocate;
	}

	public int getAccessTime() {
		return accessTime;
	}

	public int getSize() {
		return size;
	}

	public int getLineSize() {
		return lineSize;
	}

	public int getAssociativity() {
		return associativity;
	}
}
