package memory;

import java.util.ArrayList;

public class CacheLineSet<T> {
	private ArrayList<CacheLine<T>> lineSet = new ArrayList<CacheLine<T>>();
	private int associativity;
	
	public CacheLineSet(int associativity, int lineSize){
		this.associativity = associativity;
		for(int q = 0; q < associativity; q++){
			lineSet.add(new CacheLine<T>(lineSize));
		}
	}
	
	public int searchTags(int tag){
		return searchTags(tag + "");
	}
	
	// Returns -1 if not found
	public int searchTags(String tag){
		for(int q = 0; q < associativity; q++){
			if(lineSet.get(q).getTag().equals(tag)){
				return q;
			}
		}
		
		return -1;
	}
	
	public CacheLine<T> getCacheLine(int index){
		return lineSet.get(index);
	}
	
	public void insert(int index, CacheLine<T> line){
		lineSet.set(index, line);
	}
	
	public int getLineIndexToReplace(){
		int notDirtyIndex = -1;
		for(int q = 0; q < associativity; q++){
			CacheLine<T> line = lineSet.get(q);
			if(line.getTag() == null){
				return q;
			}
			if(notDirtyIndex == -1 && !line.isDirty()){
				notDirtyIndex = q;
			}
		}
		
		if(notDirtyIndex != -1){
			return notDirtyIndex;
		}
		
		return 0;
	}
}
