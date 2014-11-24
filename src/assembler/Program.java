package assembler;

import java.util.HashMap;

import utilities.CacheDetailsHolder;
import instructions.Instruction;
import memory.Memory;

public class Program {
	Instruction[] instructions;
	Memory memory;

	public Program(String Code, int startAddress, int MemAccessTime,
			CacheDetailsHolder[] CacheDetails,
			HashMap<Integer, Integer> editedAddress) {
	}
}
