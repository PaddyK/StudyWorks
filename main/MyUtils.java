package main;

public final class MyUtils {
	public static String extractClassifierToConfigure(String[] args) {
		String cSwitch = "-C";
		return searchArgs(cSwitch, args);
	}
	
	public static String searchArgs(String aSwitch, String[] args) {
		for(int i = 0; i < args.length; i++)
			if(args[i].equalsIgnoreCase(aSwitch))
				return args[i+1];
		return null;
	}
	
	private static Boolean checkArgs(String cSwitch, String[] args) {
		for(int i = 0; i < args.length; i++)
			if(args[i].equalsIgnoreCase(cSwitch))
				return true;
		return false;
	}
	
	public static boolean isBaseline(String[] args) {
		String bSwtich = "-b";
		return checkArgs(bSwtich, args);
	}
	
	public static boolean isTune(String[] args) {
		return checkArgs("-t", args);
	}
	
	public static boolean isRoughSearch(String[] args) {
		return checkArgs("-s", args);
	}
	
	public static boolean isDebug(String[] args) {
		return checkArgs("-debug", args);
	}
	
	public static boolean isWithConfig(String[] args) {
		return checkArgs("-config", args);
	}
}