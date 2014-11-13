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
		String bin = "";
		String ret = "";
		while (num != 0) {
			bin += num % 2;
			num /= 2;
		}
		for (int i=bin.length()-1; i>=0; i--) {
			ret += bin.charAt(i);
		}
		return ret;
	}
}
