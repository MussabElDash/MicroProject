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
		int len = 0;
		String res = Integer.toBinaryString(num);
		int tot = res.length() - len;
		return res.substring(tot);
	}
}
