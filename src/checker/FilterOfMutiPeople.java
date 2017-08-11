package checker;

import java.util.Map;

import util.KeyPoint;

public class FilterOfMutiPeople {
	String []partList = {"nose", "neck", "rshoulder", "relbow", "rwrist", "lshoulder", "lelbow", 
			"lwrist", "rhip", "rknee", "rankle", "lhip", "lknee", "lankle", "reye", "rear", "leye", "lear"};
	public Map<String, KeyPoint> filter(Map<String, KeyPoint>[] array){
		int max=0,maxpos=0;
		for(int i = 0;i<array.length;i++){
			int points = canUse(array[i]);
			if(points>max){
				maxpos = i;
				max = points;
			}
		}
		return array[maxpos];
	}
	
	public int canUse(Map<String,KeyPoint> people){
		int count=0;
		for(int i =0;i<partList.length;i++){
			if(people.get(partList[i]).getP()!=0){
				count++;
			}
		}
		return count;
	}
}
