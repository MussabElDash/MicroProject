package tomasulo;

import java.util.ArrayList;
import java.util.HashMap;

import instructions.Instruction;

public class RSMaster {
	private static int numInstructions;
	private static int rsCount;
	private static ArrayList<ReservationStation> rStations = new ArrayList<ReservationStation>();
	
	public static void init(HashMap<RSType, Integer> rsInfo) {
		int sum = 0;
		for (RSType type : RSType.values()) {
			int cnt = rsInfo.get(type).intValue();
			for (int j=0; j<cnt; j++) {
				rStations.add(new ReservationStation(type));
			}
			sum += cnt;
		}
		rsCount = sum;
		numInstructions = 0;
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
	
	public static boolean issue(Instruction instruction) {
		for(int i = 0; i < rsCount; i++)
			if(rStations.get(i).free() && rStations.get(i).getType() == instruction.getType()) {
				// TODO: binding logic
				return true;
			}
		return false;
	}
	
}
