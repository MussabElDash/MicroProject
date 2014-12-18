package tomasulo;

import java.util.ArrayList;
import java.util.HashMap;

import utilities.Utilities;
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
	private static Memory mem = Memory.getInstance();

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
		for (int i = 0; i < rStations.size(); i++) {
			ReservationStation curRS = rStations.get(i);
			Instruction ins = curRS.getOp();
			if (ins.getType() == RSType.ADD || ins.getType() == RSType.MUL) {
				if (curRS.getCycles() == 0) {
					int res = ins.compute(curRS.getVj(), curRS.getVk());
					for (int j = 0; j < rStations.size(); j++) {
						ReservationStation chkRS = rStations.get(j);
						if (chkRS.getQj() == curRS.getDestination()) {
							chkRS.setVj(res);
							chkRS.setQj(0);
						}
						if (chkRS.getQk() == curRS.getDestination()) {
							chkRS.setVk(res);
							chkRS.setQk(0);
						}
					}
					ReorderBufferElement elem = ReorderBuffer
							.getROBElement(curRS.getDestination());
					elem.setVal(res);
					elem.setReady(true);
					curRS.flush();
				}
			} else if (ins.getType() == RSType.LD) {
				if (curRS.getCycles() == -1) {
					int res = Utilities.getDecimalNumber(mem
							.getMemoryValue(curRS.getA()));
					for (int j = 0; j < rStations.size(); j++) {
						ReservationStation chkRS = rStations.get(j);
						if (chkRS.getQj() == curRS.getDestination()) {
							chkRS.setVj(res);
							chkRS.setQj(0);
						}
						if (chkRS.getQk() == curRS.getDestination()) {
							chkRS.setVk(res);
							chkRS.setQk(0);
						}
					}
					ReorderBufferElement elem = ReorderBuffer
							.getROBElement(curRS.getDestination());
					elem.setVal(res);
					elem.setReady(true);
					curRS.flush();
				}
			} else if (ins.getType() == RSType.ST) {
				if (curRS.getQk() == 0) {
					if (curRS.getCycles() != -1) {
						curRS.setCycles(curRS.getCycles() - 1);
					} else {
						ReorderBufferElement elem = ReorderBuffer
								.getROBElement(curRS.getDestination());
						elem.setVal(curRS.getVk());
						elem.setReady(true);
						curRS.flush();
					}
				}
			}
		}
		for (int i = 0; i < rStations.size(); i++) {
			ReservationStation curRS = rStations.get(i);
			Instruction ins = curRS.getOp();
			if (ins.getType() == RSType.ADD || ins.getType() == RSType.MUL) {
				if (curRS.getQj() == 0 && curRS.getQk() == 0) {
					if (curRS.getCycles() != 0) {
						curRS.setCycles(curRS.getCycles() - 1);
					}
				}
			} else if (ins.getType() == RSType.LD) {
				if (curRS.getCycles() == delay.get(RSType.LD)) {
					boolean noStores = true;
					for (int j = 0; j < rStations.size(); j++) {
						ReservationStation chkRS = rStations.get(j);
						if (!chkRS.free()
								&& chkRS.getType() == RSType.ST
								&& chkRS.getCycles() == delay.get(RSType.ST)
								&& ReorderBuffer.before(chkRS.getDestination(),
										curRS.getDestination())) {
							noStores = false;
							break;
						}
					}
					if (noStores == true && curRS.getQj() == 0) {
						curRS.calcAddress();
						curRS.setCycles(curRS.getCycles() - 1);
					}
				} else {
					boolean noStores = true;
					for (int j = 0; j < rStations.size(); j++) {
						ReservationStation chkRS = rStations.get(j);
						if (!chkRS.free()
								&& chkRS.getType() == RSType.ST
								&& ReorderBuffer.before(chkRS.getDestination(),
										curRS.getDestination())
								&& chkRS.getA() == curRS.getA()) {
							noStores = false;
							break;
						}
					}
					if (noStores == true) {
						curRS.setCycles(curRS.getCycles() - 1);
					}
				}
			} else if (ins.getType() == RSType.ST) {
				if (curRS.getQj() == 0) {
					curRS.calcAddress();
					ReorderBufferElement elem = ReorderBuffer.getROBElement(curRS.getDestination());
					elem.setDest(curRS.getA()+"");
					curRS.setCycles(curRS.getCycles() - 1);
				}
			}
		}
	}

	public static synchronized int size() {
		return rsCount;
	}

	public static ReservationStation getRS(int num) {
		return rStations.get(num);
	}

	public static int getDelay(RSType type) {
		return delay.get(type);
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

	public static void updateRSField(ReservationStation w, String reg, int num,
			boolean isImm) {
		if (isImm == true) {
			if (num == 1) {
				w.setVj(Integer.parseInt(reg));
				w.setQj(0);
			} else {
				w.setVk(Integer.parseInt(reg));
				w.setQk(0);
			}
		} else {
			int val = mem.getRegister(reg).getROBNum();
			if (val == -1) {
				if (num == 1) {
					w.setVj(mem.getRegisterValue(reg));
					w.setQj(0);
				} else {
					w.setQk(mem.getRegisterValue(reg));
					w.setQk(0);
				}
			} else {
				ReorderBufferElement elem = ReorderBuffer.getROBElement(val);
				if (elem.isReady() == true) {
					if (num == 1) {
						w.setVj(elem.getVal());
						w.setQj(0);
					} else {
						w.setVk(elem.getVal());
						w.setQk(0);
					}
				} else {
					if (num == 1) {
						w.setVj(0);
						w.setQj(val);
					} else {
						w.setVk(0);
						w.setQk(val);
					}
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
		if (instruction.getType() == RSType.ADD
				|| instruction.getType() == RSType.MUL) {
			updateRSField(w, instruction.getRegB(), 1, false);
			if (instruction instanceof Addi) {
				updateRSField(w, instruction.getImm(), 2, true);
			} else {
				updateRSField(w, instruction.getRegC(), 2, false);
			}
			mem.getRegister(instruction.getRegA()).setROBNum(robInd);
		} else if (instruction.getType() == RSType.LD) {
			updateRSField(w, instruction.getRegB(), 1, false);
			mem.getRegister(instruction.getRegA()).setROBNum(robInd);
		} else if (instruction.getType() == RSType.ST) {
			updateRSField(w, instruction.getRegB(), 1, false);
			updateRSField(w, instruction.getRegA(), 2, false);
		}
	}

}
