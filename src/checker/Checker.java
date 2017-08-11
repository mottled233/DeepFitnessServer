package checker;

import java.util.Map;

import util.KeyPoint;

public abstract class Checker {
	public abstract String[] checkit(Map<String, KeyPoint> people);
}
