package checker;

import java.util.ArrayList;
import java.util.Map;


import util.KeyPoint;

public class BeforePushUpChecker extends Checker{
	String detected = "detected";
	String armstr = "armstraight";
	String legstr = "legstraight";
	String onground = "onground";
	String hipdown = "hipdown";
	String handRight = "handright";
	String headstr = "headstraight";
	
	@Override
	public String[] checkit(Map<String, KeyPoint> people) {
		//stored with the comments
		ArrayList<String> list = new ArrayList<>();
		//check if user's right side body detected
		if(!canDetectedRightSide(people))
			list.add(detected);
		else if(!isOnGround(people))
			list.add(onground);
		else{
			if (!isHipDown(people))
				list.add(hipdown);
			else if(!isLegStr(people))
				list.add(legstr);
			if (!isHeadStr(people))
				list.add(headstr);
			if (!isArmStr(people))
				list.add(armstr);
			else if(!isHandRight(people))
				list.add(handRight);
		}
		
		if(list.size()==0)
			list.add("good");

		String[] arr = new String[list.size()];
		for(int i = 0;i<list.size();i++){
			arr[i] = list.get(i);
		}
		return arr;
	}
	
	boolean isHandRight(Map<String, KeyPoint> people){
		if(Math.abs(people.get("rshoulder").slope(people.get("rwrist")))>5.5)
			return true;
		return false;
			
	}
	
	boolean isLegStr(Map<String, KeyPoint> people){
		if(KeyPoint.angle(people.get("rhip"), people.get("rknee"), people.get("rankle"))>164){
			return true;
		}return false;
	}
	
	boolean isArmStr(Map<String, KeyPoint> people){
		if(KeyPoint.angle(people.get("rshoulder"), people.get("relbow"), people.get("rwrist"))>160)
			return true;
		else return false;
	}
	
	boolean isHeadStr(Map<String, KeyPoint> people){
		if(KeyPoint.angle(people.get("rhip"), people.get("neck"), people.get("nose"))>134)
			return true;
		else return false;
	}
	
	boolean canDetectedRightSide(Map<String, KeyPoint> people){
		if(people.get("nose").getP()!=0&&people.get("neck").getP()!=0&&
				people.get("rshoulder").getP()!=0&&people.get("rwrist").getP()!=0&&people.get("rankle").getP()!=0)
			return true;
		return false;
	}
	
	boolean isHipDown(Map<String, KeyPoint> people){
		if(KeyPoint.angle(people.get("rknee"), people.get("rhip"), people.get("neck"))>160)
			return true;
		return false;
			
	}
	
	boolean isOnGround(Map<String, KeyPoint> people){
		if(people.get("rknee").slope(people.get("rhip"))>-1&&people.get("rhip").slope(people.get("neck"))>-0.5){
			return true;
		}
		return false;
	}
		

}