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
}
