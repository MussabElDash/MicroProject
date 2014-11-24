package memory;

import java.util.ArrayList;

public class CacheLine<T> {
	private ArrayList<T> line = new ArrayList<T>();
	private int size;
	private boolean dirty = false;
	private String tag = null;
	
	public CacheLine(int size){
		this.size = size;
		for(int q = 0; q < size; q++){
			line.add(null);
		}
	}
	
	public void setLine(ArrayList<T> line){
		this.line = line;
	}
	
	public T getBlock(int offset){
		return line.get(offset);
	}
	
	public void setBlock(int offset, T value){
		line.set(offset, value);
	}
	
	public boolean isDirty(){
		return dirty;
	}
	
	public void setDirty(boolean dirty){
		this.dirty = dirty;
	}
	
	public String getTag(){
		return tag;
	}
	
	public void setTag(String tag){
		this.tag = tag;
	}
}
