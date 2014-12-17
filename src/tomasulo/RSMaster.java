package tomasulo;

import java.util.ArrayList;
import java.util.HashMap;

import memory.Memory;
import instructions.Instruction;
import instructions.isa.Addi;
import instructions.isa.Beq;
import instructions.isa.Jmp;

public class RSMaster {
	private static int numInstructions;
	private static int rsCount;
	private static ArrayList<ReservationStation> rStations = new ArrayList<ReservationStation>();
	private static HashMap<RSType, Integer> delay = new HashMap<RSType, Integer>();

	public static void init(HashMap<RSType, Integer> rsInfo,
			HashMap<RSType, Integer> exc) {
		int sum = 0;
		for (RSType type : RSType.values()) {
			int cnt = rsInfo.get(type).intValue();
			for (int j = 0; j < cnt; j++) {
				rStations.add(new ReservationStation(type));
			}
			sum += cnt;
		}
		rsCount = sum;
		numInstructions = 0;
		delay = exc;
	}

	public static void stepForth() {
		// TODO: Execute logic for all reservation stations
	}

	public static synchronized int size() {
		return rsCount;
	}

	public static ReservationStation getRS(int num) {
		return rStations.get(num);
	}

	public static boolean isEmpty() {
		return numInstructions == 0;
	}

	public static int findFreeStation(RSType type) {
		for (int i = 0; i < rsCount; i++) {
			if (rStations.get(i).free() && rStations.get(i).getType() == type) {
				return i;
			}
		}
		return -1;
	}

	public static void issue(int rsInd, Instruction instruction, int robInd) {
		Memory mem = Memory.getInstance();
		ReservationStation w = rStations.get(rsInd);
		w.setOp(instruction);
		w.setCycles(delay.get(instruction.getType()));
		w.setDestination(robInd);
		if (instruction.getType() == RSType.LD
				|| instruction.getType() == RSType.ST
				|| instruction instanceof Jmp || instruction instanceof Beq) {
			w.setA(instruction.getImmValue());
		}
		if (instruction.getType() == RSType.ADD || instruction.getType() == RSType.MUL) {
			String regB = instruction.getRegB();
			if (mem.getRegister(regB).getROBNum() == -1) {
				
			}
			else {
				
			}
			if (instruction instanceof Addi) {
				
			}
			else {
				
			}
		}
		else if (instruction.getType() == RSType.LD) {
			
		}
		// Rest of attributes
	}

}
