package util;
import javax.print.DocFlavor.STRING;

/**
 * class to print log
 * first,set the _priority
 * second,Log.out("information",priority)
 * if the priority smaller than _priority
 * it can't be printed.
 *  
 * @author mottled
 * 
 */
public class Log {
	
	public static final int INFO = 1, DEBUG = 3, EX = 5;
	private static int _priority = 0;
	private Log(){};
	
	/**
	 * set the log level
	 * @param priority the priority.
	 * 0 to print any information
	 */
	public static void setLogPriority(int priority){
		_priority = priority;
	}
	
	public static void out(String info, int priority){
		if(priority>=_priority){
			System.out.println(info);
		}
	}
	
	public static void d(String info){
		out(info, 3);
	}
	
	public static void e(String info){
		out(info, 5);
	}
	
	public static void i(String info){
		out(info, 1);
	}
}
