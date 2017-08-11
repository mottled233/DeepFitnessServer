package checker;

import java.util.ArrayList;
import java.util.Map;
import util.ShortList;
import util.KeyPoint;

public class PushUpChecker extends Checker{
	
	String detected = "detected";
	String armstr = "armstraight";
	String armdown = "armdown";
	String legstr = "legstraight";
	String hipdown = "hipdown";
	String headstr = "headstraight";
	ShortList timeList = new ShortList(25);
	
	@Override
	public String[] checkit(Map<String, KeyPoint> people) {
		//create list
		ArrayList<String> list = new ArrayList<>();
		
		//check for general faults
		if(!canDetectedRightSide(people))
			list.add(detected);
		else{
			if (!isHipDown(people))
				list.add(hipdown);
			else if(!isLegStr(people))
				list.add(legstr);
			if (!isHeadStr(people))
				list.add(headstr);
		}
		
		//check for 
		if((list.size()!=0)&&!list.get(0).equals(detected)){
			isActionInPlace(people, list);
		}
		
		
		//if no fault
		if(list.size()==0)
			list.add("good");
		
		//list to array
		String[] arr = new String[list.size()];
		for(int i = 0;i<list.size();i++){
			arr[i] = list.get(i);
		}
		return arr;
	}
	
	
	boolean isActionInPlace(Map<String, KeyPoint> people, ArrayList<String> list){
		timeList.put((int) KeyPoint.angle(people.get("rshoulder"), people.get("relbow"), people.get("rwrist")));
		if(timeList.size()!=timeList.capacity())
			return true;
		int[] maxs = timeList.getMax();
//		if(maxs.length==2){

		if(maxs[1]<160){
			list.add("armstraight");
			return false;
		}
//		}
		int[] mins = timeList.getMin();
		if(mins[1]<85)
			return true;
		else{
			list.add("armdown");
			return false;
		}

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
}
