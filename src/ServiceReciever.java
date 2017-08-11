import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import checker.Checker;
import checker.CheckerFactory;
import checker.NoSuchCheckerException;
import util.Log;

public class ServiceReciever implements Runnable{
	
	private Socket clientSocket;
	private FT server;
	
	public ServiceReciever(FT server, Socket clientSocket){
		this.clientSocket = clientSocket;
		this.server = server;
	}

	/**
	 * the thread to process the client service
	 */
	public void run(){
		DataInputStream dis = null;
		FileOutputStream fos = null;
		DataOutputStream dos = null;//close in Sender.
		Map<Long, Long> map = null;
		ServiceSender sender = null;
		try {
			//reply the client it has been accepted
			dos = new DataOutputStream(clientSocket.getOutputStream());
			dos.writeInt(FT.SOCKET_ACCEPT);
			dos.flush();
			//create fault checker of user choose
			dis = new DataInputStream(clientSocket.getInputStream());
			Checker checker = null;
			try{
				String name = dis.readUTF();
				checker = CheckerFactory.createChecker(name);
				Log.d("checker:"+name);
			}catch (NoSuchCheckerException nce){
				Log.e("Client request a sport called "+nce.getWrongChecker()+" is not exsits.");
				dos.writeInt(FT.NOT_EXISTS);
				dos.flush();
				dis.close();
				dos.close();
				server.closeClient();
				return;
			}
			//build the sendThread
			//hash map
			map = new HashMap<>();
			sender = new ServiceSender(server, clientSocket, checker, map, dos);
			Thread sendThread = new Thread(sender);
			sendThread.start();
			//success, reply the user
			dos.writeInt(FT.SOCKET_ACCEPT);
			dos.flush();
			
			//time counter
			long sTime=0, eTime=0;
			
			//ready to receive data.
			while(!clientSocket.isClosed()){
				//get client picture id and put it in map
				map.put(server.getID(), dis.readLong());
//				map.put(server.getID(), 1111l);
				//the length of the file
				sTime = System.currentTimeMillis();
				if(clientSocket.isClosed())
					break;
				int length = dis.readInt();				Log.out("the " + server.getID() + " pic length:" + length, 1);

				if (length > 0) {
					//write to different picture file
					fos = new FileOutputStream(FT.PICPATH+server.getID()+FT.PIC_SUFFIX);
					byte[] bytes = new byte[length];
					if(clientSocket.isClosed())
						break;
					dis.readFully(bytes, 0, bytes.length);
					fos.write(bytes);
					fos.flush();
					fos.close();
					fos = new FileOutputStream(FT.VERIFYPATH+server.getID()+FT.VERIFY_SUFFIX);
					fos.close();
					eTime = System.currentTimeMillis();
					Log.out("output " + server.getID() + ".jpeg successfully:"+(eTime-sTime)+"ms",1);
					server.addID();
				}else{
					break;
				}
			}
			server.closeClient();
			sender.stop();
			if (fos != null)
				fos.close();

			
		} catch (Exception e) {
			if(sender!=null){
				sender.stop();
			}
			server.closeClient();
			e.printStackTrace();

		}
	}
}
