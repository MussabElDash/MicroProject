package utilities;

public class Utilities {
	public static void raiseError(String message){
		try{
			throw new UnsupportedOperationException(message);
		}
		catch(UnsupportedOperationException e){
			System.out.println(e.getStackTrace());
		}
	}
	
	public static String getBinaryNumber(int num) {
		String res = Integer.toBinaryString(num);
		while (res.length() != 32) {
			res = "0" + res;
		}
		return res;
	}
	
	public static String getBinaryNumber(int num, int len) {
		String res = Integer.toBinaryString(num);
		while (res.length() != 32) {
			res = "0" + res;
		}
		return res.substring(32 - len);
	}

	public static int getDecimalNumber(String memoryValue) {
		String newVal = "";
		boolean flag = false;
		if (memoryValue.charAt(0) == '1') {
			flag = true;
			for (int i=0; i<memoryValue.length(); i++) {
				char val = (memoryValue.charAt(i) == '0') ? '1' : '0';
				newVal += val;
			}
		}
		int ret = 0;
		if (flag == true) {
			ret = (Integer.parseInt(newVal, 2) + 1) * -1;
			return ret;
		}
		else {
			ret = Integer.parseInt(memoryValue, 2);
			return ret;
		}
	}
}
