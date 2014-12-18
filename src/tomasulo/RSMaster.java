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
	
	public static void updateRSField(ReservationStation w, String reg, int num, boolean isImm) {
		if (isImm == true) {
			if (num == 1) {
				w.setVj(Integer.parseInt(reg));
				w.setQj(0);
			}
			else {
				w.setVk(Integer.parseInt(reg));
				w.setQk(0);
			}
		}
		else {
			Memory mem = Memory.getInstance();
			int val = mem.getRegister(reg).getROBNum();
			if (val == -1) {
				if (num == 1) {
					w.setVj(mem.getRegisterValue(reg));
					w.setQj(0);
				}
				else {
					w.setQk(mem.getRegisterValue(reg));
					w.setQk(0);
				}
			}
			else {
				if (num == 1) {
					w.setVj(0);
					w.setQj(val);
				}
				else {
					w.setVk(0);
					w.setQk(val);
				}
			}
		}
	}

	public static void issue(int rsInd, Instruction instruction, int robInd) {
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
			updateRSField(w, instruction.getRegB(), 1, false);
			if (instruction instanceof Addi) {
				updateRSField(w, instruction.getImm(), 2, true);
			}
			else {
				updateRSField(w, instruction.getRegC(), 2, false);
			}
		}
		else if (instruction.getType() == RSType.LD) {
			
		}
		// Rest of attributes
	}

}
