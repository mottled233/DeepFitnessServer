import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

import checker.Checker;
import checker.FilterOfMutiPeople;
import util.FileJuger;
import util.JsonParser;
import util.KeyPoint;
import util.Log;

public class ServiceSender implements Runnable{
	Socket clientSocket;
	DataOutputStream dos;
	FT server;
	Checker checker;
	boolean isWork;
	Map<Long, Long> map;//to stored <server id, client id>
	long handleId;
	FilterOfMutiPeople filter;
	
	public ServiceSender(FT server, Socket socket, Checker checker, Map<Long, Long> map, DataOutputStream dos){
		this.server = server;
		this.clientSocket = socket;
		this.checker = checker;
		this.dos = dos;
		isWork = true;
		this.map = map;
		handleId = server.getID();
		filter = new FilterOfMutiPeople();
	}
	
	public void run(){
		try{
			long last=0;
			while(isWork||handleId<server.getID()){
				//Check to see if the file exists��if not means that handler has handled the picture;
				while(!FileJuger.jugeFileExists(FT.VERIFYPATH+handleId+FT.VERIFY_SUFFIX));
				while(FileJuger.jugeFileExists(FT.VERIFYPATH+handleId+FT.VERIFY_SUFFIX));
				//parse the JSON to array of map of keypoints
				//each member of the array is a map,stored with one people's keyPoint
				Map<String, KeyPoint>[] kpmaps = JsonParser.parseToMap(FT.JSON_PATH+handleId+FT.JSON_SUFFIX);
				
				//TODO filter of situation of two or more people
				Map<String, KeyPoint> people = null;
				if(kpmaps.length==0){
					Log.out("no people!", 0);
				}else if(kpmaps.length==1){
					people = kpmaps[0];
				}else {
					people = filter.filter(kpmaps);
				}
				
				//check fault
				String[] faults={"detected"};
				if(people!=null)
					faults = checker.checkit(people);

				System.out.print("check"+handleId+"results:");
				for(String str : faults){
					System.out.print(str);
				}
				System.out.println("");
				
				//build json
				if(map.get(handleId)==null){
					Log.e("no pointer,map.get");
					break;
				}
				if(people==null){
					Log.e("no pointer,people");
					Log.e("kpmaps.length:"+kpmaps.length+";");
				}
				
				String json = JsonParser.parseToString(map.get(handleId), faults, people)+"\n";
				
				//send data
				if(!clientSocket.isClosed())
					dos.writeUTF(json);
				
				//clear map
				map.remove(handleId);
				
				//delete json
				File file = new File(FT.JSON_PATH+handleId+FT.JSON_SUFFIX);
				file.delete();
				
				//handle done, id++
				handleId++;
				
			}
			dos.close();
			Log.d("sender thread stop.");
		}catch(Exception e){
			e.printStackTrace();
			try {
				server.closeClient();
				dos.close();
				Log.d("sender thread stop.");
			} catch (IOException e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			}
		}
	}
	
	public void stop(){
		isWork = false;
	}
}
