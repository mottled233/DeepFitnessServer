package util;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.attribute.FileTime;
import java.util.HashMap;
import java.util.Map;

import org.json.*;

public class JsonParser {
	
	public static Map<String, KeyPoint> [] parseToMap(String path) throws IOException{
		BufferedReader br = null;
		try{
			br = new BufferedReader(new FileReader(path));
		}catch(IOException e){
			
			System.out.println("error on load json");
			throw e;
		}
		String s = "", temp = null;
		while((temp = br.readLine())!= null)
			s += temp+"\n";
		Log.out("json info:"+s,0);
		br.close();
		JSONObject json = new JSONObject(s);
		JSONArray jArray = json.getJSONArray("people");
		int NOPeople = jArray.length();
		Map<String, KeyPoint> []peoMap = new Map[NOPeople];
		String []partList = {"nose", "neck", "rshoulder", "relbow", "rwrist", "lshoulder", "lelbow", 
				"lwrist", "rhip", "rknee", "rankle", "lhip", "lknee", "lankle", "reye", "rear", "leye", "lear"};
		for(int i = 0; i<NOPeople; i++){
			peoMap[i] = new HashMap<>();
			JSONObject people = jArray.getJSONObject(i);
			JSONArray parts = people.getJSONArray("pose_keypoints");
			for(int j = 0; j<partList.length; j++)
				peoMap[i].put(partList[j], new KeyPoint(parts.getDouble(j*3), parts.getDouble(j*3+1), parts.getDouble(j*3+2)));
		}
		  

		return peoMap;
	}

	public static String parseToString(long id, String[] comments, Map<String, KeyPoint> keypoint){
		//build json_object to stored key_points
		String []partList = {"nose", "neck", "rshoulder", "relbow", "rwrist", "lshoulder", "lelbow", 
				"lwrist", "rhip", "rknee", "rankle", "lhip", "lknee", "lankle", "reye", "rear", "leye", "lear"};
		JSONObject body = new JSONObject();

		for(int i = 0; i<partList.length; i++){
			JSONObject xyz = new JSONObject();
			if(keypoint!=null){
				KeyPoint kp = keypoint.get(partList[i]);
				xyz.put("x", kp.getX());
				xyz.put("y", kp.getY());
				xyz.put("p", kp.getP());
				body.put(partList[i], xyz);
			}else{
				xyz.put("x", 0);
				xyz.put("y", 0);
				xyz.put("p", 0);
				body.put(partList[i], xyz);
			}
		}
		//build json_array to stored comments
		JSONArray commArr = new JSONArray();
		for(int i = 0; i<comments.length; i++)
			commArr.put(comments[i]);
		
		//build json.
		JSONObject json = new JSONObject();
		json.put("id", id).put("comments", commArr).put("keypoints", body);
		String jString = json.toString();
		Log.i(jString);
		return jString;
	}
	
	public static void main(String[] args) throws IOException{
		if(parseToMap("D:/json/7_keypoints.json").length>0)
			System.out.println(parseToString(111l, args, parseToMap("D:/json/7_keypoints.json")[0]));
		
	}
	
}