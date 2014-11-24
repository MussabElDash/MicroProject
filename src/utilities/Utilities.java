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
}
