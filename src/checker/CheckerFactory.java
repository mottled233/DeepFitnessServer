package checker;

public class CheckerFactory {
	
	public static Checker createChecker(String name) throws NoSuchCheckerException{
		if(name.equals("test"))
			return new TestChecker();
		else if(name.equals("beforepushup"))
			return new BeforePushUpChecker();
		else if(name.equals("pushup"))
			return new PushUpChecker();
		else
			throw new NoSuchCheckerException("No such Checker called:"+name, 55, name);
	}
}