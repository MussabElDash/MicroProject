package memory;

import java.util.ArrayList;

public class CacheLine {
	private ArrayList<String> line = new ArrayList<String>();
	private int size;
	private boolean dirty = false;
	private String tag = null;
	
	public CacheLine(int size){
		this.size = size;
		for(int q = 0; q < size; q++){
			line.add(null);
		}
	}
	
	public void setLine(ArrayList<String> line){
		this.line = line;
	}
	
	public String getBlock(int offset){
		return line.get(offset);
	}
	
	public void setBlock(int offset, String value){
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
