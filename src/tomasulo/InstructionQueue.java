package tomasulo;

import instructions.Instruction;

import java.util.LinkedList;

public class InstructionQueue {
	private static int size = 0;
	private static int pipelineWidth = 1;
	
	private static LinkedList<Instruction> queue = new LinkedList<Instruction>();
	
	public static boolean enqueue(Instruction instruction) {
		if(queue.size() == size)
			return false;
		queue.push(instruction);
		return true;
	}
	
	public static boolean isEmpty() {
		return queue.isEmpty();
	}
	
	public static boolean isFull() {
		return queue.size() >= size;
	}
	
	public static Instruction front() {
		return queue.peek();
	}
	
	public static Instruction dequeue() {
		return queue.pop();
	}
	
	public static void flush() {
		queue.clear();
	}
	
	public static void resize(int newSize) {
		size = newSize;
		flush();
	}
	
	public static int availableInstructions() {
		return queue.size();
	}
	
	public static void setPipelineWidth(int newWidth) {
		pipelineWidth = newWidth;
	}
	
	public static int getPipelineWidth() {
		return pipelineWidth;
	}
	
	public static void issue() {
		for(int i = 0; i < pipelineWidth && !isEmpty(); i++) {
			Instruction next = front();
			// TODO: issue logic
		}
	}
}
